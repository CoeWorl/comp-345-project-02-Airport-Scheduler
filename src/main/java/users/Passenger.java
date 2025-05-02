package users;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import layout.Airport;
import layout.Gate;
import layout.POI;
import layout.Terminal;

//test
public class Passenger extends User{

    private HashMap<Flight, Schedule> flightPlans;
    private AirportController controller;

    public Passenger(String name, String username, String password, String email, AirportController controller) {
        super(name, username, password, email);
        this.controller = controller;
        flightPlans = new HashMap<>();
    }

    @JsonCreator
    public Passenger(
            @JsonProperty("name") String name,
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("email") String email,
            @JsonProperty("overall_preferences") ArrayList<Overall_Preferences> overallPreferences,
            @JsonProperty("food_preferences") ArrayList<Food_Preferences> foodPreferences,
            @JsonProperty("beverage_preferences") ArrayList<Beverage_Preferences> beveragePreferences,
            @JsonProperty("shopping_preferences") ArrayList<Shopping_Preferences> shoppingPreferences,
            @JsonProperty("recreation_preferences") ArrayList<Recreation_Preferences> recreationPreferences
    ) {
        super(name, username, password, email, overallPreferences, foodPreferences, beveragePreferences, shoppingPreferences, recreationPreferences);
    }

    public Passenger(String name, String username, String password, String email) {
        super(name, username, password, email);
    }

    public HashMap<Flight, Schedule> getFlightPlans(){
        return flightPlans;
    }

    /**adds a new flight to hashmap
     * input - flight number
     * searches for flight in airport controller and adds to hashmap with empty schedule
     * output - void
     * @throws IllegalArgumentException if flight already in plans
     * @throws IllegalArgumentException if flight does not exist
     */
    public void addFlight(String flightNum){
        if(checkFlight(flightNum)){
            throw new IllegalArgumentException("Flight already in plans");
        }else{
            HashMap<String, Flight> flights = controller.getFlights();;
            if(flights.containsKey(flightNum)){
                Flight flight = flights.get(flightNum);
                flightPlans.put(flight, new Schedule(flight.getDepartureTime(), flight.getSrc(), flight.getTerminal()));
            }else{
                throw new IllegalArgumentException("Flight does not exist");
            }
        }
    }

    /**adds new flight to hashmap
     * input - flight number, src, dest, departure time, arrival time, status, terminal, gate
     * searches for flight in airport controller and adds to hashmap with empty schedule
     * or creates new flight and adds to hashmap with empty schedule
     * output - void
     * @throws IllegalArgumentException if flight already in plans
     * @throws IllegalArgumentException if src or dest airport not in system
     */
    public void addFlightManual(String flightNum, String srcCode, String destCode, String deptTime, String arrTime, Integer terminalNum, String gate){
        if(checkFlight(flightNum)){
            throw new IllegalArgumentException("Flight already in plans");
        }else if(controller.getFlights().containsKey(flightNum)){
            Flight flight = controller.getFlights().get(flightNum);
            flightPlans.put(flight, new Schedule(flight.getDepartureTime(), flight.getSrc(), flight.getTerminal()));
        }else{
            if(controller.getAirports().containsKey(srcCode) && controller.getAirports().containsKey(destCode)){
                Airport src = controller.getAirports().get(srcCode);
                Airport dest = controller.getAirports().get(destCode);
                HashMap<Integer, Terminal> terminals = (HashMap<Integer, Terminal>) src.getTerminalsMap();
                Terminal term = terminals.get(terminalNum);
                Gate gate1 = (Gate) term.getGates().get(gate);
                Long deptTimeLong = Long.parseLong(deptTime);
                Long arrTimeLong = Long.parseLong(arrTime);
                Flight flight = new Flight(flightNum, src, dest, deptTimeLong, arrTimeLong, "on time", term, gate1);
                flightPlans.put(flight, new Schedule(deptTimeLong, src, term));
            }else{
                throw new IllegalArgumentException("Airport does not exiist");
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
    public void randomSchedule(String flightNum, int numPOIs){
        if(checkFlight(flightNum)){
            Flight flight = getFlight(flightNum);
            Schedule schedule = new Schedule(flight.getDepartureTime(), flight.getSrc(), flight.getTerminal());
            schedule.randomSchedule(numPOIs);
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
            if(restaurants.isEmpty()){
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
            if(shops.isEmpty()){
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

    /**returns random restaurant name
     * input - flight number
     * checks if flight exists in user's map
     * output - string
     * @throws IllegalArgumentException if flight not in hashmap
     * @throws IllegalArgumentException if no restaurants in terminal
     */
    public String randomRestaurantRecommendation(String flightNum){
        if(checkFlight(flightNum)){
            Flight flight = getFlight(flightNum);
            Terminal terminal = flight.getTerminal();
            ArrayList<POI> restaurants = terminal.getRestaurants();
            if(!restaurants.isEmpty()){
                Random rand = new Random();
                int index = rand.nextInt(restaurants.size());
                POI restaurant = restaurants.get(index);
                return "Recommended Restaurant: " + restaurant.getName();
            }else{
                throw new IllegalArgumentException("no restaurants in terminal");
            }
        }else{
            throw new IllegalArgumentException("Flight not in plans");
        }
    }

    /**returns random shop name
     * input - flight number
     * checks if flight exists in user's map
     * output - string
     * @throws IllegalArgumentException if flight not in hashmap
     * @throws IllegalArgumentException if no shops in terminal
     */
    public String randomShopRecommendation(String flightNum){
        if(checkFlight(flightNum)){
            Flight flight = getFlight(flightNum);
            Terminal terminal = flight.getTerminal();
            ArrayList<POI> shops = terminal.getShops();
            if(!shops.isEmpty()){
                Random rand = new Random();
                int index = rand.nextInt(shops.size());
                POI shop = shops.get(index);
                return "Recommended Shop: " + shop.getName();
            }else{
                throw new IllegalArgumentException("no restaurants in terminal");
            }
        }else{
            throw new IllegalArgumentException("Flight not in plans");
        }
    }

    /**adds specific restaurant to schedule
     * input - flight number and restaurant name
     * output - void
     * @throws IllegalArgumentException if flight not in hashmap
     * @throws IllegalArgumentException if restaurant not in terminal
     */
    public void addRestaurantToSchedule(String flightNum, String restaurantName){
        if(checkFlight(flightNum)){
            Flight flight = getFlight(flightNum);
            Terminal terminal = flight.getTerminal();
            ArrayList<POI> restaurants = terminal.getRestaurants();
            for(POI restaurant : restaurants){
                if(restaurant.getName().equals(restaurantName)){
                    Schedule schedule = getSchedule(flightNum);
                    schedule.addPOI(restaurant);
                    return;
                }
            }
            throw new IllegalArgumentException("Restaurant not in terminal");
        }else{
            throw new IllegalArgumentException("Flight not in plans");
        }
    }

    /**adds specific shop to schedule
     * input - flight number and shop name
     * output - void
     * @throws IllegalArgumentException if flight not in hashmap
     * @throws IllegalArgumentException if shop not in terminal
     */
    public void addShopToSchedule(String flightNum, String shopName){
        if(checkFlight(flightNum)){
            Flight flight = getFlight(flightNum);
            Terminal terminal = flight.getTerminal();
            ArrayList<POI> shops = terminal.getShops();
            for(POI shop : shops){
                if(shop.getName().equals(shopName)){
                    Schedule schedule = getSchedule(flightNum);
                    schedule.addPOI(shop);
                    return;
                }
            }
            throw new IllegalArgumentException("Shop not in terminal");
        }else{
            throw new IllegalArgumentException("Flight not in plans");
        }
    }

}

