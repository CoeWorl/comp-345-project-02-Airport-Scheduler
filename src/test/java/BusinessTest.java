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
}
