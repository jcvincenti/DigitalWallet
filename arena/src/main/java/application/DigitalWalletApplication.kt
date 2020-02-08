package application

import data.DigitalWalletData
import org.uqbar.arena.Application
import org.uqbar.arena.windows.Window
import view.LoginWindow
import viewModel.*
import wallet.*
import java.time.LocalDate


fun main() {
    val digitalWallet = DigitalWalletData.build()

    val user = User("38533749", "Fabian", "Frangella",
    "admin", "admin", true)
    digitalWallet.register(user)
    digitalWallet.assignAccount(user, Account(user, "1230"))

    val gift = LoyaltyGift(validTo = LocalDate.now().plusDays(10),
            validFrom = LocalDate.now(), strategy = DiscountGiftStrategy(10.0), minNumberOfTransactions = 5,
            minAmountPerTransaction = 10.0, name = "Hot sale")

    val discount = LoyaltyGift(validTo = LocalDate.now().plusDays(10),
            validFrom = LocalDate.now(), strategy = FixedGiftStrategy(10.0), minNumberOfTransactions = 5,
            minAmountPerTransaction = 10.0, name = "Cyber week")

    digitalWallet.addLoyalty(gift)
    digitalWallet.addLoyalty(discount)

    // Application start
    DigitalWalletApplication(digitalWallet).start()
}

/**
 * @Author Fabian Frangella
 * DigitalWallet Application
 * Must instantiate this class to build the UI
 */
class DigitalWalletApplication(val digitalWallet: DigitalWallet) : Application() {
    override fun createMainWindow(): Window<*> {
        return LoginWindow(this,
                LoginViewModel(digitalWallet,""))
    }
}
