import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import layout.Business;
import layout.Terminal;

public class BusinessTest {

    @Test
    public void businessConstructorTest(){ //Unit tests
        Business business = new Business("business", new Terminal("Terminal 1", 1, null, "New York Airport"), "restaurant", "9am-3pm");
        assertEquals(business.getName(), "business"); //Equivalence class: valid business, border case: no
        assertEquals(business.getType(), "restaurant");
        assertEquals(business.getHours(), "9am-3pm");
        assertEquals(business.getTerminal(), 1);
        assertEquals(business.getActivity(), null); //Equivalence class: no activity, border case: no
        business.updateHours("12pm-8pm");
        assertEquals(business.getHours(), "12pm-8pm"); //Equivalence class: updated hours, border case: no
    }
}
