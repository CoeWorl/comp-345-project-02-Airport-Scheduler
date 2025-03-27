import layout.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TerminalTest {
    @Test
    public void testTerminal() {
        Terminal terminal = new Terminal("Terminal 1", 1, new Gate("North Entrance", 1, true));
        Terminal terminal1;
        try{
            terminal1 = Json.fromJsonFile("src/test/resources/JFK/1.json", Terminal.class);
        }
        catch(IOException e){
            System.err.println("Error reading file");
            terminal1 = new Terminal("File read went wrong", 0, new Gate("Nothing", 1, true));
        }

        assertEquals("2fc59d7c-849b-446f-adb1-0702682237c3", terminal1.getUuid().toString());
        assertEquals("Terminal 1", terminal1.getName());
        assertEquals("South Entrance", terminal1.getEntrances().get(0).getName());
        System.out.println(terminal1.getPoi());
        System.out.println(terminal1.getEntrances());
    }
}
