import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import users.Schedule;
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

    @Test
    public void timeTest(){
        Airport airport = new Airport("JFK", "John F. Kennedy International Airport");
        Terminal terminal = new Terminal("B", 2, null, "JFK");
        Schedule schedule = new Schedule(1744799400, airport, terminal);
        schedule.getDeptTime(); //April 16, 2025 @ 10:30:00 (UTC)
        String expected = "activity.Flight has already departed"; //as of April 15, 2025 @ 14:34:00 (UTC)
        assertEquals(expected, schedule.timeUntilDeparture());
        schedule.timeToArriveAtAirport(); //April 16, 2025 @ 7:30:00 (UTC)
    }
}
