

import static org.junit.Assert.assertThrows;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import layout.*;
import org.junit.Test;
//test
public class UserTest {

    @Test
    public void userConstructorTest() {
        AirportController ac = new AirportController();
        ArrayList<User> users = new ArrayList<>();
        Passenger rebecca = new Passenger("Rebecca", "rje158", "123", "redson@ithaca.edu");
        users.add(rebecca);
        assertEquals(1, users.size());
        assertEquals("Rebecca", rebecca.getName());
        assertEquals("redson@ithaca.edu", rebecca.getEmail());
        assertTrue(rebecca.checkCredentials("rje158", "123"));
        assertTrue(ac.getUsers().contains(rebecca));
        rebecca.updatePassword("123", "456");
        assertTrue(rebecca.checkCredentials("rje158", "456"));
        assertFalse(rebecca.checkCredentials("rje158", "123"));
        assertThrows(IllegalArgumentException.class, () -> rebecca.updatePassword("123", "333"));
        rebecca.updateEmail("rje158@gmail.com");
        assertEquals("rje158@gmail.com", rebecca.getEmail());
        rebecca.updateUsername("rje158");
        assertEquals("rje158", rebecca.getUsername());
        rebecca.updateName("Becca");
        assertEquals("Becca", rebecca.getName());
        Owner noah = new Owner("Noah", "noed", "789", "no@gmail.com");
        users.add(noah);
        assertEquals(users.size(), 2);
        assertEquals(noah.getName(), "Noah");
        assertEquals(noah.getUsername(), "noed");
        assertEquals(noah.getEmail(), "no@gmail.com");
        assertTrue(ac.getUsers().contains(noah));
        assertTrue(noah.checkCredentials("noed", "789"));
        Passenger lindsay = new Passenger("Lindsay", "linds", "900", "linds@gmail.com");
        users.add(lindsay);
        assertEquals(users.size(), 3);
        assertEquals(lindsay.getName(), "Lindsay");
        assertEquals(lindsay.getUsername(), "linds");
        assertEquals(lindsay.getEmail(), "linds@gmail.com");
        assertTrue(ac.getUsers().contains(lindsay));
        assertTrue(lindsay.checkCredentials("linds", "900"));
        assertThrows(IllegalArgumentException.class, () -> lindsay.updateEmail("lindsay"));
    }

    @Test
    public void passengerTest(){
        AirportController ac = new AirportController();
        Airport jfk = new Airport("JFK", "John F. Kennedy International Airport");
        Airport lax = new Airport("LAX", "Los Angeles International Airport");
        ac.addAirport("JFK", jfk);
        ac.addAirport("LAX", lax);
        Terminal terminal = new Terminal("Terminal 1", 1, new Gate("A1", 1, false), "JFK");
        Flight f1 = new Flight("AA1234", jfk, lax, 1743528600, 	1743543000, "on-time", terminal, new Gate("A1", 1, false));
        Flight f2 = new Flight("AA5678", lax, jfk, 1743544800, 1743560100, "on-time", terminal, new Gate("A2", 1, false));
        ac.addFlight(f1);
        ac.addFlight(f2);
        assertEquals(ac.getAirports().size(), 2);
        assertEquals(ac.getFlights().size(), 2);
        Passenger rebecca = new Passenger("Rebecca", "redson", "123", "redson@ithaca.edu");
        assertThrows(IllegalArgumentException.class, () -> rebecca.addFlight("ab23"));
        assertThrows(IllegalArgumentException.class, () -> rebecca.removeFlight("ab23"));
        assertThrows(IllegalArgumentException.class, () -> rebecca.createSchedule("ab23"));
        assertThrows(IllegalArgumentException.class, () -> rebecca.randomSchedule("ab23", 1));
        assertThrows(IllegalArgumentException.class, () -> rebecca.getSchedule("ab23"));
        assertThrows(IllegalArgumentException.class, () -> rebecca.updateSchedule("ab23", new Schedule(rebecca.getFlight("ab23").getDeptTime(), jfk, terminal)));
        assertFalse(rebecca.checkFlight("ab23"));
        HashMap<Flight, Schedule> flightPlans = rebecca.getFlightPlans();
        assertTrue(flightPlans.isEmpty());
        rebecca.addFlight("AA1234");
        assertEquals(rebecca.getFlightPlans().size(), 1);
        rebecca.removeFlight("AA1234");
        assertFalse(rebecca.checkFlight("AA1234"));
        assertThrows(IllegalArgumentException.class, () -> rebecca.removeFlight("AA1234"));
        assertThrows(IllegalArgumentException.class, () -> rebecca.createSchedule("AA1234"));
        assertTrue(rebecca.getFlightPlans().isEmpty());
        rebecca.addFlight("AA5678");
        rebecca.addFlight("AA1234");
        assertEquals(rebecca.getFlightPlans().size(), 2);
        rebecca.createSchedule("AA1234");
        Schedule schedule = rebecca.getSchedule("AA1234");
        assertEquals(schedule.getAirport().getName(), "John F. Kennedy International Airport");
        assertEquals(schedule.getTerminal().getName(), "Terminal 1");
        rebecca.randomSchedule("AA5678", 0);
        Schedule randSchedule = rebecca.getSchedule("AA5678");
        assertEquals(randSchedule.getAirport().getName(), "Los Angeles International Airport");
        assertEquals(randSchedule.getTerminal().getName(), "Terminal 2");
        Flight flight = rebecca.getFlight("AA1234");
        Schedule newSched =  new Schedule(flight.getDepartureTime(), flight.getSrc(), flight.getTerminal());
        rebecca.updateSchedule("AA1234", newSched);
        Schedule updatedSched = rebecca.getSchedule("AA1234");
        assertEquals(newSched, updatedSched);
        assertEquals(updatedSched.getAirport().getName(), "John F. Kennedy International Airport");
        assertEquals(updatedSched.getTerminal().getName(), "Terminal 1");
        rebecca.removeFlight("AA1234");
        assertEquals(rebecca.getFlightPlans().size(), 1);
        rebecca.removeFlight("AA5678");
        assertTrue(rebecca.getFlightPlans().isEmpty());
    }

//    @Test
//    public void updateScheduleTest(){
//        AirportController ac = new AirportController();
//        Passenger rebecca = new Passenger("Rebecca", "redson", "abc", "redson@gmail.com");
//        Airport jfk = new Airport("JFK", "John F. Kennedy International Airport");
//        Airport lax = new Airport("LAX", "Los Angeles International Airport");
//        FlightJson flightJson = new FlightJson("src/test/resources/testFlights.json");
//        flightJson.createFlights();
//        rebecca.addFlight("AA1234");
//        Terminal term = rebecca.getFlight("AA1234").getTerminal();
//        Business bus1 = new Business("Starbucks", term.getTerminalNumber(), "restaurant", "9am-5pm");
//        Business bus2 = new Business("Bookstore", term.getTerminalNumber(), "shop", "9am-8pm");
//        Business bus3 = new Business("McDonald's", term.getTerminalNumber(), "restaurant", "9am-9pm");
//        Business bus4 = new Business("Newstand",term.getTerminalNumber(), "shop", "9am-7pm");
//        assertEquals(rebecca.getFlightPlans().size(), 1);
//        rebecca.addRandomRestaurant("AA1234");
//        assertEquals(rebecca.getSchedule("AA1234").getRestaurants().size(), 1);
//        rebecca.addRandomShop("AA1234");
//        assertEquals(rebecca.getSchedule("AA1234").getShops().size(), 1);
//        assertTrue(rebecca.getSchedule("AA1234").getRestaurants().contains(bus1) | rebecca.getSchedule("AA1234").getRestaurants().contains(bus2));
//        assertTrue(rebecca.getSchedule("AA1234").getShops().contains(bus3) | rebecca.getSchedule("AA1234").getShops().contains(bus4));
//        rebecca.addRandomPOI("AA1234");
//        assertEquals(rebecca.getSchedule("AA1234").getPOIs().size(), 3);
//        Passenger noah = new Passenger("Noah", "noed", "123", "noed@gmail.com");
//        noah.addFlight("AA1234");
//        String restaurantRec = noah.randomRestaurantRecommendation("AA1234");
//        String restRecName = restaurantRec.split[": "][1];
//        assertTrue(term.getRestaurants().contains(restaurantRec));
//        noah.addRestaurantToSchedule("AA1234", restRecName);
//        assertTrue(noah.getSchedule("AA1234").getRestaurants().contains(restaurantRec));
//        String shopRec = noah.randomShopRecommendation("AA1234");
//        String shopRecName = shopRec.split[": "][1];
//        assertTrue(term.getShops().contains(shopRec));
//        noah.addShopToSchedule("AA1234", shopRecName);
//        assertTrue(noah.getSchedule("AA1234").getShops().contains(shopRec));
//        assertThrows(IllegalArgumentException.class, () -> noah.randomRestaurantRecommendation("AA5678")); //flight not in plans
//        assertThrows(IllegalArgumentException.class, () -> noah.randomShopRecommendation("AA5678"));
//        assertThrows(IllegalArgumentException.class, () -> noah.addRestaurantToSchedule("AA5678", restRecName));
//        assertThrows(IllegalArgumentException.class, () -> noah.addShopToSchedule("AA5678", shopRecName));
//        assertThrows(IllegalArgumentException.class, () -> noah.addRestaurantToSchedule("AA1234", "Dunkin'")); //restaurant not in terminal
//        assertThrows(IllegalArgumentException.class, () -> noah.addShopToSchedule("AA1234", "General Store")); //shop not in terminal
//    }

    @Test
    public void userPreferencesTest(){
        Passenger rebecca = new Passenger("Rebecca", "rje158", "123", "redson@ithaca.edu");
        ArrayList<User.Overall_Preferences> overall_preferences = new ArrayList<>();
        overall_preferences.add(User.Overall_Preferences.BEVERAGES);
        overall_preferences.add(User.Overall_Preferences.FOOD);
        overall_preferences.add(User.Overall_Preferences.RECREATION);
        overall_preferences.add(User.Overall_Preferences.SHOPPING);
        ArrayList<User.Beverage_Preferences> beverage_preferences = new ArrayList<>();
        beverage_preferences.add(User.Beverage_Preferences.TEA);
        beverage_preferences.add(User.Beverage_Preferences.COFFEE);
        beverage_preferences.add(User.Beverage_Preferences.SODA);
        beverage_preferences.add(User.Beverage_Preferences.JUICE);
        beverage_preferences.add(User.Beverage_Preferences.ALCOHOL);

        rebecca.setPreferences(overall_preferences, null, beverage_preferences, null, null);
        assertEquals(4, rebecca.getOverall_preferences().size());
        assertTrue(rebecca.getOverall_preferences().contains(User.Overall_Preferences.BEVERAGES));
        assertTrue(rebecca.getOverall_preferences().contains(User.Overall_Preferences.FOOD));
        assertTrue(rebecca.getOverall_preferences().contains(User.Overall_Preferences.RECREATION));
        assertTrue(rebecca.getOverall_preferences().contains(User.Overall_Preferences.SHOPPING));

        assertEquals(5, rebecca.getBeverage_preferences().size());
        assertTrue(rebecca.getBeverage_preferences().contains(User.Beverage_Preferences.TEA));
        assertTrue(rebecca.getBeverage_preferences().contains(User.Beverage_Preferences.COFFEE));
        assertTrue(rebecca.getBeverage_preferences().contains(User.Beverage_Preferences.SODA));
        assertTrue(rebecca.getBeverage_preferences().contains(User.Beverage_Preferences.JUICE));
        assertTrue(rebecca.getBeverage_preferences().contains(User.Beverage_Preferences.ALCOHOL));
    }

    @Test
    public void userSetPreferencesInvalidTest() {
        Passenger rebecca = new Passenger("Rebecca", "rje158", "123", "redson@ithaca.edu");

        // Invalid overall preferences size
        ArrayList<User.Overall_Preferences> invalidOverallPreferences = new ArrayList<>();
        invalidOverallPreferences.add(User.Overall_Preferences.FOOD);

        assertThrows(IllegalArgumentException.class, () ->
                rebecca.setPreferences(invalidOverallPreferences, null, null, null, null)
        );

        // Invalid food preferences size
        ArrayList<User.Overall_Preferences> validOverallPreferences = new ArrayList<>();
        validOverallPreferences.add(User.Overall_Preferences.FOOD);
        validOverallPreferences.add(User.Overall_Preferences.BEVERAGES);
        validOverallPreferences.add(User.Overall_Preferences.RECREATION);
        validOverallPreferences.add(User.Overall_Preferences.SHOPPING);

        ArrayList<User.Food_Preferences> invalidFoodPreferences = new ArrayList<>();
        invalidFoodPreferences.add(User.Food_Preferences.FAST_FOOD);

        assertThrows(IllegalArgumentException.class, () ->
                rebecca.setPreferences(validOverallPreferences, invalidFoodPreferences, null, null, null)
        );
    }

    @Test
    public void testUserSerialization() throws IOException {
        Passenger rebecca = new Passenger("Rebecca", "rje158", "123", "redson@ithaca.edu");
        AirportController airportController = new AirportController();
        airportController.addFlight(new Flight("AA1234", new Airport("JFK", "New York Airport"), new Airport("LAX", "Los Angeles Airport"), 1743528600, 1743543000, "on-time", new Terminal("Terminal 1", 1, new Gate("A1", 1, false), "JFK"), new Gate("A1", 1, false)));
        rebecca.addFlight("AA1234");
        ArrayList<User.Overall_Preferences> overall_preferences = new ArrayList<>();
        overall_preferences.add(User.Overall_Preferences.BEVERAGES);
        overall_preferences.add(User.Overall_Preferences.FOOD);
        overall_preferences.add(User.Overall_Preferences.RECREATION);
        overall_preferences.add(User.Overall_Preferences.SHOPPING);
        ArrayList<User.Beverage_Preferences> beverage_preferences = new ArrayList<>();
        beverage_preferences.add(User.Beverage_Preferences.TEA);
        beverage_preferences.add(User.Beverage_Preferences.COFFEE);
        beverage_preferences.add(User.Beverage_Preferences.SODA);
        beverage_preferences.add(User.Beverage_Preferences.JUICE);
        beverage_preferences.add(User.Beverage_Preferences.ALCOHOL);

        rebecca.setPreferences(overall_preferences, null, beverage_preferences, null, null);
        String filePath = "src/test/resources/rebecca.json";
        Json.toJsonFile(filePath, rebecca);

        Passenger deserializedRebecca = Json.fromJsonFile(filePath, Passenger.class);

        assertEquals(rebecca.getName(), deserializedRebecca.getName());
        assertEquals(rebecca.getUsername(), deserializedRebecca.getUsername());
        assertEquals(rebecca.getEmail(), deserializedRebecca.getEmail());
        assertEquals(rebecca.checkCredentials("rje158", "123"), deserializedRebecca.checkCredentials("rje158", "123"));
    }

//    @Test
//    public void passengerTest(){
//        AirportController ac = new AirportController();
//        Terminal terminal = new Terminal("Terminal 1");
//        Airport jfk = new Airport();
//        Airport lax = new Airport();
//        Flight f1 = new Flight("AA1234", jfk, lax, 1743528600, 	1743543000, "on-time", terminal, new Gate("A1", terminal, false));
//        Flight f2 = new Flight("AA5678", lax, jfk, 1743544800, 1743560100, "on-time", terminal, new Gate("A2", terminal, false));
//        ac.addFlight(f1);
//        ac.addFlight(f2);
//        Passenger rebecca = new Passenger("Rebecca", "redson", "123", "redson@ithaca.edu");
//        assertThrows(IllegalArgumentException.class, () -> rebecca.addFlight("ab23"));
//        assertThrows(IllegalArgumentException.class, () -> rebecca.removeFlight("ab23"));
//        assertThrows(IllegalArgumentException.class, () -> rebecca.createSchedule("ab23"));
//        assertThrows(IllegalArgumentException.class, () -> rebecca.randomSchedule("ab23"));
//        assertThrows(IllegalArgumentException.class, () -> rebecca.getSchedule("ab23"));
//        assertThrows(IllegalArgumentException.class, () -> rebecca.updateSchedule("ab23", new Schedule(rebecca.getFlight("ab23").getDeptTime(), new ArrayList<POI>())));
//        assertFalse(rebecca.checkFlight("ab23"));
//        HashMap<Flight, Schedule> flightPlans = rebecca.getFlightPlans();
//        assertTrue(flightPlans.isEmpty());
//        rebecca.addFlight("AA1234");
//        assertEquals(rebecca.getFlightPlans().size(), 1);
//        rebecca.removeFlight("AA1234");
//        assertFalse(rebecca.checkFlight("AA1234"));
//        assertThrows(IllegalArgumentException.class, () -> rebecca.removeFlight("AA1234"));
//        assertThrows(IllegalArgumentException.class, () -> rebecca.createSchedule("AA1234"));
//        assertTrue(rebecca.getFlightPlans().isEmpty());
//        rebecca.addFlight("AA5678");
//        rebecca.addFlight("AA1234");
//        assertEquals(rebecca.getFlightPlans().size(), 2);
//        rebecca.createSchedule("AA1234");
//        Schedule schedule = rebecca.getSchedule("AA1234");
//        assertEquals(schedule.getAirport().getName(), "JFK");
//        assertEquals(schedule.getTerminal().getName(), "Terminal 1");
//        rebecca.randomSchedule("AA5678", 3);
//        Schedule randSchedule = rebecca.getSchedule("AA5678");
//        assertEquals(randSchedule.getAirport().getName(), "LAX");
//        assertEquals(randSchedule.getTerminal().getName(), "Terminal 2");
//        Flight flight = rebecca.getFlight("AA1234");
//        Schedule newSched =  new Schedule(flight.getDepartureTime(), flight.getSrc(), flight.getTerminal());
//        rebecca.updateSchedule("AA1234", newSched);
//        Schedule updatedSched = rebecca.getSchedule("AA1234");
//        assertEquals(newSched, updatedSched);
//        assertEquals(updatedSched.getAirport().getName(), "JFK");
//        assertEquals(updatedSched.getTerminal().getName(), "Terminal 1");
//        rebecca.removeFlight("AA1234");
//        assertEquals(rebecca.getFlightPlans().size(), 1);
//        rebecca.removeFlight("AA5678");
//        assertTrue(rebecca.getFlightPlans().isEmpty());
//    }
//
//    @Test
//    public void ownerTest(){
//        Owner noah = new Owner("Noah", "noed", "789", "no@gmail.com");
//        HashSet<Business> businesses = noah.getBusinesses();
//        assertTrue(businesses.isEmpty());
//        Business business = new Business("restaurant", new Terminal("terminal 1"), "restaurant", "9am-3pm");
//        assertThrows(IllegalArgumentException.class, () -> noah.removeBusiness(business));
//        assertFalse(noah.checkBusiness(business));
//        noah.addBusiness(business);
//        assertTrue(noah.checkBusiness(business));
//        assertEquals(noah.getBusinesses().size(), 1);
//        assertEquals(noah.getRestaurants().size(), 1);
//        assertTrue(noah.getShops().isEmpty());
//        Business business2 = new Business("shop", new Terminal("terminal 1"), "shop", "9am-6pm");
//        assertThrows(IllegalArgumentException.class, () -> noah.addActivity(business2, "sale", "sale"));
//        noah.addBusiness(business2);
//        assertEquals(noah.getBusinesses().size(), 2);
//        assertEquals(noah.getShops().size(), 1);
//        assertThrows(IllegalArgumentException.class, () -> noah.addBusiness(business));
//        noah.addActivity(business2, "sale", "sale");
//        assertTrue(business2.hasActivity());
//        assertThrows(IllegalArgumentException.class, () -> noah.addActivity(business2, "super sale", "sale"));
//        noah.removeActivity(business2);
//        assertFalse(business2.hasActivity());
//        assertThrows(IllegalArgumentException.class, () -> noah.removeActivity(business));
//        noah.removeBusiness(business);
//        assertEquals(noah.getBusinesses().size(), 1);
//        assertTrue(noah.getRestaurants().isEmpty());
//        assertEquals(noah.getShops().size(), 1);
//        noah.removeBusiness(business2);
//        assertTrue(noah.getBusinesses().isEmpty());
//        assertTrue(noah.getShops().isEmpty());
//        assertTrue(noah.getRestaurants().isEmpty());
//    }

    @Test
    public void validEmailTest(){
        // valid email address
        assertTrue(User.validEmail("a@b.com"));   // Equivalence Class: valid email, Border case: No, valid format

        // empty string
        assertFalse(User.validEmail(""));         // Equivalence Class: invalid email (empty), Border case: Yes

        // invalid email, starts with invalid character
        assertFalse(User.validEmail("_a@gmail.com"));  // Equivalence Class: invalid email (starting with underscore), Border case: No

        // invalid email, domain missing after '@'
        assertFalse(User.validEmail("a@.com"));      // Equivalence Class: invalid email (missing domain), Border case: Yes

        // invalid email, missing '@' symbol
        assertFalse(User.validEmail("a@gmail"));     // Equivalence Class: invalid email (missing '@' symbol), Border case: No

        // valid email address from a university domain
        assertTrue(User.validEmail("redson@ithaca.edu"));  // Equivalence Class: valid email, Border case: No, valid format

        // invalid email, missing local part before '@'
        assertFalse(User.validEmail("@gmail.com"));  // Equivalence Class: invalid email (missing local part), Border case: Yes

        // invalid email, contains space
        assertFalse(User.validEmail("a b@gmail.com"));  // Equivalence Class: invalid email (contains space), Border case: No

        // invalid email, missing '@'
        assertFalse(User.validEmail("agmail.com"));  // Equivalence Class: invalid email (missing '@'), Border case: Yes
        
    }


    @Test
    public void addFlightManualTest(){
        AirportController ac = new AirportController();
        Passenger rebecca = new Passenger("Rebecca", "redson", "abc", "redson@gmail.com");
        Airport jfk = new Airport("JFK", "John F. Kennedy International Airport");
        Airport lax = new Airport("LAX", "Los Angeles International Airport");
        ac.addAirport("JFK", jfk);
        ac.addAirport("LAX", lax);
        Terminal terminal = new Terminal("Terminal 1", 1, new Gate("A1", 1, false), "JFK");
        Flight f1 = new Flight("AA1234", jfk, lax, 1743528600, 	1743543000, "on-time", terminal, new Gate("A1", 1, false));
        ac.addFlight(f1);
        rebecca.addFlightManual("AA1234", "JFK", "LAX", "12:30", "16:00", "Terminal 1", "Gate A1");
        assertEquals(rebecca.getFlightPlans().size(), 1);
        assertEquals(ac.getFlights().size(), 1);
        assertEquals(ac.getAirports().size(), 2);
        assertThrows(IllegalArgumentException.class, () -> rebecca.addFlightManual("AA1234", "JFK", "LAX", "12:30", "16:00", "Terminal 1", "Gate A1"));//flight already added
        assertThrows(IllegalArgumentException.class, () -> rebecca.addFlightManual("AA4567", "ELM", "ORL", "8:00", "10:30", "Terminal 1", "Gate A2"));//airports not in system
    }
}

