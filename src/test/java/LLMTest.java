import layout.Business;
import layout.Json;
import layout.Terminal;
import org.junit.Test;
import users.Activity;
import users.OllamaClient;
import users.Passenger;
import users.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class LLMTest {
    @Test
    public void testLLM() throws IOException, InterruptedException {
        User rebecca = Json.fromJsonFile("src/test/resources/rebeccaNoFlights.json", Passenger.class);
        System.err.println(rebecca.getBeverage_preferences());
        Terminal t = Json.fromJsonFile("src/test/resources/JFK/1.json", Terminal.class);
        Activity a = new Activity("Pizza 50% Off", "Food", (Business) t.getPOI(UUID.fromString("b624702e-eb27-4392-817a-2e0cc01c28e1")));
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(a);
        System.out.println(OllamaClient.plannerRequest(rebecca, t, activities));
    }
}
