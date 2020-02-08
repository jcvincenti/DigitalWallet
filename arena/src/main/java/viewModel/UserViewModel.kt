package viewModel

import org.uqbar.commons.model.annotations.Dependencies
import org.uqbar.commons.model.annotations.Observable
import wallet.Account
import wallet.User


/**
 * @Author Fabian Frangella & Juan Cruz Vincenti
 * ViewModel for wallet.User Class
 */
@Observable
class UserViewModel(val user: User) {

    var status: String = if(user.account!!.isBlocked)"Blocked" else "Enabled"
    var states: List<String> = mutableListOf("Enabled", "Blocked")
    var email: String = user.email
    var userIsAdmin: Boolean = user.isAdmin

    /**
     * functions to get the user atributes
     */
    fun getUserName() = user.firstName
    fun getUserLastName() = user.lastName
    fun getUserDNI() = user.idCard
    fun getUserEmail() = user.email
    fun getUserPassword() = user.password
    fun getUserIsAdmin() = if (user.isAdmin) "Yes" else "No"

    /**
     * function get the user CVU
     */
    fun getUserCVU() = user.account!!.cvu

    /**
     * function the get the balance from the user Account
     */
    fun getBalance() = user.account!!.balance

    /**
     * @Author Juan Cruz Vincenti
     * function to get the current Enabled/Blocked status according to the user's account boolean status
     */
    fun getUserAccountStatusToString() = if(user.account!!.isBlocked)"Blocked" else "Enabled"

}
