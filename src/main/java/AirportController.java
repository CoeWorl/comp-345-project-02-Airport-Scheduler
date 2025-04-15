import java.util.HashMap;
import java.util.HashSet;

import layout.Airport;

public class AirportController {

    private Static AirportController instance;

    private HashMap<String, Flight> flights;
    private HashMap<String, Airport> airports;
    private HashSet<User> users;

    private AirportController(){
        flights = new HashMap<>();
        airports = new HashMap<>();
        users = new HashSet<>();
    }

    public static AirportController getInstance(){
        if(instance == null){
            instance = new AirportController();
        }
        return instance;
    }

    public HashMap<String, Flight> getFlights(){
        return flights;
    }

    public HashMap<String, Airport> getAirports(){
        return airports;
    }

    public HashSet<User> getUsers(){
        return users;
    }

    /*adds new flight to map
     * input - flight
     * output - void
     * @throws IllegalArgumentException if flight already in map
    */
    public void addFlight(Flight flight){
        if(flights.containsKey(flight.getFlightNumber())){
            throw new IllegalArgumentException("Flight already exists");
        }else{
            flights.put(flight.getFlightNumber(), flight);
        }
    }

    /*adds new airport to map
     * input - airport code, airport
     * output - void
     * @throws IllegalArgumentException if airport already in map
     */
    public void addAirport(String code, Airport airport){
        if(airports.containsKey(code)){
            throw new IllegalArgumentException("Airport already exists");
        }else{
            airports.put(code, airport);
        }
    }

    /*adds new user to set
    *input - user
    *output - void
    * @throws IllegalArgumentException if user already in set
     */
    public void addUser(User user){
        if(users.contains(user)){
            throw new IllegalArgumentException("User already exists");
        }else{
            users.add(user);
        }
    }
    
}
