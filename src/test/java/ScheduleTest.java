import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import layout.POI;
import layout.Terminal;
import layout.Business;

public class ScheduleTest {
    private Schedule schedule;
    private List<POI> poiList;
    @Test
    public void addPOITest() {
        schedule = new Schedule(1617225600, poiList);
        POI poi = new Business("Burger Joint", new Terminal("A", null));
        schedule.addPOI(poi);
    }
}
