package view

import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import viewModel.UserViewModel

class DeleteUserWindow(owner: WindowOwner, model: UserViewModel) : Dialog<UserViewModel>(owner, model) {
    override fun createFormPanel(mainPanel: Panel) {
        title = "DigitalWallet - Delete User"
        Label(mainPanel) with {
            text = "Are you sure you want to delete the user: " +thisWindow.modelObject.getUserName()+" "+
                    thisWindow.modelObject.getUserLastName()+"?"
        }
    }
    override fun addActions(actionsPanel: Panel) {
        Button(actionsPanel) with {
            caption = "Accept"
            onClick {
               accept()
           }
        }
        Button(actionsPanel) with {
            caption = "Cancel"
            onClick {
                cancel()
            }
        }
    }
}
