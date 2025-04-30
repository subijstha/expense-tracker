package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.List;

/**
 * Utility class for converting objects to/from JSON using Jackson.
 */
public final class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }



    private JsonUtil() {
        // Utility class — prevent instantiation
    }

    /**
     * Converts an object to its JSON string representation.
     *
     * @param object the object to convert
     * @return JSON string
     */
    public static String toJson(Object object) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    /**
     * Converts a JSON string to a list of objects.
     *
     * @param json       JSON string
     * @param valueClass class of the object type
     * @param <T>        type of object
     * @return list of objects
     */
    public static <T> List<T> fromJsonToList(String json, Class<T> valueClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, valueClass));
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON into list of " + valueClass.getSimpleName(), e);
        }
    }

}
