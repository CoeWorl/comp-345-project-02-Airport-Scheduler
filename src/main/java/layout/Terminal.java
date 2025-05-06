package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.util.*;

/**
 * Represents an airport terminal. It manages gates, points of interest (POI), and
 * their connections within the terminal.
 */
public class Terminal {
    private final String name;
    private final UUID uuid;
    private final List<Gate> entrances;
    private final int number;
    private final Map<UUID, List<Connection>> poi_connections;
    private final Map<UUID, POI> poi;
    private final String airport;

    /**
     * Constructs a Terminal with a single entrance.
     *
     * @param name    the name of the terminal
     * @param number  the terminal number
     * @param entrance the entrance gate of the terminal
     * @param airport the airport code
     */
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

    /**
     * Constructs a Terminal from JSON properties.
     *
     * @param name          the name of the terminal
     * @param number        the terminal number
     * @param uuid          the unique identifier of the terminal
     * @param entranceUuids the list of entrance UUIDs as strings
     * @param poi_string    the POI and connection data map
     * @param airport       the airport code
     * @throws IOException if reading a POI from file fails
     */
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
            // Create POI object (read from file) determined by UUID prefix.
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

    /**
     * Returns the distance between two POIs in the terminal.
     *
     * @param start the starting POI
     * @param end   the destination POI
     * @return the connection weight if found; -1 otherwise
     */
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

    /**
     * Returns the name of the terminal.
     *
     * @return the terminal name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the unique identifier for the terminal.
     *
     * @return the UUID of the terminal
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Returns the list of entrance gates.
     *
     * @return a list of Gate objects representing the entrances
     */
    public List<Gate> getEntrances() {
        return entrances;
    }

    /**
     * Searches for a POI with the given UUID. It searches both in the POI map and the connections.
     *
     * @param uuid the UUID of the POI to search for
     * @return the POI if found; null otherwise
     */
    public POI getPOI(UUID uuid) {
        if (this.poi.containsKey(uuid)) {
            return this.poi.get(uuid);
        }
        for (List<Connection> connectionList : poi_connections.values()) {
            for (Connection connection : connectionList) {
                if (connection.getDest().getUuid().equals(uuid)) {
                    return connection.getDest();
                }
            }
        }
        return null;
    }

    /**
     * Returns a list of all POIs in the terminal.
     *
     * @return a list of POI objects
     */
    public List<POI> getPOIs() {
        return new ArrayList<>(poi.values());
    }

    /**
     * Returns the terminal number.
     *
     * @return the terminal number as an integer
     */
    public int getTerminalNumber(){
        return number;
    }

    /**
     * Returns a copy of the POI connections map.
     *
     * @return a map with POI UUID keys and lists of Connection objects as values
     */
    public Map<UUID, List<Connection>> getPoiMap() {
        return new HashMap<>(poi_connections);
    }

    /**
     * Returns all shops in the terminal.
     *
     * @return an ArrayList of POIs that are shops
     */
    public ArrayList<POI> getShops() {
        ArrayList<POI> shops = new ArrayList<>();
        for (POI p : poi.values()) {
            if (p instanceof Business) {
                if(((Business) p).getType().equalsIgnoreCase("shop") ||
                        ((Business) p).getType().equalsIgnoreCase("store")){
                    shops.add(p);
                }
            }
        }
        return shops;
    }

    /**
     * Returns all restaurants in the terminal.
     *
     * @return an ArrayList of POIs that are restaurants
     */
    public ArrayList<POI> getRestaurants() {
        ArrayList<POI> restaurants = new ArrayList<>();
        for (POI p : poi.values()) {
            if (p instanceof Business) {
                if(((Business) p).getType().equalsIgnoreCase("restaurant")){
                    restaurants.add(p);
                }
            }
        }
        return restaurants;
    }

    /**
     * Finds the shortest route between two POIs using a Dijkstra-like algorithm.
     *
     * @param start the starting POI
     * @param end   the destination POI
     * @return a LinkedList of POIs representing the path; null if no valid path exists
     */
    public LinkedList<POI> findShortestRoute(POI start, POI end) {
        Map<POI, Integer> distances = new HashMap<>();
        PriorityQueue<POI> queue = new PriorityQueue<>((p1, p2) -> {
            Integer dist1 = distances.get(p1);
            Integer dist2 = distances.get(p2);
            return Integer.compare(dist1 != null ? dist1 : Integer.MAX_VALUE, dist2 != null ? dist2 : Integer.MAX_VALUE);
        });
        Map<POI, POI> predecessors = new HashMap<>();
        Set<POI> visited = new HashSet<>();

        // Initialize distances for all POIs
        for (POI poi : poi.values()) {
            distances.put(poi, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            POI current = queue.poll();
            if (visited.contains(current)) continue;
            visited.add(current);

            // Debug print current POI and its distance
            System.out.println("Visiting: " + current.getName() + ", Distance: " + distances.get(current));

            // Break if destination is reached
            if (current.equals(end)) break;

            // Update distances to neighboring POIs
            List<Connection> connections = poi_connections.get(current.getUuid());
            if (connections != null) {
                for (Connection connection : connections) {
                    POI neighbor = connection.getDest();
                    if (visited.contains(neighbor)) continue;

                    int newDist = distances.get(current) + connection.getWeight();
                    if (newDist < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                        distances.put(neighbor, newDist);
                        predecessors.put(neighbor, current);
                        queue.add(neighbor);

                        // Debug print updated distance for neighbor
                        System.out.println("Updated distance for " + neighbor.getName() + ": " + newDist);
                    }
                }
            }
        }

        // Reconstruct the shortest path from end to start
        LinkedList<POI> path = new LinkedList<>();
        for (POI at = end; at != null; at = predecessors.get(at)) {
            path.addFirst(at);
        }

        // Debug print the reconstructed path
        System.out.println("Reconstructed path: " + path);

        // Check if valid path exists and starts with the start POI
        if (path.isEmpty() || !path.getFirst().equals(start)) {
            System.out.println("No valid path found from " + start.getName() + " to " + end.getName());
            return null;
        }

        return path;
    }

    /**
     * Returns a map of all gate POIs in the terminal, keyed by their names.
     *
     * @return a HashMap with gate names as keys and POI objects as values
     */
    public HashMap<String, POI> getGates(){
        HashMap<String, POI> gates = new HashMap<>();
        for (POI p : poi.values()) {
            if (p instanceof Gate) {
                gates.put(p.getName(), p);
            }
        }
        return gates;
    }

    /**
     * Adds a new POI to the terminal.
     *
     * @param newPOI the POI to add
     */
    public void addPOI(POI newPOI){
        poi.put(newPOI.getUuid(), newPOI);
    }

    /**
     * Returns the airport code for this terminal.
     *
     * @return the airport code as a String
     */
    public String getAirport(){
        return airport;
    }
}