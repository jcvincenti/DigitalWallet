package view

import common.*
import exceptions.InvalidFieldException
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import viewModel.LoyaltiesAdministrationAddViewModel

/**
 * @author Andres Mora
 */

class LoyaltiesAdministrationAddWindow(owner: WindowOwner, model: LoyaltiesAdministrationAddViewModel)
    : Dialog<LoyaltiesAdministrationAddViewModel>(owner, model) {

    override fun createFormPanel(mainPanel: Panel) {
        title = "DigitalWallet - Benefits administration - Add loyalty"
        createPanels(mainPanel)

        Button(mainPanel) with {
            caption = "Add"
            onClick {
                try {
                    val loyaltyData = mutableListOf(modelObject.name, modelObject.amountOfEachOperation.toString(),
                            modelObject.dateFrom.toString(), modelObject.dateUp.toString(), modelObject.percentageOfDiscount.toString(),
                            modelObject.giftAmount.toString(), modelObject.numberOfOperations.toString())

                    validateFields(loyaltyData)
                    accept()
                } catch (ex: InvalidFieldException){
                    thisWindow.showInfo(ex.message)
                }
            }
        }
    }

    /**
     * @author Andres Mora
     * function to create a generic panel for use in Loyalties Administration
     * */
    private fun createPanels(mainPanel: Panel) {
        createTextInputPanel(mainPanel, "Name:", "name", 200)
        createDateInputPanel(mainPanel, "Valid from:", "dateFrom", 200)
        createDateInputPanel(mainPanel, "Valid to:", "dateUp", 200)
        createNumericFieldPanel(mainPanel, "Min number of transaction:", "numberOfOperations", 200)
        createNumericFieldPanel(mainPanel, "Min amount per transaction:", "amountOfEachOperation", 200)
        createSelectorPanel(mainPanel, "Type: ", "typeOfDiscount", "selectedDiscount", 200)
        createConditionalInputPanel(mainPanel,"Gift", "selectedGift", 200)
        createConditionalInputPanel(mainPanel,"Discount", "selectedDis", 200)
    }

}
