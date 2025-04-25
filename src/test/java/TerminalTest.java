import layout.*;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class TerminalTest {
    @Test
    public void testTerminal() throws IOException { //unit tests
        //Terminal terminal = new Terminal("Terminal 1", 1, new Gate("North Entrance", 1, true), "JFK");
        Terminal terminal1;
        terminal1 = Json.fromJsonFile("src/test/resources/JFK/1.json", Terminal.class);
        assertEquals(1, terminal1.getTerminalNumber());

        assertEquals("efc59d7c-849b-446f-adb1-0702682237c3", terminal1.getUuid().toString());
        assertEquals("Terminal 1", terminal1.getName());
        assertEquals("South Entrance", terminal1.getEntrances().getFirst().getName());

        assertEquals(5, terminal1.getDistance(terminal1.getEntrances().getFirst(), terminal1.getPOI(UUID.fromString("b624702e-eb27-4392-817a-2e0cc01c28e1"))));

        Map<UUID, List<Connection>> poiMap = terminal1.getPoiMap();
        for (UUID id : poiMap.keySet()) {
            System.err.print(id + "\t");
            System.err.println(poiMap.get(id));
        }
        System.out.println(terminal1.getEntrances());
    }

    //@Test
    /*public void terminalPOITest(){
        Gate entrance = new Gate("North Entrance", 1, true);
        Terminal terminal = new Terminal("Terminal 1", 1, entrance, "JFK");
        POI poi1 = new Business("Starbucks", terminal, "restaurant", "9am-5pm");
        POI poi2 = new Business("McDonald's", terminal, "restaurant", "9am-9pm");
        POI poi3 = new Business("Bookstore", terminal, "shop", "9am-7pm");
        assertEquals(terminal.getPOIs().size(), 4);
        assertEquals(terminal.getShops().size(), 1);
        assertEquals(terminal.getRestaurants().size(), 2);
    }*/

    @Test
    public void testFindShortestRoute() throws IOException { //Integration between terminal and poi
        Terminal terminal = Json.fromJsonFile("src/test/resources/JFK/1.json", Terminal.class);

        // Equivalence: Direct connection
        POI start = terminal.getPOI(UUID.fromString("b624702e-eb27-4392-817a-2e0cc01c28e1"));
        POI end = terminal.getPOI(UUID.fromString("acace207-152a-4042-85f6-b43234da2417"));
        LinkedList<POI> shortestPath = terminal.findShortestRoute(start, end);
        assertNotNull(shortestPath);
        assertEquals(2, shortestPath.size());
        assertEquals(start, shortestPath.getFirst());
        assertEquals(end, shortestPath.getLast());

        // Equivalence: Indirect connection
        start = terminal.getPOI(UUID.fromString("aeebc26c-96cd-40a6-9055-9b2a164db481"));
        end = terminal.getPOI(UUID.fromString("acace207-152a-4042-85f6-b43234da2417"));
        shortestPath = terminal.findShortestRoute(start, end);
        assertNotNull(shortestPath);
        assertTrue(shortestPath.size() > 2);
        assertEquals(start, shortestPath.getFirst());
        assertEquals(end, shortestPath.getLast());

        // Equivalence: No path exists
        start = terminal.getPOI(UUID.fromString("aeebc26c-96cd-40a6-9055-9b2a164db481"));
        end = new Restroom(1);
        shortestPath = terminal.findShortestRoute(start, end);
        assertNull(shortestPath);

        // Edge case: Start and end are the same
        start = terminal.getPOI(UUID.fromString("b624702e-eb27-4392-817a-2e0cc01c28e1"));
        end = start;
        shortestPath = terminal.findShortestRoute(start, end);
        assertNotNull(shortestPath);
        assertEquals(1, shortestPath.size());
        assertEquals(start, shortestPath.getFirst());
    }
}
