import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import layout.POI;
import layout.Terminal;
import layout.Airport;
import layout.Business;

public class ScheduleTest {
    private Schedule schedule;
    private List<POI> poiList;
    @Test
    public void addPOITest() {
        Airport airport = new Airport("JFK", "John F. Kennedy International Airport");
        Terminal terminal = new Terminal("A", 1, null, "JFK");
        schedule = new Schedule(1617225600, airport, terminal);
        POI poi = new Business("Starbucks", 1);
        schedule.addPOI(poi);
        assertEquals(schedule.getPOI(0), poi);
        assertEquals(airport, schedule.getAirport());
        assertEquals(terminal, schedule.getTerminal());
        assertEquals(schedule.getPOIs().size(), 1);
        assertEquals(schedule.getDepartureTime(), 1617225600);
    }
}
