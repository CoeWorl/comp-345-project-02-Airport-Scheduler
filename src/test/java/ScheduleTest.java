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
        Airport airport = new Airport();
        Terminal terminal = new Terminal("Terminal 1");
        schedule = new Schedule(1617225600, airport, terminal);
        POI poi = new Business("Burger Joint", terminal, "Restaurant", "12pm-8pm");
        schedule.addPOI(poi);
    }
}
