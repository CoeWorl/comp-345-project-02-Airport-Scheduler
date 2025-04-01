import org.junit.Test;
import static org.junit.Assert.*;

import layout.Airport;
import layout.Terminal;
import layout.Gate;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FlightTest {
    private Flight flight;

    @Test
    public void changeStatusTest(){
        Gate gate = new Gate("A1", 1, true);
        flight = new Flight("AA123", new Airport("JFK", "John J. Kennedy"), new Airport("LAX", "Los Angeles Airport"), 1617225600, 1617232800, "On Time", new Terminal("A", 1, gate, "LAX"), gate);
        flight.changeStatus("Delayed");
        assertEquals("Delayed", flight.getStatus());
    }
}
