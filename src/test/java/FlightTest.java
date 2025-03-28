import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FlightTest {
    private Flight flight;

    @BeforeEach
    public void setUp() {
        flight = new Flight("AA123", new Airport("JFK"), new Airport("LAX"), 1617225600, 1617232800, "On Time", new Terminal("A"), new Gate("A1"));
    }

    @Test
    public void changeStatusTest(){
        flight.changeStatus("Delayed");
        assertEquals("Delayed", flight.getStatus());
    }
}
