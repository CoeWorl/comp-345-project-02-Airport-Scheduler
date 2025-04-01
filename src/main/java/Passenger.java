import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import layout.POI;
import layout.Terminal;

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
     * searches for flight in database/json file and adds to hashmap with empty schedule
     * output - void
     * @throws IllegalArgumentException if flight already in plans
     * @throws IllegalArgumentException if flight does not exist
     */
    public void addFlight(String flightNum){
        if(checkFlight(flightNum)){
            throw new IllegalArgumentException("Flight already in plans");
        }else{
            HashMap<String, Flight> flights = AirportController.getFlights();
            if(flights.containsKey(flightNum)){
                Flight flight = flights.get(flightNum);
                flightPlans.put(flight, new Schedule(flight.getDepartureTime(), flight.getSrc(), flight.getTerminal()));
            }else{
                throw new IllegalArgumentException("Flight does not exist");
            }
        }
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
            throw new IllegalArgumentException("Flight not in plans");
        }
    }

    /**checks if flight exists in user's hashmap
     * input - flight number
     * output - boolean
     */
    public boolean checkFlight(String flightNum){
        for(Flight flight : flightPlans.keySet()){
            if(flight.getFlightNumber().equals(flightNum)){
                return true;
            }
        }
        return false;
    }

    /**if checkFlight returned true, searches through flights in flightplans and returns flight based on flightnum
     * input - flight number
     * output - flight
     * @throws IllegalArgumentException if flight not in hashmap
     */
    public Flight getFlight(String flightNum){
        Flight flight = null;
        for(Flight f : flightPlans.keySet()){
            if(f.getFlightNumber().equals(flightNum)){
                flight = f;
            }
        }
        if(flight == null){
            throw new IllegalArgumentException("flight not in plans");
        }else{
            return flight;
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
            Flight flight = getFlight(flightNum);
            Schedule schedule = new Schedule(flight.getDepartureTime(), flight.getSrc(), flight.getTerminal());
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
            Flight flight = getFlight(flightNum);
            Schedule schedule = new Schedule(flight.getDepartureTime(), flight.getSrc(), flight.getTerminal());
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

    /**adds poi to schedule for specific flight
     * input - flight number and poi
     * checks if flight exists in user's map
     * output - void, schedule connected to flight updated
     * @throws IllegalArgumentException if flight not in hashmap
     * @throws IllegalArgumentException if poi not in terminal
     */
    public void addPOItoSchedule(String flightnum, POI poi){
        if(checkFlight(flightnum)){
            Schedule schedule = getSchedule(flightnum);
            schedule.addPOI(poi);
        }else{
            throw new IllegalArgumentException("Flight not in plans");
        }
    }

    /**adds random poi of type restaurant to schedule if one exists in terminal
     * input - flight number
     * output - void, schedule connected to flight updated
     * @throws IllegalArgumentException if flight not in hashmap
     * @throws IllegalArgumentException if no restaurants in terminal
     */
    public void addRandomRestaurant(String flightnum){
        if(checkFlight(flightnum)){
            Flight flight = getFlight(flightnum);
            Terminal terminal = flight.getTerminal();
            ArrayList<POI> restaurants = terminal.getRestaurants();
            if(restaurants.size() > 0){
                Random rand = new Random();
                int index = rand.nextInt(restaurants.size());
                POI restaurant = restaurants.get(index);
                Schedule schedule = getSchedule(flightnum);
                schedule.addPOI(restaurant);
            }else{
                throw new IllegalArgumentException("no restaurants in terminal");
            }
        }else{
            throw new IllegalArgumentException("Flight not in plans");
        }
    }

    /**adds random poi of type shop to schedule if one exists in terminal
     * input - flight number
     * output - void, schedule connected to flight updated
     * @throws IllegalArgumentException if flight not in hashmap
     * @throws IllegalArgumentException if no shops in terminal
     */
    public void addRandomShop(String flightnum){
        if(checkFlight(flightnum)){
            Flight flight = getFlight(flightnum);
            Terminal terminal = flight.getTerminal();
            ArrayList<POI> shops = terminal.getShops();
            if(shops.size() > 0){
                Random rand = new Random();
                int index = rand.nextInt(shops.size());
                POI shop = shops.get(index);
                Schedule schedule = getSchedule(flightnum);
                schedule.addPOI(shop);
            }else{
                throw new IllegalArgumentException("no shops in terminal");
            }
        }else{
            throw new IllegalArgumentException("Flight not in plans");
        }
    }

}
