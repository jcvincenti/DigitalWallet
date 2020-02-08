package viewModel

import org.uqbar.commons.model.annotations.Observable
import wallet.User


/**
 * @Author Juan Cruz Vincenti
 * ViewModel for AddUserWindow
 */
@Observable
class AddUserViewModel {
    var userName: String = ""
    var userLastName: String = ""
    var userDNI: String = ""
    var userPassword: String = ""
    var userEmail: String = ""
    var userIsAdmin: Boolean = false

    fun getUser() = User(userDNI,userName,userLastName,userEmail,userPassword,userIsAdmin)

}