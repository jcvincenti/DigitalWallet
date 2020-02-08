package view

import common.*
import exceptions.InvalidFieldException
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.*
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import viewModel.UserViewModel

/**
 *  @author: Juan Cruz Vincenti
 *
 *  Visualize User Window
 *  Features:
 *  Visualize and modify certain parameters of the user
 */

class ModifyUserWindow(owner: WindowOwner, model: UserViewModel)
    : Dialog<UserViewModel>(owner, model) {

    /**
     * Function to create all the panels that the mainPanel contain
     */
    override fun createFormPanel(mainPanel: Panel) {
        title = "DigitalWallet - Modify User"

        createDoubleLabelPanel(mainPanel, "First Name", modelObject.getUserName(), 200)
        createDoubleLabelPanel(mainPanel, "Last Name", modelObject.getUserLastName(), 200)
        createDoubleLabelPanel(mainPanel, "ID Card", modelObject.getUserDNI(), 200)
        createTextInputPanel(mainPanel,"Email Adress", "email",200)
        createSelectorPanel(mainPanel, "Status", "states", "status", 200)
        createDoubleLabelPanel(mainPanel, "CVU Account", modelObject.getUserCVU(), 200)
        createDoubleLabelPanel(mainPanel, "Balance", modelObject.getBalance().toString(), 200)
        createCheckBoxPanel(mainPanel,"Is Administrator?","userIsAdmin",200)
        Panel(mainPanel) with {
            asHorizontal()
            Button(it) with {
                caption = "Modify"
                onClick {
                    try{
                        val fieldData = mutableListOf(thisWindow.modelObject.email)
                        validateFields(fieldData)
                        accept()
                    }catch (ex: InvalidFieldException){
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