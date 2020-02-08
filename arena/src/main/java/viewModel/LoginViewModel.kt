package viewModel

import org.uqbar.commons.model.annotations.Observable
import wallet.DigitalWallet

/**
 * @author Federico Garetti
 */
@Observable
class LoginViewModel(val digitalWallet: DigitalWallet,var email: String) {
    var password: String = ""

    fun login(){
        digitalWallet.login(email,password)
    }
}