// File: src/main/java/layout/Airport.java
package layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.util.*;
import users.AirportController;

/**
 * Represents an airport containing multiple terminals and inter-terminal entrance connections.
 */
public class Airport {
    private final String code;
    private final String name;
    private final UUID uuid;
    private final Map<Integer, Terminal> terminals;
    private final Map<UUID, List<Connection>> entranceConnections;

    /**
     * Constructs an Airport with the specified code and name.
     *
     * @param code the airport code
     * @param name the airport name
     */
    public Airport(String code, String name) {
        this.code = code;
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.terminals = new HashMap<>();
        this.entranceConnections = new HashMap<>();
    }

    /**
     * Constructs an Airport from JSON properties.
     *
     * @param code the airport code
     * @param name the airport name
     * @param uuid the unique identifier for the airport
     * @param terminals a list of terminal numbers
     * @param entranceConnections a map of entrance connections
     * @throws IOException if an error occurs while reading a Terminal from file
     */
    @JsonCreator
    public Airport(@JsonProperty("code") String code,
                   @JsonProperty("name") String name,
                   @JsonProperty("uuid") UUID uuid,
                   @JsonProperty("terminals") List<Integer> terminals,
                   @JsonProperty("entranceConnections") Map<UUID, List<Map<String, Object>>> entranceConnections) throws IOException {
        this.code = code;
        if (name == null) {
            throw new IllegalArgumentException("Airport name cannot be null.");
        }
        this.name = name;
        this.uuid = uuid;
        this.terminals = new HashMap<>();
        for (Integer t : terminals) {
            Terminal terminal = Json.fromJsonFile("src/test/resources/" + this.code + "/" + t + ".json", Terminal.class);
            this.terminals.put(t, terminal);
        }
        this.entranceConnections = new HashMap<>();
        for (Map.Entry<UUID, List<Map<String, Object>>> entry : entranceConnections.entrySet()) {
            UUID entranceUuid = entry.getKey();
            List<Map<String, Object>> connectionsData = entry.getValue();
            List<Connection> connectionList = new ArrayList<>();
            for (Map<String, Object> connectionData : connectionsData) {
                int weight = (int) connectionData.get("weight");
                UUID connectionUuid = UUID.fromString((String) connectionData.get("uuid"));
                Connection connection = new Connection(weight, connectionUuid, this.code);
                connectionList.add(connection);
            }
            this.entranceConnections.put(entranceUuid, connectionList);
        }
    }

    /**
     * Returns the name of the airport.
     *
     * @return the airport name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the unique identifier of the airport.
     *
     * @return the airport UUID
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Returns a copy of the terminal mapping.
     *
     * @return a map of terminal numbers to Terminal objects
     */
    public Map<Integer, Terminal> getTerminalsMap() {
        return new HashMap<>(terminals);
    }

    /**
     * Returns a list of terminal numbers available in the airport.
     *
     * @return a list of terminal numbers
     */
    public List<Integer> getTerminals() {
        return new ArrayList<>(terminals.keySet());
    }

    /**
     * Returns a copy of the entrance connections map.
     *
     * @return a map with entrance UUID keys and lists of Connection objects as values
     */
    public Map<UUID, List<Connection>> getEntranceConnections() {
        return new HashMap<>(entranceConnections);
    }

    /**
     * Returns the distance (weight) of the connection between two gate POIs.
     *
     * @param start the starting gate
     * @param end the destination gate
     * @return the weight of the connection if present; -1 otherwise
     */
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

    /**
     * Returns the airport code.
     *
     * @return the airport code
     */
    public String getCode() {
        return code;
    }

    /**
     * Finds the inter-terminal path from a start POI to an end POI.
     * <p>
     * The method follows these steps:
     * <ol>
     *   <li>If both POIs are in the same terminal, compute the route using the terminal&apos;s shortest route.</li>
     *   <li>Otherwise, identify the start and destination terminals and obtain their first entrances.</li>
     *   <li>Compute the shortest route from the start POI to the start terminal&apos;s entrance.</li>
     *   <li>Find the inter-terminal connection between the two entrances.</li>
     *   <li>Compute the shortest route from the destination terminal&apos;s entrance to the destination POI.</li>
     *   <li>Merge the segments to form the full path.</li>
     * </ol>
     * Debug statements trace each step of the computation.
     *
     * @param start the starting POI
     * @param end the destination POI
     * @return a LinkedList of POIs representing the complete path; null if any segment fails
     */
    public LinkedList<POI> findInterTerminalPath(POI start, POI end) {
        System.out.println("Starting findInterTerminalPath from " + start.getName() + " to " + end.getName());

        // If both POIs are in the same terminal, use the terminal's shortest route.
        if (start.getTerminal() == end.getTerminal()) {
            System.out.println("Both POIs are in the same terminal.");
            Terminal term = terminals.get(start.getTerminal());
            LinkedList<POI> sameTerminalPath = term.findShortestRoute(start, end);
            System.out.println("Same terminal path: " + sameTerminalPath);
            return sameTerminalPath;
        }

        // Determine start and destination terminals.
        Terminal startTerminal = terminals.get(start.getTerminal());
        Terminal destTerminal = terminals.get(end.getTerminal());
        if (startTerminal == null || destTerminal == null) {
            System.out.println("Invalid terminal information: one of the terminals is null.");
            return null;
        }

        // Determine optimal entrances (using the first entrance in the list).
        Gate startEntrance = startTerminal.getEntrances().get(0);
        Gate destEntrance = destTerminal.getEntrances().get(0);
        System.out.println("Start terminal entrance: " + startEntrance.getName()
                + ", Destination terminal entrance: " + destEntrance.getName());

        // 1. Get the shortest route from start POI to the start terminal's entrance.
        LinkedList<POI> pathToEntrance = startTerminal.findShortestRoute(start, startEntrance);
        if (pathToEntrance == null) {
            System.out.println("No valid path from start POI to start terminal entrance.");
            return null;
        }
        System.out.println("Path from start to start entrance: " + pathToEntrance);

        // 2. Find the inter-terminal connection from the start entrance to destination entrance.
        List<Connection> connections = entranceConnections.get(startEntrance.getUuid());
        Connection interTerminalConn = null;
        if (connections != null) {
            for (Connection conn : connections) {
                if (conn.getDest().equals(destEntrance)) {
                    interTerminalConn = conn;
                    break;
                }
            }
        }
        if (interTerminalConn == null) {
            System.out.println("No inter-terminal connection found from " + startEntrance.getName()
                    + " to " + destEntrance.getName());
            return null;
        }
        System.out.println("Inter-terminal connection found: " + interTerminalConn);

        // 3. Get the shortest route from the destination entrance to the destination POI.
        LinkedList<POI> pathFromEntrance = destTerminal.findShortestRoute(destEntrance, end);
        if (pathFromEntrance == null) {
            System.out.println("No valid path from destination terminal entrance to destination POI.");
            return null;
        }
        System.out.println("Path from destination entrance to destination: " + pathFromEntrance);

        // Merge the three segments; avoid duplicate entrance entries.
        LinkedList<POI> fullPath = new LinkedList<>();
        fullPath.addAll(pathToEntrance);
        if (!fullPath.getLast().equals(destEntrance)) {
            fullPath.add(destEntrance);
        }
        if (!pathFromEntrance.isEmpty() && pathFromEntrance.getFirst().equals(destEntrance)) {
            fullPath.addAll(pathFromEntrance.subList(1, pathFromEntrance.size()));
        } else {
            fullPath.addAll(pathFromEntrance);
        }
        System.out.println("Full inter-terminal path: " + fullPath);
        return fullPath;
    }
}