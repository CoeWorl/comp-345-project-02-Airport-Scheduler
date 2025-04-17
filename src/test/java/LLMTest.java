import layout.Json;
import org.junit.Test;
import users.OllamaClient;
import users.Passenger;
import users.User;

import java.io.IOException;

public class LLMTest {
    @Test
    public void testLLM() throws IOException {
        User rebecca = Json.fromJsonFile("src/test/resources/rebeccaNoFlights.json", Passenger.class);
        System.err.println(rebecca.getBeverage_preferences());
    }
}
