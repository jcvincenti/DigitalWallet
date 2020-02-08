package exceptions

/**
 * @author Juan Cruz Vincenti
 * this exception is thrown when someone tries to delete the "global" admin (email == admin)
 */

open class CantDeleteCurrentUserException() : Exception() {
    override val message: String?
        get() = "You can't delete the current user."
}