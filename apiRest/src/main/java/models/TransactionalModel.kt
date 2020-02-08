package models

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import serializers.LocalDateTimeDeserializer
import serializers.LocalDateTimeSerializer
import java.time.LocalDateTime

/**
 * @author Andres Mora, Fabian Frangella
 */

class TransactionalModel (
        val amount: Double,
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        val dateTime: LocalDateTime? = null,
        val description: String,
        val fullDescription: String,
        val cashOut: Boolean
)






