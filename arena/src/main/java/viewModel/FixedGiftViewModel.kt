package viewModel

import org.uqbar.commons.model.annotations.Observable
import wallet.DigitalWallet
import wallet.DiscountGiftStrategy
import wallet.FixedGiftStrategy
import wallet.LoyaltyGift

@Observable
class FixedGiftViewModel : LoyaltyStrategyViewModel {
    var name: String = "Gift"

    override fun add(digitalWallet: DigitalWallet, loyaltyModel: LoyaltiesAdministrationAddViewModel){
        var loyalty = LoyaltyGift(name = loyaltyModel.name,
                minAmountPerTransaction = loyaltyModel.amountOfEachOperation,
                minNumberOfTransactions = loyaltyModel.numberOfOperations,
                validFrom = loyaltyModel.dateFrom,
                validTo = loyaltyModel.dateUp,
                strategy = FixedGiftStrategy(loyaltyModel.giftAmount)
        )

        digitalWallet.addLoyalty(loyalty)
    }
}

@Observable
class DiscountViewModel : LoyaltyStrategyViewModel {
    var name: String = "Discount"

    override fun add(digitalWallet: DigitalWallet, loyaltyModel: LoyaltiesAdministrationAddViewModel){
        var loyalty = LoyaltyGift(name = loyaltyModel.name,
                minAmountPerTransaction = loyaltyModel.amountOfEachOperation,
                minNumberOfTransactions = loyaltyModel.numberOfOperations,
                validFrom = loyaltyModel.dateFrom,
                validTo = loyaltyModel.dateUp,
                strategy = DiscountGiftStrategy(loyaltyModel.percentageOfDiscount)
        )

        digitalWallet.addLoyalty(loyalty)
    }
}

interface LoyaltyStrategyViewModel {

    fun add(digitalWallet: DigitalWallet, loyaltyModel: LoyaltiesAdministrationAddViewModel)
}