package layout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/*
* Class from Toby Dragon's TECMap project
* */
public class Json {

    public static POI jsonPOI(UUID poiUuid, String airportCode) throws IOException {
        return switch (poiUuid.toString().charAt(0)) {
            case 'a' -> Json.fromJsonFile("src/test/resources/" + airportCode + "/POI/Gate/" + poiUuid + ".json", Gate.class);
            case 'b' -> Json.fromJsonFile("src/test/resources/" + airportCode + "/POI/Business/" + poiUuid + ".json", Business.class);
            case 'c' -> Json.fromJsonFile("src/test/resources/" + airportCode + "/POI/Restroom/" + poiUuid + ".json", Restroom.class);
            case 'e' -> Json.fromJsonFile("src/test/resources/" + airportCode + "/POI/Stairs/" + poiUuid + ".json", Stairs.class);
            default -> null;
        };
    }

    public static String toJsonString(Object objectToSerialize) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString( objectToSerialize);
    }

    public static void toJsonFile(String filename, Object objectToSerialize) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(filename), objectToSerialize);
    }

    public static <T> T fromJsonFile(String filename, Class<? extends T> classToBeCreated) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return  mapper.readValue(new File(filename), classToBeCreated);
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
    }
}