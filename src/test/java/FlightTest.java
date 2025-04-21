
import org.junit.Test;
import static org.junit.Assert.*;

import layout.Airport;
import layout.Terminal;
import layout.Gate;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
//test
public class FlightTest {
    private Flight flight;

    @Test
    public void changeStatusTest(){
        Gate gate = new Gate("A1", 1, true);
        flight = new Flight("AA123", new Airport("JFK", "John J. Kennedy"), new Airport("LAX", "Los Angeles Airport"), 1617225600, 1617232800, "On Time", new Terminal("A", 1, gate, "LAX"), gate);
        flight.changeStatus("Delayed");
        assertEquals("Delayed", flight.getStatus());
    }

    /*@Test
    public void flightFromJsonTest(){
        AirportConroller ac = new AirportController();
        Airport jfk = new Airport("JFK", "John F. Kennedy International Airport");
        Airport lax = new Airport("LAX", "Los Angeles International Airport");
        assertEquals(AirportContoller.getIntance().getAirports().size(), 2);
        FlightJson flightJson = new FlightJson("/src/test/resources/flight.json");
        flightJson.createFlights();
        assertEquals(AirportController.getInstance().getFlights().size(), 3);
        Flight flight = AirportController.getInstance().getFlights().get("AA1234");
        assertEquals(flight.getSrc(), jfk);
        assertEquals(flight.getDest(), lax);
    }*/
}

