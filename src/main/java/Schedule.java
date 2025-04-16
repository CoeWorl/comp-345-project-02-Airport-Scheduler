import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import layout.Airport;
import layout.POI;
import layout.Terminal;

public class Schedule {
    private final long deptTime; // Unix timestamp
    private final List<POI> poi; // List of Points of Interest (POI) associated with schedule
    private final Airport airport;
    private final Terminal terminal;
    // Constructor
    @JsonCreator
    public Schedule(@JsonProperty long deptTime,
                    @JsonProperty Airport airport,
                    @JsonProperty Terminal terminal) {
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

    public void randomSchedule(int numPOIS){
        ArrayList<POI> possPOIs = (ArrayList<POI>) terminal.getPOIs();
        if (possPOIs.isEmpty()) {
            throw new IllegalArgumentException("No POIs in terminal");
        }
        if (numPOIS > possPOIs.size()){
            numPOIS = possPOIs.size();
        }
        for (int i = 0; i < numPOIS; i++){
            int index = (int) (Math.random() * possPOIs.size());
            POI poi = possPOIs.get(index);
            addPOI(poi);
            possPOIs.remove(index);
        }
    }

    /*Returns how much time is remaining before flight
     * input - none
     * output - string, hours and minutes until departure
    */
    public String timeUntilDeparture(){
        long currentTime = System.currentTimeMillis() / 1000L; // Convert to seconds
        long secondsRemaining = deptTime - currentTime;
        if(secondsRemaining <= 0){
            return "Flight has already departed";
        }
        long totalMinutes = secondsRemaining / 60;
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;
        return hours + " hours and " + minutes + " minutes until departure";
    }

    /*prints when to arrive at airport based on dept time
     * input - none
     * General rule is to arrive 3 hours before flight
     * output - void
     */
    public void timeToArriveAtAirport(){
        long time = deptTime - 10800;
        printTime(time);
    }
}
