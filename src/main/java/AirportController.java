import java.util.HashMap;
import java.util.HashSet;

import layout.Airport;

public class AirportController {

    private HashMap<String, Flight> flights;
    private HashMap<String, Airport> airports;
    private HashSet<User> users;

    public AirportController(){
        flights = new HashMap<>();
        airports = new HashMap<>();
        users = new HashSet<>();
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

    public void addFlight(Flight flight){
        if(flights.containsKey(flight.getFlightNumber())){
            throw new IllegalArgumentException("Flight already exists");
        }else{
            flights.put(flight.getFlightNumber(), flight);
        }
    }
    
}
