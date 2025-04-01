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
        flight = new Flight("AA123", new Airport("JFK"), new Airport("LAX"), 1617225600, 1617232800, "On Time", new Terminal("A"), new Gate("A1"));
        flight.changeStatus("Delayed");
        assertEquals("Delayed", flight.getStatus());
    }
}
