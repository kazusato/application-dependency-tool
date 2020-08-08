package dev.kazusato.tools.appdep

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator
import org.junit.jupiter.api.Test

class GenerateJsonSchema {

    @Test
    fun generateJsonSchema() {
        val objectMapper = ObjectMapper()
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
        val jsonSchemaGenerator = JsonSchemaGenerator(objectMapper)
        val jsonSchema = jsonSchemaGenerator.generateSchema(ApplicationDependencyConfig::class.java)

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(System.out, jsonSchema)
    }

}