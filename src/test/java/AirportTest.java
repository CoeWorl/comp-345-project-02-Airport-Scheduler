import layout.*;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class AirportTest {
    @Test
    public void testAirport() throws IOException {
        Airport airport = Json.fromJsonFile("src/test/resources/JFK/airport.json", Airport.class);
        assertEquals("John F. Kennedy International Airport", airport.getName());
        assertEquals("JFK", airport.getCode());

        Terminal terminal1 = airport.getTerminals().get(1);
        assertEquals(1, terminal1.getTerminalNumber());
        assertEquals("Terminal 1", terminal1.getName());

        Gate startGate = terminal1.getEntrances().get(0);
        Gate endGate = (Gate) terminal1.getPOI(UUID.fromString("acace207-152a-4042-85f6-b43234da2417"));
        //assertEquals(5, airport.getDistance(startGate, endGate));

        Map<UUID, List<Connection>> entranceConnections = airport.getEntranceConnections();
        for (UUID id : entranceConnections.keySet()) {
            System.err.print(id + "\t");
            System.err.println(entranceConnections.get(id));
        }
    }
}