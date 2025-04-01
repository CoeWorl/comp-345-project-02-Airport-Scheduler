import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import layout.Airport;
import layout.POI;
import layout.Terminal;

public class Schedule {
    private long deptTime; // Unix timestamp
    private List<POI> poi; // List of Points of Interest (POI) associated with schedule
    private Airport airport;
    private Terminal terminal;
    // Constructor
    public Schedule(long deptTime, Airport airport, Terminal terminal) {
        this.deptTime = deptTime; 
        this.poi = new ArrayList<POI>();
        this.airport = airport;
        this.terminal = terminal;
    } 

    /*
     * inputs a Unix timestamp 
     * converts timestamp into an instant
     * prints the time in UTC timezone and is readable
     */
    private void printTime(long time) {
        // Converting Unix timestamp into an instant
        Instant instant = Instant.ofEpochSecond(time);
        // Setting timezone we want to use
        ZoneId zoneId = ZoneId.of( "UTC");
        // Creating ZonedDateTime from instant and timezone and printing it
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zoneId);
        System.out.println(zdt);
    }

    /*
     *  Prints time in UTC timezone
     */
    public void getDeptTime() {
        printTime(deptTime);
    }

    /*
     * inputs the index of the POI desired
     * returns the POI at index
     * @throws IndexOutOfBoundsException if index is not in the list
     */
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

    /*
     * returns the list of POIs
     */
    public List<POI> getPOIs() {
        return poi;
    }

    /*
     * inputs a POI
     * new POI is added to the list as the last element in the list
     * @throws NullPointerException if POI is null
     */
    public void addPOI(POI newPOI) throws NullPointerException{
        // Add a new POI to the list
        if (newPOI != null) {
            poi.add(newPOI);
        }
        else {
            throw new NullPointerException("POI can't be null");
        }
    }

    public Airport getAirport(){
        return airport;
    }

    public Terminal getTerminal(){
        return terminal;
    }

    public long getDepartureTime(){
        return deptTime;
    }
}
