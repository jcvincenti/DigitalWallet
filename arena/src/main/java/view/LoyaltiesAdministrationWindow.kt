package view

import exceptions.LoyaltyNotSelectedException
import org.uqbar.arena.windows.*
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import viewModel.*

/**
 *  @Author Andres Mora
 */
class LoyaltiesAdministrationWindow(owner: WindowOwner, model: LoyaltiesAdministrationViewModel)
    : Dialog<LoyaltiesAdministrationViewModel>(owner, model) {

    override fun createFormPanel(mainPanel: Panel) {
        title = "Digital Wallet - Benefits Administration"
        createBenefitsListPanel(mainPanel)

        Panel(mainPanel) with {
            asHorizontal()

            Button(it) with {
                caption = "Add"
                var loyaltyVM =thisWindow.modelObject
                onClick {
                    LoyaltiesAdministrationAddWindow(thisWindow, LoyaltiesAdministrationAddViewModel()) with {
                        onAccept {
                            loyaltyVM.strategyViewModel = thisWindow.modelObject.getStrategy()
                            loyaltyVM.add(thisWindow.modelObject)
                        }
                        open()
                    }
                }
            }

            Button(it) with {
                caption = "Visualize"
                onClick {
                    try {
                        thisWindow.modelObject.validateLoyalty()
                        LoyaltiesAdministrationVisualizeWindow(thisWindow, thisWindow.modelObject.selectedLoyalty!!).open()
                    } catch (ex: LoyaltyNotSelectedException){
                        thisWindow.showInfo(ex.message)
                    }
                }
            }

            Button(it) with {
                caption = "Back to Menu"
                onClick {
                    thisWindow.close()
                }
            }
        }
    }

    private fun createBenefitsListPanel(owner: Panel) {
        Panel(owner) with {
            setMinWidth(400)
            setMinHeight(200)
            table<LoyaltiesViewModel>(it) with {
                bindItemsTo("loyalties")
                bindSelectionTo("selectedLoyalty")
                column {
                    fixedSize = 100
                    title = "Name"
                    bindContentsTo("name")
                }
                column {
                    fixedSize = 100
                    title = "Strategy"
                    bindContentsTo("strategy")
                }
                column {
                    fixedSize = 200
                    title = "Valid to"
                    bindContentsTo("validTo")
                }

            }
        }
    }
}