package viewModel

import exceptions.CantDeleteCurrentUserException
import exceptions.UserNotSelectedException
import org.uqbar.commons.model.annotations.Dependencies
import org.uqbar.commons.model.annotations.Observable
import wallet.Account
import wallet.DigitalWallet
import wallet.InitialGift
import wallet.User
import java.time.LocalDateTime

/**
 * @author Fabian Frangella, Juan Cruz Vincenti
 * View Model corresponding to the DigitalWalletUserAdministrationWindow
 */
@Observable
class UserAdministrationViewModel(val digitalWallet: DigitalWallet, val currentUser: String) {
    var userFilter: String = ""
    var selectedUser: UserViewModel? = null

    /**
     * function to get all users in the digitalWallet
     */
    private fun getDigitalWalletUsers() = digitalWallet.users

    /**
     * function to get the current user email
     */
    fun isCurrentUser(user: String) = currentUser == user

    /**
     * function to build the user list table on the UI
     */
    @Dependencies("userFilter")
    fun getFilteredUsers(): List<UserViewModel> = if (userFilter == "") mapUsers() else
        mapUsers().filter { user -> user.getUserName().toUpperCase().contains(userFilter.toUpperCase()) }

    /**
     * function to get the digitalWallets users as a collection of UserViewModel
     */
    private fun mapUsers() = getDigitalWalletUsers().map { user ->
        UserViewModel(user)
    }

    /**
     * function to delete a user of the digitalWallet from a UserViewModel instance
     */
    fun deleteUser(user: UserViewModel) {
        if (isCurrentUser(user.getUserEmail())) {
            throw CantDeleteCurrentUserException()
        }else{
            getDigitalWalletUsers().find { realUser -> realUser.email == user.getUserEmail() }?.let { digitalWallet.deleteUser(it) }
        }
    }


    /**
     * function to register a user
     * the registered user will have an account assigned and will receive a gift for $200
     */
    fun register(user: User){
        digitalWallet.register(user)
        digitalWallet.assignAccount(user,setAccount(user))
        digitalWallet.addGift(InitialGift(user.account!!,200.0, LocalDateTime.now()))
    }
    /**
     * function to modify a user
     */
    fun modify(user: UserViewModel) {
        digitalWallet.users.find { realUser -> realUser.idCard == user.getUserDNI() }?.
                let {
                    it.email = user.email
                    it.isAdmin = user.userIsAdmin
                    if (user.status == "Blocked") it.account!!.block() else it.account!!.unblock()
                }
    }

    /**
     * function to assign a new account
     */
    private fun setAccount(user: User): Account {
        return Account(user, DigitalWallet.generateNewCVU() )
    }

    /**
     *
     */
    fun validateUserSelected() {
        if (selectedUser == null) {
            throw UserNotSelectedException()
        }
    }
}