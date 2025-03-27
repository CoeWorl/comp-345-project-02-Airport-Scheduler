import layout.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class POITest {
    @Test
    public void createPOITest() throws IOException {
        POI poi = new Gate("Gate 1", 1, true);
        POI poi1;
        try{
            poi1 = Json.fromJsonFile("src/test/resources/JFK/POI/Gate/acace207-152a-4042-85f6-b43234da2417.json", Gate.class);
        }
        catch(IOException e){
            throw new IOException("File read went wrong inside POI constructor");
        }

        assertEquals("Gate 1", poi1.getName());
    }
}
