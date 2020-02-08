package models

/**
 * @author Fabian Frangella
 */
data class UserRegisterModel(val email: String = "", val firstName: String,
                             val idCard: String, val lastName: String, val password: String) {

}
