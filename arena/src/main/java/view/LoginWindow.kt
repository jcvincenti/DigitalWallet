package view

import org.uqbar.arena.kotlin.extensions.*
import common.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import viewModel.LoginViewModel
import viewModel.MainMenuViewModel
import wallet.LoginException

/**
 * @author Federico Garetti
 */
class LoginWindow(owner: WindowOwner, model: LoginViewModel) : Dialog<LoginViewModel>(owner,model){
    override fun createFormPanel(mainPanel: Panel) {
        title = "DigitalWallet - Login"
        createTextInputPanel(mainPanel, "E-Mail", "email", 150)
        createPasswordFieldPanel(mainPanel, "Password", "password",150)
        Button(mainPanel) with{
            caption = "Login"
            onClick{
                try {
                    modelObject.login()
                    thisWindow.close()
                    MainMenuWindow(thisWindow, MainMenuViewModel(thisWindow.modelObject)).open()
                }catch (e: LoginException){
                    thisWindow.showInfo(e.message)
                }
            }
        }
    }


}