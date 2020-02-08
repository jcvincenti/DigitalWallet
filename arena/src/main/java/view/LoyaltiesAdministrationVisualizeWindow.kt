package view

import common.createDoubleLabelPanel
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.*
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import viewModel.LoyaltiesViewModel

/**
 *  @author: Andres Mora
 *
 *  Visualize details of loyalty
 */

class LoyaltiesAdministrationVisualizeWindow(owner: WindowOwner, model: LoyaltiesViewModel)
    : Window<LoyaltiesViewModel>(owner, model) {

    override fun createContents(mainPanel: Panel) {
        title = "DigitalWallet - Details Loyalty"

        createDoubleLabelPanel(mainPanel, "Name:", modelObject.loyality.name, 200)
        createDoubleLabelPanel(mainPanel, "Min amount per transaction:", modelObject.loyality.minAmountPerTransaction.toString(), 200)
        createDoubleLabelPanel(mainPanel, "Min number of transaction:", modelObject.loyality.minNumberOfTransactions.toString(), 200)
        createDoubleLabelPanel(mainPanel, "Type:", modelObject.getStrategy(), 200)
        createDoubleLabelPanel(mainPanel, "Valid from:", modelObject.loyality.validFrom.toString(), 200)
        createDoubleLabelPanel(mainPanel, "Valid to:", modelObject.loyality.validTo.toString(), 200)

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