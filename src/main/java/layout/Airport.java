package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.util.*;

public class Airport {
    private final String code;
    private final String name;
    private final UUID uuid;
    private final Map<Integer, Terminal> terminals;
    private final Map<UUID, List<Connection>> entranceConnections;
    // poiUUID: connection list
    // no terminal distinction, since airports might have a small number of terminals and at most
    // a couple hundred entrances, so it's not worth the extra complexity

    public Airport(String code, String name) {
        this.code = code;
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.terminals = new HashMap<>();
        this.entranceConnections = new HashMap<>();
        AirportController.getInstance().getAirports().put(code, this);
    }

    @JsonCreator
    public Airport(@JsonProperty("code") String code,
                   @JsonProperty("name") String name,
                   @JsonProperty("uuid") UUID uuid,
                   @JsonProperty("terminals") List<Integer> terminals,
                   @JsonProperty("entranceConnections") Map<UUID, List<Map<String, Object>>> entranceConnections) throws IOException {
        this.code = code;
        this.name = name;
        this.uuid = uuid;
        this.terminals = new HashMap<>();
        for (Integer t: terminals) {
            Terminal terminal = Json.fromJsonFile("src/test/resources/" + this.code + "/" + t + ".json", Terminal.class);
            this.terminals.put(t, terminal);
        }

        this.entranceConnections = new HashMap<>();
        for (Map.Entry<UUID, List<Map<String, Object>>> entry : entranceConnections.entrySet()) {
            UUID entranceUuid = entry.getKey();
            List<Map<String, Object>> connections = entry.getValue();

            // Create connection objects
            List<Connection> connectionList = new ArrayList<>();
            for (Map<String, Object> connectionData : connections) {
                int weight = (int) connectionData.get("weight");
                UUID connectionUuid = UUID.fromString((String) connectionData.get("uuid"));

                Connection connection = new Connection(weight, connectionUuid, this.code);
                connectionList.add(connection);
            }
            this.entranceConnections.put(entranceUuid, connectionList);
        }
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Map<Integer, Terminal> getTerminals() {
        return new HashMap<>(terminals);
    }

    public Map<UUID, List<Connection>> getEntranceConnections() {
        return new HashMap<>(entranceConnections);
    }

    public int getDistance(Gate start, Gate end) {
        if (entranceConnections.containsKey(start.getUuid())) {
            for (Connection connection : entranceConnections.get(start.getUuid())) {
                if (connection.getDest().getUuid().equals(end.getUuid())) {
                    return connection.weight();
                }
            }
        }
        System.err.println("ERROR: Entrance not found");
        return -1;
    }

    public String getCode() {
        return code;
    }
}