package viewModel
import exceptions.InvalidFieldException
import org.uqbar.commons.model.annotations.Dependencies
import org.uqbar.commons.model.annotations.Observable
import wallet.DiscountGiftStrategy
import wallet.FixedGiftStrategy
import wallet.LoyaltyGiftStrategy
import java.time.LocalDate

/**
 * @author Andres Mora
 */
@Observable
class LoyaltiesAdministrationAddViewModel {

    var name: String = ""
    var dateFrom: LocalDate = LocalDate.now()
    var dateUp: LocalDate = LocalDate.now().plusDays(10)
    var giftAmount: Double = 0.0
    var numberOfOperations: Int = 0
    var amountOfEachOperation: Double = 0.0
    var percentageOfDiscount: Double = 0.0
    var typeOfDiscount: List<String> = mutableListOf(FixedGiftViewModel().name, DiscountViewModel().name)
    var selectedDiscount: String = this.typeOfDiscount[0]

    fun getStrategy() : LoyaltyStrategyViewModel {
        return if (selectedDiscount == "Gift"){
            FixedGiftViewModel()
        } else {
            DiscountViewModel()
        }
    }

    @Dependencies("selectedDiscount")
    fun getSelectedGift() = this.selectedDiscount.equals("Gift")

    @Dependencies("selectedDiscount")
    fun getSelectedDis() = this.selectedDiscount.equals("Discount")
}

