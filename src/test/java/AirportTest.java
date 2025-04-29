import layout.*;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class AirportTest {
    @Test
    public void testAirport() throws IOException {
        Airport airport = Json.fromJsonFile("src/test/resources/JFK/airport.json", Airport.class);
        assertEquals("John F. Kennedy International Airport", airport.getName());
        assertEquals("JFK", airport.getCode());

        Terminal terminal1 = airport.getTerminalsMap().get(1);
        assertEquals(1, terminal1.getTerminalNumber());
        assertEquals("Terminal 1", terminal1.getName());

        Gate startGate = terminal1.getEntrances().getFirst();
        Gate endGate = (Gate) terminal1.getPOI(UUID.fromString("acace207-152a-4042-85f6-b43234da2417"));
        //assertEquals(5, airport.getDistance(startGate, endGate));

        Map<UUID, List<Connection>> entranceConnections = airport.getEntranceConnections();
        for (UUID id : entranceConnections.keySet()) {
            System.err.print(STR."\{id}\t");
            System.err.println(entranceConnections.get(id));
        }
    }

    @Test
    public void testInterTerminalPathFromJson() throws IOException {
        Airport airport = Json.fromJsonFile("src/test/resources/JFK/airport.json", Airport.class);

        Terminal terminal1 = airport.getTerminalsMap().get(1);
        Terminal terminal2 = airport.getTerminalsMap().get(2);
        assertNotNull("Terminal 1 should not be null", terminal1);
        assertNotNull("Terminal 2 should not be null", terminal2);

        POI startPOI = terminal1.getPOI(UUID.fromString("ba05ecb7-879b-44dc-baab-e476bc30bca0"));
        POI destPOI = terminal2.getPOI(UUID.fromString("c0f03a77-b23d-45f2-984c-e63665af29f8"));
        assertNotNull("Start POI should not be null", startPOI);
        assertNotNull("Destination POI should not be null", destPOI);

        List<POI> path = airport.findInterTerminalPath(startPOI, destPOI);

        // Verify that a valid path is returned.
        assertNotNull("Inter-terminal path must not be null", path);
        assertFalse("Inter-terminal path must not be empty", path.isEmpty());
        // Verify the path starts and ends with the correct POIs.
        assertEquals("Path must start with the start POI", startPOI, path.getFirst());
        assertEquals("Path must end with the destination POI", destPOI, path.getLast());

        // Verify that the path includes both terminal entrances.
        Gate entrance1 = terminal1.getEntrances().getFirst();
        Gate entrance2 = terminal2.getEntrances().getFirst();
        assertTrue("Path must contain the start terminal entrance", path.contains(entrance1));
        assertTrue("Path must contain the destination terminal entrance", path.contains(entrance2));
    }
}