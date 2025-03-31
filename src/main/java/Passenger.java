import java.util.HashMap;

public class Passenger extends User{

    private HashMap<Flight, Schedule> flightPlans;
    
    public Passenger(String name, String username, String password, String email){
        super(name, username, password, email);
        flightPlans = new HashMap<>();
    }

    public HashMap<Flight, Schedule> getFlightPlans(){
        return flightPlans;
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
        if(checkFlight(flightNum)){
            flightPlans.remove(getFlight(flightNum));
        }else{
            throw new IllegalArgumentException("Flight not in plans")
        }
    }

    /**checks if flight exists in user's hashmap
     * input - flight number
     * output - boolean
     */
    public boolean checkFlight(String flightNum){
        throw new RuntimeException("Not yet implemented");
    }

    /**if checkFlight returned true, searches through flights in flightplans and returns flight based on flightnum
     * input - flight number
     * output - flight
     */
    public Flight getFlight(String flightNum){
        for(Flight flight : flightPlans.keySet()){
            if(flight.getFlightNumber().equals(flightNum)){
                return flight;
            }
        }
    }

    /**creates schedule for specific flight and adds it to hashmap
     * input - flight number
     * checks if flight exists in user's map
     * output - void
     * @throws IllegalArgumentException if flight not in hashmap
     */
    public void createSchedule(String flightNum){
        if(checkFlight(flightNum)){
            Schedule schedule = new Schedule();
            flightPlans.put(getFlight(flightNum), schedule);
        }else{
            throw new IllegalArgumentException("Flight not in plans");
        }
    }

    /**gets schedule for specific flight 
     * input - flight number
     * checks if flight exists in user's map
     * output - schedule
     * @throws IllegalArgumentException if flight not in hasmap
    */
    public Schedule getSchedule(String flightNum){
        if(checkFlight(flightNum)){
            return flightPlans.get(getFlight(flightNum));
        }else{
            throw new IllegalArgumentException("Flight not in plans");
        }
    }

    /**randomly generates schedule for specific flight
     * input - flight number
     * checks if flight exists in user's map
     * output - void
     * @throws IllegalArgumentException if flight not in hashmap
     */
    public void randomSchedule(String flightNum){
        if(checkFlight(flightNum)){
            Schedule schedule = new Schedule();
            schedule.randomSchedule();
            flightPlans.put(getFlight(flightNum), schedule);
        }else{
            throw new IllegalArgumentException("Flight not in plans");
        }
    }

    /**updates schedule for specific flight
     * input - flight number and new schedule
     * checks if flight exists in user's map
     * output - void
     * @throws IllegalArgumentException if flight not in hashmap
     */
    public void updateSchedule(String flightNum, Schedule schedule){
        if(checkFlight(flightNum)){
            flightPlans.put(getFlight(flightNum), schedule);
        }else{
            throw new IllegalArgumentException("Flight not in plans");
        }
    }

    

}
