package exceptions

/**
 * @author Fabian Frangella, Andres Mora
 * this exception throw when the user does not select a item to perform any action that need
 * a item to be selected;
 */

open class NotSelectedException(var item: String) : Exception() {
    override val message: String?
        get() = "You must select a " + item + " from the list to perform this action"
}

/**
* i.e: modify,visualize or delete on the DigitalWalletUserListWindow
*/
class UserNotSelectedException : NotSelectedException("user")

class LoyaltyNotSelectedException : NotSelectedException("loyalty")