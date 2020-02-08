package view

import org.uqbar.arena.kotlin.extensions.*
import common.*
import exceptions.InvalidFieldException
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import viewModel.AddUserViewModel

/**
 *  @author: Juan Cruz Vincenti
 *
 *  Add User Window
 *  Features:
 *  Parameters for user adding
 */

class AddUserWindow(owner: WindowOwner, model: AddUserViewModel)
    : Dialog<AddUserViewModel>(owner, model) {
    /**
     * Function to create all the panels that the mainPanel contain
     */
    override fun createFormPanel(mainPanel: Panel) {

        title = "DigitalWallet - Add User"

        createTextInputPanel(mainPanel, "First Name", "userName", 200)
        createTextInputPanel(mainPanel, "Last Name", "userLastName", 200)
        createNumericFieldPanel(mainPanel, "ID Card", "userDNI", 200)
        createTextInputPanel(mainPanel, "Email Adress", "userEmail", 200)
        createPasswordFieldPanel(mainPanel, "Password", "userPassword", 200)
        createCheckBoxPanel(mainPanel, "Is Administrator?", "userIsAdmin", 200)
        Panel(mainPanel) with {
            asHorizontal()
            Button(it) with {
                caption = "Add"
                onClick {
                    try {
                        val fieldData = mutableListOf(thisWindow.modelObject.userName,
                                thisWindow.modelObject.userLastName, thisWindow.modelObject.userDNI,
                                thisWindow.modelObject.userEmail, thisWindow.modelObject.userPassword)

                        validateFields(fieldData)
                        accept()
                    } catch (ex: InvalidFieldException){
                        thisWindow.showInfo(ex.message)
                    }
                }
            }
            Button(it) with {
                caption = "Cancel"
                onClick {
                    thisWindow.close()
                }
            }
        }
    }
}