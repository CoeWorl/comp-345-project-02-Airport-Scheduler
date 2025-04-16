package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.util.*;

public class Terminal {
    private final String name;
    private final UUID uuid;
    private final List<Gate> entrances;
    private final int number;
    private final Map<UUID, List<Connection>> poi_connections;
    private final Map<UUID, POI> poi;
    private final String airport;

    public Terminal(String name, int number, Gate entrance, String airport) {
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.entrances = new LinkedList<>();
        this.entrances.add(entrance);
        this.poi_connections = new HashMap<>();
        this.number = number;
        this.airport = airport;
        this.poi = new HashMap<>();
    }

    @JsonCreator
    public Terminal(@JsonProperty("name") String name, @JsonProperty("number") int number,
                    @JsonProperty("uuid") UUID uuid,
                    @JsonProperty("entrances") List<String> entranceUuids,
                    @JsonProperty("poi") HashMap<UUID, List<Map<String, Object>>> poi_string,
                    @JsonProperty("airport") String airport) throws IOException {
        this.name = name;
        this.uuid = uuid;
        this.number = number;
        this.airport = airport;
        this.poi = new HashMap<>();

        this.entrances = new ArrayList<>();
        for (String entranceUuid : entranceUuids) {
            Gate gate = Json.fromJsonFile("src/test/resources/" + this.airport + "/POI/Gate/" + entranceUuid + ".json", Gate.class);
            this.entrances.add(gate);
            this.poi.put(gate.getUuid(), gate);
        }

        this.poi_connections = new HashMap<>();
        for (Map.Entry<UUID, List<Map<String, Object>>> entry : poi_string.entrySet()) {
            UUID poiUuid = entry.getKey();
            List<Map<String, Object>> connections = entry.getValue();
            POI poi;
            // Create POI object (read from file)
            poi = switch (poiUuid.toString().charAt(0)) {
                case 'a' -> Json.fromJsonFile("src/test/resources/" + this.airport + "/POI/Gate/" + poiUuid + ".json", Gate.class);
                case 'b' -> Json.fromJsonFile("src/test/resources/" + this.airport + "/POI/Business/" + poiUuid + ".json", Business.class);
                case 'c' -> Json.fromJsonFile("src/test/resources/" + this.airport + "/POI/Restroom/" + poiUuid + ".json", Restroom.class);
                case 'e' -> Json.fromJsonFile("src/test/resources/" + this.airport + "/POI/Stairs/" + poiUuid + ".json", Stairs.class);
                default -> null;
            };
            if (poi==null) System.err.println("Unknown POI: " + poiUuid);
            this.poi.put(poiUuid, poi);
            // Create connection objects
            List<Connection> connectionList = new ArrayList<>();
            for (Map<String, Object> connectionData : connections) {
                int weight = (int) connectionData.get("weight");
                UUID connectionUuid = UUID.fromString((String) connectionData.get("uuid"));

                Connection connection = new Connection(weight, connectionUuid, this.airport);
                connectionList.add(connection);
            }
            this.poi_connections.put(poiUuid, connectionList);
        }
    }

    public int getDistance(POI start, POI end) {
        if (poi_connections.containsKey(start.getUuid())) {
            for (Connection connection : poi_connections.get(start.getUuid())) {
                if (connection.getDest().getUuid().equals(end.getUuid())) {
                    return connection.weight();
                }
            }
        }
        System.err.println("ERROR: POI NOT FOUND");
        return -1;
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

    public POI getPOI(UUID uuid) {
        for (UUID id : poi_connections.keySet()) {
            if (id.equals(uuid)) {
                return this.poi.get(id);
            }
        }
        return null;
    }

    public List<POI> getPOIs() {
        return new ArrayList<>(poi.values());
    }

    public int getTerminalNumber(){
        return number;
    }

    public Map<UUID, List<Connection>> getPoiMap() {
        return new HashMap<>(poi_connections);
    }

    public ArrayList<POI> getShops() {
        ArrayList<POI> shops = new ArrayList<>();
        for (POI p : poi.values()) {
            if (p instanceof Business) {
                shops.add(p);
            }
        }
        return shops;
    }

    public ArrayList<POI> getRestaurants() {
        ArrayList<POI> restaurants = new ArrayList<>();
        for (POI p : poi.values()) {
            if (p instanceof Business) {
                restaurants.add(p);
            }
        }
        return restaurants;
    }

    public LinkedList<POI> findShortestRoute(POI start, POI end) {
        Map<POI, Integer> distances = new HashMap<>();
        // Priority queue to store POIs with their current shortest distance
        PriorityQueue<POI> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Map<POI, POI> predecessors = new HashMap<>();
        Set<POI> visited = new HashSet<>();


        for (POI poi : poi.values()) {
            distances.put(poi, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            POI current = queue.poll();
            if (visited.contains(current)) continue;
            visited.add(current);

            // Stop if we reach the destination
            if (current.equals(end)) break;

            // Update distances to neighbors
            List<Connection> connections = poi_connections.get(current.getUuid());
            if (connections != null) {
                for (Connection connection : connections) {
                    POI neighbor = connection.getDest();
                    if (visited.contains(neighbor)) continue;

                    int newDist = distances.get(current) + connection.getWeight();
                    if (newDist < distances.get(neighbor)) {
                        distances.put(neighbor, newDist);
                        predecessors.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }
        }

        LinkedList<POI> path = new LinkedList<>();
        for (POI at = end; at != null; at = predecessors.get(at)) {
            path.addFirst(at);
        }

        // If the start is not in the path, no path exists
        if (!path.isEmpty() && !path.getFirst().equals(start)) {
            return null;
        }

        return path;
    }
}