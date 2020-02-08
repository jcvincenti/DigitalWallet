package common

import exceptions.InvalidFieldException
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*

/**
 * Function to create the text input panels with a TextBox and a specific size Label
 */
fun createTextInputPanel (owner: Panel, textLabel: String, bind: String, widthControl: Int){
    Panel(owner) with{
        asHorizontal()
        Label (it) with {
            text = textLabel
            width = widthControl
            alignLeft()
        }
        TextBox (it) with {
            width = widthControl
            bindTo(bind)
        }
    }
}

/**
 * Function to create the numeric field panels with a TextBox and a specific size Label
 */
fun createNumericFieldPanel (owner: Panel, textLabel: String, bind: String, widthControl: Int){
    Panel(owner) with{
        asHorizontal()
        Label (it) with {
            text = textLabel
            width = widthControl
            alignLeft()
        }
        NumericField (it) with {
            width = widthControl
            bindTo(bind)
        }
    }
}

/**
 * Function to create the password field panels with a TextBox and a specific size Label
 */
fun createPasswordFieldPanel (owner: Panel, textLabel: String, bind: String, widthControl: Int){
    Panel(owner) with{
        asHorizontal()
        Label (it) with {
            text = textLabel
            width = widthControl
            alignLeft()
        }
        PasswordField (it) with {
            width = widthControl
            bindTo(bind)
        }
    }
}

/**
 * Function to create the check box panels with a Checkbox and a specific size Label
 */
fun createCheckBoxPanel (owner: Panel, textLabel: String, bind: String, widthControl: Int){
    Panel (owner) with {
        asHorizontal()
        Label (it) with {
            text = textLabel
            width = widthControl
            alignLeft()
        }
        CheckBox (it) with {
            bindTo(bind)
        }
    }
}

fun createDoubleLabelPanel (owner: Panel, textLabel: String, textLabel2: String, widthControl: Int){
    Panel (owner) with {
        asHorizontal()
        Label (it) with {
            text = textLabel
            width = widthControl
            alignLeft()
        }
        Label (it) with {
            text = textLabel2
            width = widthControl
            alignLeft()
        }
    }
}

/**
 * @author Andres Mora
 * Function to create the selector panel with a selector and a specific size Label
 */
fun createSelectorPanel (owner: Panel, textLabel: String, bind: String, value: String, widthControl: Int){
    Panel(owner) with{
        asHorizontal()
        Label (it) with {
            text = textLabel
            width = widthControl
            alignLeft()
        }
        Selector<String> (it) with {
            width = widthControl - 15 //Descuento el width de la flechita para que quede lindo
            bindItemsTo(bind)
            bindSelectedTo(value)
        }
    }
}

/**
 * @author Andres Mora
 */
fun createConditionalInputPanel (owner: Panel, textLabel: String, bind: String, widthControl: Int){
    Panel(owner) with{
        asHorizontal()
        Label (it) with {
            text = textLabel
            width = widthControl
            alignLeft()
            bindEnabledTo(bind)
        }
        NumericField (it) with {
            width = widthControl
            bindEnabledTo(bind)
        }
    }
}

/**
 * @author Andres Mora
 */
fun createDateInputPanel (panel: Panel, textLabel: String, bind: String, widthControl: Int) {

    Panel(panel) with {
        asHorizontal()
        Label (it) with {
            text = textLabel
            width = widthControl
            alignLeft()
        }
        TextBox(it) with {
            width = widthControl
            withFilter(LocalDateFilter())
            bindTo(bind).setTransformer(LocalDateTransformer())
        }
    }
}

/**
 * @author Andres Mora
 * This function validates that a collection strings is not null or empty
 */
fun validateFields(fieldData : List<String>){
    for (data in fieldData){
        if (data.isNullOrEmpty()){
            throw InvalidFieldException()
        }
    }
}



