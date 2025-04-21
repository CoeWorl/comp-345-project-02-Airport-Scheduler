package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record Connection(int weight, POI dest) {

    private static final Map<UUID, POI> poiCache = new HashMap<>();

    public Connection(@JsonProperty("weight") int weight,
                      @JsonProperty("uuid") UUID uuid,
                      @JsonProperty("airportCode") String airportCode) {
        this(weight, createPOI(uuid, airportCode));
    }

    private static POI createPOI(UUID uuid, String airportCode) {
        if (poiCache.containsKey(uuid)) {
            return poiCache.get(uuid); // Return existing POI
        }
        try {
            POI newPOI = Json.jsonPOI(uuid, airportCode); // Create new POI
            poiCache.put(uuid, newPOI); // Cache the new POI
            return newPOI;
        } catch (Exception e) {
            System.err.println("Error creating POI: " + e);
            return null;
        }
    }
    public int getWeight() {
        return weight;
    }

    public POI getDest() {
        return dest;
    }

    @Override
    public String toString() {
        return STR."Connection{weight=\{weight}, destUUID=\{this.getDest().getUuid()}}";
    }
}