import layout.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TerminalTest {
    @Test
    public void testTerminal() {
        Terminal terminal = new Terminal("Terminal 1");
        try{
            Terminal terminal1 = Json.fromJsonFile("src/test/resources/airport1.json", Terminal.class);
            assertEquals("2fc59d7c-849b-446f-adb1-0702682237c3", terminal1.getUuid().toString());
            assertEquals("Terminal 1", terminal1.getName());
        }
        catch(IOException e){System.out.println("Whoops");}
    }
}
