import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import layout.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class POITest {
    @Test
    public void createPOITest() throws IOException { //Unit tests
        POI poi1, poi2, poi3, poi4, poi5;

        poi1 = Json.fromJsonFile("src/test/resources/JFK/POI/Gate/aeebc26c-96cd-40a6-9055-9b2a164db481.json", Gate.class);
        poi2 = Json.fromJsonFile("src/test/resources/JFK/POI/Gate/acace207-152a-4042-85f6-b43234da2417.json", Gate.class);
        poi3 = Json.fromJsonFile("src/test/resources/JFK/POI/Business/b624702e-eb27-4392-817a-2e0cc01c28e1.json", Business.class);

        assert poi1 != null;
        assertEquals("South Entrance", poi1.getName());
        assertTrue(((Gate) poi1).isEntranceToTerminal());

        assertEquals("Gate A1", poi2.getName());
        assertFalse(((Gate) poi2).isEntranceToTerminal());

        assertEquals("Joe Pizza", poi3.getName());

        assertThrows(IOException.class, () -> Json.fromJsonFile("src/test/resources/JFK/POI/Gate/invalid.json", Gate.class));

        // Shows that business could be created from Gate file
        Json.fromJsonFile("src/test/resources/JFK/POI/Gate/acace207-152a-4042-85f6-b43234da2417.json", Business.class);


        assertThrows(InvalidDefinitionException.class, ()-> Json.fromJsonFile(
                "src/test/resources/JFK/POI/Business/b624702e-eb27-4392-817a-2e0cc01c28e1.json", POI.class));

        // Restroom and stairs creation
        poi4 = new Restroom(1);
        poi5 = new Stairs(1);
        Json.toJsonFile(STR."src/test/resources/JFK/POI/Restroom/\{poi4.getUuid()}.json", poi4);
        Json.toJsonFile(STR."src/test/resources/JFK/POI/Stairs/\{poi5.getUuid()}.json", poi5);

        POI poi6 = Json.fromJsonFile(STR."src/test/resources/JFK/POI/Restroom/\{poi4.getUuid()}.json", Restroom.class);
        POI poi7 = Json.fromJsonFile(STR."src/test/resources/JFK/POI/Stairs/\{poi5.getUuid()}.json", Stairs.class);
        assertEquals(poi4.getName(), poi6.getName());
        assertEquals(poi5.getName(), poi7.getName());
        assertEquals(poi4.getUuid(), poi6.getUuid());
        assertEquals(poi5.getUuid(), poi7.getUuid());
    }
}
