import layout.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class POITest {
    @Test
    public void createPOITest() throws IOException {
        POI poi1, poi2, poi3;
        try{
            poi1 = Json.fromJsonFile("src/test/resources/JFK/POI/Gate/aeebc26c-96cd-40a6-9055-9b2a164db481.json", Gate.class);
            poi2 = Json.fromJsonFile("src/test/resources/JFK/POI/Gate/acace207-152a-4042-85f6-b43234da2417.json", Gate.class);
            poi3 = Json.fromJsonFile("src/test/resources/JFK/POI/Business/b624702e-eb27-4392-817a-2e0cc01c28e1.json", Business.class);
        }
        catch(IOException e){
            poi1 = null;
            poi2 = null;
            poi3 = null;
            System.err.println(e);
        }

        assert poi1 != null;
        assertEquals("South Entrance", poi1.getName());
        assertTrue(((Gate) poi1).isEntranceToTerminal());

        assertEquals("Gate A1", poi2.getName());
        assertFalse(((Gate) poi2).isEntranceToTerminal());

        assertEquals("Joe Pizza", poi3.getName());

        assertThrows(IOException.class, () -> Json.fromJsonFile("src/test/resources/JFK/POI/Gate/invalid.json", Gate.class));
        Json.fromJsonFile("src/test/resources/JFK/POI/Gate/acace207-152a-4042-85f6-b43234da2417.json", Business.class);
    }
}
