import java.util.HashMap;

public class Passenger extends User{

    private HashMap<Flight, Schedule> flightPlans;
    
    public Passenger(String name, String username, String password, String email){
        super(name, username, password, email);
    }

    public HashMap<Flight, Schedule> getFlightPlans(){
        throw new RuntimeException("not yet implemented");
    }

    /**adds a new flight to hashmap
     * input - flight number
     * searches for flight in database/json file and adds to hashset
     * output - void
     * @throws IllegalArgumentException if flight does not exist
     */
    public void addFlight(String flightNum){
        throw new RuntimeException("Not yet implemented");
    }

    /**removes flight from hashmap
     * input - flight number
     * searches for flight in hashmap and removes
     * output - void
     * @throws IllegalArgumentException if flight not in hashmap
     */
    public void removeFlight(String flightNum){
        throw new RuntimeException("Not yet implemented");
    }

    /**checks if flight exists in user's hashmap
     * input - flight number
     * output - boolean
     */
    public boolean checkFlight(String flightNum){
        throw new RuntimeException("Not yet implemented");
    }

    /**creates schedule for specific flight and adds it to hashmap
     * input - flight number
     * checks if flight exists in user's map
     * output - void
     * @throws IllegalArgumentException if flight not in hashmap
     */
    public void createSchedule(String flightNum){
        throw new RuntimeException("Not yet implemented");
    }

    /**gets schedule for specific flight 
     * input - flight number
     * checks if flight exists in user's map
     * output - schedule
     * @throws IllegalArgumentException if flight not in hasmap
    */
    public Schedule getSchedule(String flightNum){
        throw new RuntimeException("Not yet implemented");
    }

    /**randomly generates schedule for specific flight
     * input - flight number
     * checks if flight exists in user's map
     * output - void
     * @throws IllegalArgumentException if flight not in hashmap
     */
    public void randomSchedule(String flightNum){
        throw new RuntimeException("not yet implemented");
    }

    /**updates schedule for specific flight
     * input - flight number and new schedule
     * checks if flight exists in user's map
     * output - void
     * @throws IllegalArgumentException if flight not in hashmap
     */
    public void updateSchedule(String flightNum, Schedule schedule){
        throw new RuntimeException("not yet implemented");
    }

    

}
