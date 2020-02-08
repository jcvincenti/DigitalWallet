package exceptions

/**
 * @author Andres Mora, Juan Cruz Vincenti
 * This exception is thrown when the field is invalid
 */
class InvalidFieldException : Exception(){
    override val message: String
        get() = "The fields entered do not have the correct format"
}