package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.util.*;

public class Terminal {
    private final String name;
    private final UUID uuid;
    private final List<Gate> entrances;
    private final Map<POI, List<Connection>> poi;
    private final int number;
    private final String airport = "JFK";

    public Terminal(String name, int number, Gate entrance) {
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.entrances = new LinkedList<>();
        this.entrances.add(entrance);
        this.poi = new HashMap<>();
        this.number = number;
    }

    @JsonCreator
    public Terminal(@JsonProperty("name") String name, @JsonProperty("number") int number,
                    @JsonProperty("uuid") UUID uuid,
                    @JsonProperty("entrances") List<Gate> entrances,
                    @JsonProperty("poi") HashMap<UUID, List<Map<String, Object>>> poi_string) throws IOException {
        System.err.println("Checkpoint 1");
        this.name = name;
        this.uuid = uuid;
        this.entrances = entrances;
        this.number = number;

        this.poi = new HashMap<>();

        for (Map.Entry<UUID, List<Map<String, Object>>> entry : poi_string.entrySet()) {
            UUID poiUuid = entry.getKey();
            List<Map<String, Object>> connections = entry.getValue();
            POI poi;
            // Create POI object (read from file)
            try {
                poi = switch (poiUuid.toString().charAt(0)) {
                    case 'a' -> Json.fromJsonFile(STR."src/main/resources/POI/Gate\{poiUuid}.json", Gate.class);
                    case 'b' -> Json.fromJsonFile(STR."src/main/resources/POI/Business\{poiUuid}.json", Business.class);
                    default -> throw new IOException("Invalid POI type");
                };
            } catch (IOException e) {
                throw new IOException("File read went wrong inside Terminal constructor");
            }

            // Create connection objects
            List<Connection> connectionList = new ArrayList<>();

            for (Map<String, Object> connectionData : connections) {
                int weight = (int) connectionData.get("weight");
                UUID connectionUuid = UUID.fromString((String) connectionData.get("uuid"));

                Connection connection = new Connection(weight, connectionUuid);
                System.out.println(connection);
                connectionList.add(connection);
            }
            System.out.println(poi);
            this.poi.put(poi, connectionList);
        }
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<Gate> getEntrances() {
        return entrances;
    }

    public Map<POI, List<Connection>> getPoi() {
        return new HashMap<>(poi);
    }
}
