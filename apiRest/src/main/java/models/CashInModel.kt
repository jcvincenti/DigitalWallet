package models

/**
 * @author Juan Cruz Vincenti
 */
data class CashInModel(
        val cvu: String,
        val amount: Double,
        val cardNumber: String,
        val fullName: String,
        val endDate: String,
        val securityCode: String,
        val cardType: String
)