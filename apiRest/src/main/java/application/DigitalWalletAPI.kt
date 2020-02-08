package application

import controllers.*
import data.DigitalWalletData
import exceptions.NotFoundException
import handlers.NotFoundHandler
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.util.RouteOverviewPlugin

fun main() {
    DigitalWalletAPI(7000).init()
}

/**
 * @author Fabian Frangella, Federtico Garetti, Andres Mora, Juan Cruz Vincenti
 */
class DigitalWalletAPI(private val port: Int) {
    fun init(): Javalin {
        val app = Javalin.create {
            it.defaultContentType = "application/json"
            it.registerPlugin(RouteOverviewPlugin("/routes"))
            it.enableCorsForAllOrigins()
        }
                .start(port)
        // initialize system data
        val digitalWallet = DigitalWalletData.build()

        // initialize controllers
        val transactionController = TransactionController(digitalWallet)
        val userController = UserController(digitalWallet)

        app.routes {
            path("login") {
                post(userController::loginUser)
            }
            path("users") {
                path(":cvu") {
                    delete(userController::deleteUser)
                }
                path("user") {
                    path(":id") {
                        get(userController::getUser)
                    }
                }
                path("userByCVU") {
                    path(":cvu") {
                        get(userController::getUserByCVU)
                    }
                }
                path("userData") {
                    path(":id") {
                        post(userController::changeUser)
                    }
                }
                path("user-id") {
                    path(":email") {
                        get(userController::getIdByEmail)
                    }
                }

            }
            path("register") {
                post(userController::createUser)
            }
            path("transactions") {
                path(":cvu") {
                    get(transactionController::getTransactions)
                }
            }
            path("cashIn") {
                post(transactionController::cashIn)
            }
            path("transfer") {
                post(transactionController::transfer)
            }
            path("account") {
                path(":cvu") {
                    get(transactionController::getBalance)
                }
            }
        }

        app.exception(NotFoundException::class.java) { ex, ctx ->
            ctx.status(404)
            ctx.json(NotFoundHandler(ex.message!!))
        }

        return app
    }
}