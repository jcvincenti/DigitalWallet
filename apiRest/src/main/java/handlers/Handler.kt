package handlers

open class Handler (val code: Int, val type: String, open val message: String)

data class NotFoundHandler(override val message: String): Handler(404, "Not found", message)
