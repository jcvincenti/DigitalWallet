package viewModel

import exceptions.LoyaltyNotSelectedException
import org.uqbar.arena.kotlin.extensions.thisWindow
import org.uqbar.commons.model.annotations.Observable
import wallet.DigitalWallet

@Observable
class LoyaltiesAdministrationViewModel(val digitalWallet: DigitalWallet, var strategyViewModel: LoyaltyStrategyViewModel) {

    var selectedLoyalty: LoyaltiesViewModel? = null

    val loyalties: List<LoyaltiesViewModel> = digitalWallet.loyaltyGifts.map { loyality ->
        LoyaltiesViewModel(loyality)}


    fun add(loyalityModel: LoyaltiesAdministrationAddViewModel) {
        strategyViewModel.add(digitalWallet, loyalityModel)
    }

    fun validateLoyalty() {
        if (selectedLoyalty == null) {
            throw LoyaltyNotSelectedException()
        }
    }
}