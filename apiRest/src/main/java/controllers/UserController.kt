package controllers

import handlers.*
import io.javalin.http.Context
import models.*
import wallet.*
import java.time.LocalDateTime

/**
 * @author Fabian Frangella, Federico Garetti, Juan Cruz Vincenti
 */
class UserController(val digitalWallet: DigitalWallet) {

    /**
    * function to get the user idCard from the email
    */
    fun getIdByEmail(ctx: Context){
        val email = ctx.pathParam("email")
        try{
            val realId = digitalWallet.users.find{user -> user.email == email}!!.idCard
            val idCard = IdCardModel(realId)
            ctx.status(200)
            ctx.json(idCard)
        } catch (ex: Exception){
            ctx.status(400)
            ctx.json(Handler(400,"Bad Request",ex.message!!))
        }
    }

    /** 
    * function to get a user from the idCard
    */
    fun getUser(ctx: Context){
        val id = ctx.pathParam("id")
        var user = UserModel()
        try {
            val dwUser = digitalWallet.users.find { user -> user.idCard == id }
            user.email = dwUser!!.email
            user.firstName = dwUser.firstName
            user.lastName = dwUser.lastName
            user.idCard = dwUser.idCard
            user.cvu = dwUser.account!!.cvu
            ctx.status(200)
            ctx.json(user)
        } catch (ex: Exception) {
            ctx.status(400)
            ctx.json(Handler(400,"Bad Request",ex.message!!))
        }
    }

    /**
    * function to get a user from the CVU
    */
    fun getUserByCVU(ctx: Context){
        val cvu = ctx.pathParam("cvu")
        var user = UserModel()
        try {
            val dwUser = digitalWallet.users.find { user -> user.account!!.cvu == cvu }
            user.email = dwUser!!.email
            user.firstName = dwUser.firstName
            user.lastName = dwUser.lastName
            user.idCard = dwUser.idCard
            user.cvu = cvu
            ctx.status(200)
            ctx.json(user)
        } catch (ex: Exception) {
            ctx.status(400)
            ctx.json(Handler(400,"Bad Request",ex.message!!))
        }
    }

    /**
    * function to change a user name, last name and email 
    */
    fun changeUser(ctx: Context){
        val id = ctx.pathParam("id")
        var userData = ctx.body<UserDataModel>()
        try {
                var user = digitalWallet.users.find { user -> user.idCard == id }!!
                user.firstName = userData.firstName
                user.lastName = userData.lastName
                user.email = userData.email
                ctx.status(200)
                ctx.json(userData)
        } catch (ex: Exception){
            ctx.status(400)
            ctx.json(Handler(400,"Bad Request",ex.message!!))
        }
    }

    /**
     * function to login
     */
    fun loginUser(ctx: Context) {
        val user = ctx.body<LoginModel>()

        try {
            digitalWallet.login(user.email, user.password)
            ctx.status(201)
            ctx.json(user)
        } catch (ex: LoginException) {
            ctx.status(400)
            ctx.json(Handler(400, "Bad Request", ex.message!!))
        }
    }

    /**
     * function to delete a user
     */
    fun deleteUser(ctx: Context) {
        val cvu = ctx.pathParam("cvu")
        try {
            digitalWallet.deleteUser(digitalWallet.users.find { user -> user.account!!.cvu == cvu }!!)
            ctx.json("The user with cvu $cvu has been deleted")
            ctx.status(200)
        } catch (ex: NoSuchElementException) {
            ctx.status(404)
            ctx.json(Handler(404, "Bad request", ex.message!!))
        }
    }

    /**
     * function to create a new user
     */
    fun createUser(ctx: Context) {
        val newUser = ctx.bodyValidator(UserRegisterModel::class.java)
                .check({ it.lastName.isNotEmpty() }, "Last Name can't be empty")
                .check({ it.firstName.isNotEmpty() }, "First Name can't be empty")
                .check({ it.email.isNotEmpty() }, "Email can't be empty")
                .check({ it.password.isNotEmpty() }, "Password can't be empty")
                .check({ it.idCard.isNotEmpty() }, "Id Card can't be empty")
                .get()
        try {
            val dwUser = createDigitalWalletUser(newUser)
            digitalWallet.register(dwUser)
            digitalWallet.assignAccount(dwUser, createAccount(dwUser))
            digitalWallet.addGift(InitialGift(dwUser.account!!, 200.0, LocalDateTime.now()))
            ctx.json(mapOf("account" to dwUser.account!!.cvu))
            ctx.status(201)
        } catch (exception: IllegalArgumentException) {
            ctx.status(400)
            // no pongo el mensaje que viene del modelo porque dice "Credit Card" en vez de idCard
            ctx.json(Handler(400, "Bad request", "ID card or e-mail already registered"))
        }
    }

    /**
     * function to create a digitalWallet user from a UserRegister model
     */
    private fun createDigitalWalletUser(newUserModel: UserRegisterModel) =
            User(newUserModel.idCard, newUserModel.firstName, newUserModel.lastName, newUserModel.email, newUserModel.password, false)

    /**
     * function to create a new account
     */
    private fun createAccount(user: User): Account {
        return Account(user, DigitalWallet.generateNewCVU())
    }

}