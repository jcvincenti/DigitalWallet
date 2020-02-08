package common

import org.uqbar.arena.bindings.ValueTransformer
import org.uqbar.arena.filters.TextFilter
import org.uqbar.arena.widgets.TextInputEvent
import org.uqbar.commons.model.exceptions.UserException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 * @author Andres Mora
 */
class LocalDateFilter : TextFilter {

    override fun accept(event: TextInputEvent): Boolean {
        /*
        val expected = listOf("\\d", "\\d?", "/", "\\d", "\\d?", "/", "\\d{0,4}")
        val regex = expected.reversed().fold("") { result, element -> "($element$result)?" }.toRegex()
        */

        val expected = "[0-9]{0,2}/?[0-9]{0,2}/?[0-9]{0,4}".toRegex()
        return event.potentialTextResult.matches(expected)
    }

}

/**
 * @author Andres Mora
 */
class LocalDateTransformer : ValueTransformer<LocalDate, String> {
    private var pattern = "dd/MM/yyyy"

    override fun getModelType() = LocalDate::class.java
    override fun getViewType() = String::class.java

    override fun modelToView(valueFromModel: LocalDate): String {
        return valueFromModel.format(DateTimeFormatter.ofPattern(pattern))
    }

    @Throws(UserException::class)
    override fun viewToModel(valueFromView: String): LocalDate {
        try {
            return LocalDate.parse(valueFromView, DateTimeFormatter.ofPattern(pattern))
        }catch(ex: Exception){
            throw UserException("Fecha incorrecta, usar $pattern")
        }
    }

}