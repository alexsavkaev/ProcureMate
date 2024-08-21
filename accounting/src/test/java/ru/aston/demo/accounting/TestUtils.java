package ru.aston.demo.accounting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;

public final class TestUtils {

    private TestUtils() {
    }

    public static <T> String toJson(T value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(SNAKE_CASE);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper.writeValueAsString(value);
    }
}
