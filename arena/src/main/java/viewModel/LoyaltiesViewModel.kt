package viewModel

import org.uqbar.commons.model.annotations.Observable
import wallet.DiscountGiftStrategy
import wallet.LoyaltyGift

@Observable
class LoyaltiesViewModel(val loyality: LoyaltyGift) {

    fun getName() = loyality.name

    fun getStrategy(): String {

        return if (loyality.strategy::class.equals(DiscountGiftStrategy::class)) {
            "Discount"
        } else {
            "Gift"
        }
    }

    fun getValidTo() = loyality.validTo

}