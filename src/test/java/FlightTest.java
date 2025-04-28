
import org.junit.Test;
import static org.junit.Assert.*;

import layout.Airport;
import layout.Terminal;
import layout.Gate;
import users.Flight;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
//test
public class FlightTest {
    private Flight flight;

    @Test
    public void changeStatusTest(){ //unit test
        Gate gate = new Gate("A1", 1, true);
        flight = new Flight("AA123", new Airport("JFK", "John J. Kennedy"), new Airport("LAX", "Los Angeles Airport"), 1617225600, 1617232800, "On Time", new Terminal("A", 1, gate, "LAX"), gate);
        flight.changeStatus("Delayed");
        assertEquals("Delayed", flight.getStatus()); //Equivalence class: status changed, border case: no
    }

    /*@Test
    public void flightFromJsonTest(){ //Integration test
        AirportConroller ac = new AirportController();
        Airport jfk = new Airport("JFK", "John F. Kennedy International Airport");
        Airport lax = new Airport("LAX", "Los Angeles International Airport");
        assertEquals(AirportContoller.getIntance().getAirports().size(), 2); //Equivalence class: airports added, border case: no
        FlightJson flightJson = new FlightJson("/src/test/resources/flight.json");
        flightJson.createFlights();
        assertEquals(AirportController.getInstance().getFlights().size(), 3); //Equivalence class: flights added, border case: no
        Flight flight = AirportController.getInstance().getFlights().get("AA1234");
        assertEquals(flight.getSrc(), jfk); //Equivalence class: valid source airport, border case: no
        assertEquals(flight.getDest(), lax); //Equivalence class: valid destination airport, border case: no
    }*/
}

