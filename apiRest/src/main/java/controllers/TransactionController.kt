package controllers


import exceptions.NegativeMoneyException
import handlers.Handler
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import models.BalanceModel
import models.CashInModel
import models.TransactionalModel
import models.TransferModel
import wallet.*
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 * @author Fabian Frangella, Federico Garetti, Andres Mora, Juan Cruz Vincenti
 */
class TransactionController(val digitalWallet: DigitalWallet) {

    /**
     * function to get all transactions from an account
     */
    fun getTransactions(ctx: Context) {
        try {
            val cvu = ctx.pathParam("cvu")
            val transactionsModel = digitalWallet.accountByCVU(cvu).transactions.map { transaction ->
                TransactionalModel(
                        transaction.amount,
                        transaction.dateTime,
                        transaction.description(),
                        transaction.fullDescription(),
                        transaction.isCashOut()
                )
            }
            ctx.status(200)
            ctx.json(transactionsModel)

        } catch (ex: NoSuchElementException) {
            ctx.status(400)
            ctx.json(Handler(400, "Bad request", ex.message!!))
        }
    }

    /**
     * function to perform a money transfer between accounts
     */
    fun transfer(ctx: Context) {
        val transferData = ctx.bodyAsClass(TransferModel::class.java)
        try {
            transferMoneyThroughAccounts(transferData.fromCVU, transferData.toCVU, transferData.amount)
            ctx.json("Transfer successful")
        } catch (ex: NoSuchElementException) {
            ctx.status(400)
            ctx.json(Handler(400, "Bad Request", ex.message!!))
        } catch (ex: NoMoneyException) {
            ctx.status(403)
            // cambio el mensaje porque la api está devolviendo la referencia al objeto
            ctx.json(Handler(403, "Bad Request", "You don't have enough money"))
        } catch (ex: BlockedAccountException) {
            ctx.status(403)
            ctx.json(Handler(403, "Bad Request", ex.message!!))
        } catch (ex: NegativeMoneyException) {
            ctx.status(403)
            ctx.json(Handler(403, "Bad Request", ex.message!!))
        }
    }

    /**
     * function to perform a cash in to an account
     */
    fun cashIn(ctx: Context) {
        val cashIn = ctx.bodyAsClass(CashInModel::class.java)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        var card: Card= CreditCard(cashIn.cardNumber, cashIn.fullName, LocalDate.parse("01/" + cashIn.endDate, formatter), cashIn.securityCode)
        if (cashIn.cardType == "debit"){
            card = DebitCard(cashIn.cardNumber, cashIn.fullName, LocalDate.parse("01/" + cashIn.endDate, formatter), cashIn.securityCode)
        }
        try {
            transferMoneyFromCard(
                    cashIn.cvu, card, cashIn.amount
            )
            ctx.status(201)
            ctx.json("Cash in successful")
        } catch (ex: Exception) {
            when (ex) {
                is BlockedAccountException, is NoSuchElementException -> {
                    ctx.status(400)
                    ctx.json(Handler(400, "Bad Request", ex.message!!))
                }
                is NegativeMoneyException -> {
                    ctx.status(403)
                    ctx.json(Handler(403, "Bad Request", ex.message!!))
                }
                else -> throw ex
            }
        }
    }


    /**
     * function to get the balance from an account by the cvu
     */
    fun getBalance(ctx: Context) {
        val cvu = ctx.pathParam("cvu")
        try {
            val balanceModel = BalanceModel(digitalWallet.accountByCVU(cvu).balance)
            ctx.json(balanceModel)
            ctx.status(200)
        } catch (ex: NoSuchElementException) {
            ctx.status(400)
            ctx.json(Handler(400, "Bad request", ex.message!!))
        }
    }

    private fun transferMoneyFromCard(cvu: String, card: Card, amount: Double) {
        if (amount > 0.0) {
            digitalWallet.transferMoneyFromCard(
                    cvu, card, amount
            )
        } else {
            throw NegativeMoneyException("Can't transfer a negative amount")
        }
    }

    private fun transferMoneyThroughAccounts(fromCVU: String, toCVU: String, amount: Double) {
        if (amount > 0.0) {
            digitalWallet.transfer(fromCVU, toCVU, amount)
        } else {
            throw NegativeMoneyException("Can't transfer a negative amount")
        }
    }
}
