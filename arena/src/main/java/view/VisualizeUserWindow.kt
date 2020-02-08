package view

import common.createDoubleLabelPanel
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.*
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import viewModel.UserViewModel

/**
 *  @author: Juan Cruz Vincenti
 *
 *  Visualize User Window
 *  Features:
 *  Visualize parameters of the user
 */


class VisualizeUserWindow(owner: WindowOwner, model: UserViewModel)
    : Window<UserViewModel>(owner, model) {
    /**
     * Function to create all the panels that the mainPanel contain
     */

    override fun createContents(mainPanel: Panel) {
        title = "DigitalWallet - Visualize User"

        createDoubleLabelPanel(mainPanel, "First Name", modelObject.getUserName(), 200)
        createDoubleLabelPanel(mainPanel, "Last Name", modelObject.getUserLastName(), 200)
        createDoubleLabelPanel(mainPanel, "ID Card", modelObject.getUserDNI(), 200)
        createDoubleLabelPanel(mainPanel, "Email Adress", modelObject.getUserEmail(), 200)
        createDoubleLabelPanel(mainPanel, "Account Status", modelObject.getUserAccountStatusToString(), 200)
        createDoubleLabelPanel(mainPanel, "CVU Account", modelObject.getUserCVU(), 200)
        createDoubleLabelPanel(mainPanel, "Balance", modelObject.getBalance().toString(), 200)
        createDoubleLabelPanel(mainPanel,"Admin?",modelObject.getUserIsAdmin().toString(),200)

        Panel(mainPanel) with {
            Button(it) with {
                caption = "Close"
                onClick {
                    thisWindow.close()
                }
            }
        }
    }
}