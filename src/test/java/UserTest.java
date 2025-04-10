
import static org.junit.Assert.assertThrows;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Test;

import layout.Airport;
import layout.Business;
import layout.Gate;
import layout.POI;
import layout.Terminal;

public class UserTest {
    @Test
    public void userConstructorTest() {
        ArrayList<User> users = new ArrayList<>();
        Passenger rebecca = new Passenger("Rebecca", "rje158", "123", "redson@ithaca.edu");
        users.add(rebecca);
        assertEquals(1, users.size());
        assertEquals("Rebecca", rebecca.getName());
        assertEquals("redson@ithaca.edu", rebecca.getEmail());
        assertTrue(rebecca.checkCredentials("rje158", "123"));
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
        assertEquals(2, users.size());
        assertEquals("Noah", noah.getName());
        assertEquals("noed", noah.getUsername());
        assertEquals("no@gmail.com", noah.getEmail());
        assertTrue(noah.checkCredentials("noed", "789"));
        Passenger lindsay = new Passenger("Lindsay", "linds", "900", "linds@gmail.com");
        users.add(lindsay);
        assertEquals(3, users.size());
        assertEquals("Lindsay", lindsay.getName());
        assertEquals("linds", lindsay.getUsername());
        assertEquals("linds@gmail.com", lindsay.getEmail());
        assertTrue(lindsay.checkCredentials("linds", "900"));
        assertThrows(IllegalArgumentException.class, () -> lindsay.updateEmail("lindsay"));
    }

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

}
