import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import layout.POI;

public class Schedule {
    private long deptTime; // Unix timestamp
    private List<POI> poi; // List of Points of Interest (POI) associated with schedule
    // Constructor
    public Schedule(long deptTime, List<POI> poi) {
        this.deptTime = deptTime; 
        this.poi = poi; 
    } 

    private void printTime(long time) {
        // Converting Unix timestamp into an instant
        Instant instant = Instant.ofEpochSecond(time);
        // Setting timezone we want to use
        ZoneId zoneId = ZoneId.of( "UTC");
        // Creating ZonedDateTime from instant and timezone and printing it
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zoneId);
        System.out.println(zdt);
    }

    public void getDeptTime() {
        printTime(deptTime);
    }

    public POI getPOI(int index) throws IndexOutOfBoundsException {
        if (poi.size() == 0) {
            return null; // Return null if there are no POIs in the list
        
        }
        // Check if index is within bounds
        else if (index < 0 || index >= poi.size()) {
            throw new IndexOutOfBoundsException(index + " is out of bounds");
        }
        else{
            // Return the POI at index
            return poi.get(index);
        }
    }

    public List<POI> getPOIs() {
        return poi;
    }

    public void addPOI(POI newPOI) {
        // Add a new POI to the list
        if (newPOI != null) {
            poi.add(newPOI);
        }
    }
}
