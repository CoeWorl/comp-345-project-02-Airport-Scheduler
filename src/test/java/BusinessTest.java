import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import activity.Activity;
import layout.Business;
import layout.Terminal;

public class BusinessTest {

    @Test
    public void businessConstructorTest(){
        Business business = new Business("business", new Terminal("Terminal 1", 1, null, "New York Airport"), "restaurant", "9am-3pm");
        assertEquals(business.getName(), "business");
        assertEquals(business.getType(), "restaurant");
        assertEquals(business.getHours(), "9am-3pm");
        assertEquals(business.getTerminal(), 1);
        assertEquals(business.getActivity(), null);
        business.updateHours("12pm-8pm");
        assertEquals(business.getHours(), "12pm-8pm");
    }

    @Test
    public void businessOwnerTest(){
        Owner owner = new Owner("Rebecca", "rje", "abc123", "rje@gmail.com");
        Business business = new Business("business", new Terminal("Terminal 1", 1, null, "New York Airport"), "restaurant", "9am-3pm");
        assertEquals(owner.getBusinesses().size(), 0);
        assertEquals(owner.getRestaurants().size(), 0);
        assertEquals(owner.getShops().size(), 0);
        owner.addBusiness(business);
        assertEquals(owner.getBusinesses().size(), 1);
        assertEquals(owner.getRestaurants().size(), 1);
        assertEquals(owner.getShops().size(), 0);
        assertThrows(IllegalArgumentException.class, () -> owner.addBusiness(business));
        Business business2 = new Business("business", new Terminal("terminl 1", 1, null, "New York Airport"), "shop", "9am-7pm");
        owner.addBusiness(business2);
        assertEquals(owner.getBusinesses().size(), 2);
        assertEquals(owner.getRestaurants().size(), 1);
        assertEquals(owner.getShops().size(), 1);
        owner.removeBusiness(business);
        assertEquals(owner.getBusinesses().size(), 1);
        assertEquals(owner.getRestaurants().size(), 0);
        assertEquals(owner.getShops().size(), 1);
        assertThrows(IllegalArgumentException.class, () -> owner.removeBusiness(business));
        owner.removeBusiness(business2);
        assertEquals(owner.getBusinesses().size(), 0);
        assertEquals(owner.getRestaurants().size(), 0);
        assertEquals(owner.getShops().size(), 0);
    }

    @Test
    public void businessActivityTest(){
        Business business = new Business("business", new Terminal("terminl 1", 1, null, "New York Airport"), "shop", "9am-7pm");
        assertEquals(business.getActivity(), null);
        business.addActivity("April Fools Sale", "sale");
        Activity activity = business.getActivity();
        assertEquals(activity.getName(), "April Fools Sale");
        assertEquals(activity.getType(), "sale");
        assertEquals(activity.getLocation(), business);
        assertTrue(activity.isActive());
        assertThrows(IllegalArgumentException.class, () -> business.addActivity("Super Clearance Sale", "sale"));
        business.removeActivity();
        assertEquals(business.getActivity(), null);
        assertFalse(activity.isActive());
        assertThrows(IllegalArgumentException.class, () -> business.removeActivity());
        Owner owner = new Owner("Rebecca", "rje", "abc123", "rje@gmail.com");
        owner.addBusiness(business);
        business.addActivity("Super Clearance Sale", "sale");
        Activity activity2 = business.getActivity();
        owner.removeBusiness(business);
        assertTrue(activity2.isActive());
        Business business2 = new Business("restaurant", new Terminal("terminal 1", 1, null, "New York Airport"), "restaurant", "9am-3pm");
        owner.addBusiness(business2);
        owner.addActivity(business2, "Kids Eat Free", "Family Deal");
        assertTrue(business2.hasActivity());
        assertThrows(IllegalArgumentException.class, () -> owner.addActivity(business2, "BOGO Entrees", "Sale"));
        owner.removeActivity(business2);
        assertFalse(business2.hasActivity());
        assertThrows(IllegalArgumentException.class, ()-> owner.removeActivity(business2));
    }
}
