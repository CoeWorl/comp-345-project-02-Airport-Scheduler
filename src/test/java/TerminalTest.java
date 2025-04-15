import layout.*;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TerminalTest {
    @Test
    public void testTerminal() throws IOException {
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
}
