package view

import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.*
import viewModel.*

/**
 * @author Fabian Frangella, Andres Mora
 *
 * DigitalWallet main menu Window used to navigate through the digital wallet administration modules
 * This window should be open after a successful login
 */
class MainMenuWindow(owner: WindowOwner, model:    MainMenuViewModel)
    : Window<MainMenuViewModel>(owner, model) {
    override fun createContents(mainPanel: Panel) {
        Panel(mainPanel) with {
            title = "Digital Wallet - Main menu"
            Label(mainPanel) with {
                fontSize = 16
                alignCenter()
                text = "Digital Wallet - Main Menu"
            }
            Button(mainPanel) with {
                caption = "Users administration module"
                onClick {
                    thisWindow.close()
                    UserAdministrationWindow(thisWindow,
                            UserAdministrationViewModel(thisWindow.modelObject.userAdmin.digitalWallet, thisWindow.modelObject.userAdmin.email))
                            .open()
                }
            }

            Button(mainPanel) with {
                caption = "Loyalties administration module"
                onClick {
                    LoyaltiesAdministrationWindow(thisWindow,
                            LoyaltiesAdministrationViewModel(thisWindow.modelObject.userAdmin.digitalWallet, FixedGiftViewModel()))
                            .open()
                }
            }

            Button(mainPanel) with {
                caption = "Quit"
                onClick {
                    thisWindow.close()
                }
            }
        }
    }
}