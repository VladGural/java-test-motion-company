package pro.gural.company.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Vladyslav Gural
 * @version 2024-07-31
 */
public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    private static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.warn("Utils#toJson failed to make json from object: {}, failed with message: {}",
                    object, e.getMessage(), e);
            return null;
        }
    }

    public static <T> T fromJson(String body, Class<T> c) {
        if (body == null) return null;
        try {
            return mapper.readValue(body, c);
        } catch (JsonProcessingException e) {
            logger.warn("Utils#fromJson failed to make object from json: {}, failed with message: {}",
                    body, e.getMessage(), e);
            return null;
        }
    }

    public static <T extends Enum<T>> T stringToEnum(Class<T> enumClass, String name) {
        try {
            return Enum.valueOf(enumClass, name);
        } catch (Exception e) {
            logger.error("Cannot convert string to enum", e);
            return null;
        }
    }

    public static List<String> fromStringArrayJson(String str) {
        if (str == null) {
            return new ArrayList<>();
        }
        String[] stringArray = Util.fromJson(str, String[].class);
        if (stringArray == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(stringArray);
    }
}
