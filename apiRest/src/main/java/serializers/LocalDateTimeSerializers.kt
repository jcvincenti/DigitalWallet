package serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException
import java.time.LocalDateTime

/**
 * @author Fabian Frangella
 */
class LocalDateTimeSerializer : JsonSerializer<LocalDateTime>() {
    @Throws(IOException::class)
    override fun serialize(arg0: LocalDateTime, arg1: JsonGenerator, arg2: SerializerProvider) {
        arg1.writeString(arg0.toString())
    }
}

/**
 * @author Fabian Frangella
 */
class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime>() {
    @Throws(IOException::class)
    override fun deserialize(arg0: JsonParser, arg1: DeserializationContext): LocalDateTime {
        return LocalDateTime.parse(arg0.text)
    }
}