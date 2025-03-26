import java.util.HashSet;

public class AirportOwner extends User{

    private HashSet<Business> businesses;
    private Airport airport;

    public AirportOwner(String name, String username, String password, String email){
        super(name, username, password, email);
    }

    public HashSet<Business> getBusinesses(){
        throw new RuntimeException("not yet implemented");
    }

    /**adds new business to set
     * input - business
     * checks if business already exists in set and adds if not
     * output - void
     */
    public void addBusiness(Business business){
        throw new RuntimeException("not yet implemented");
    }

    /**removes business from set
     * input - business
     * checks if business exists in set and removes if so
     * output - void
     */
    public void removeBusiness(Business business){
        throw new RuntimeException("not yet implemented");
    }

    /**checks if business is in set
     * input - business
     * output - boolean
     */
    public boolean checkBusiness(Business business){
        throw new RuntimeException("not yet implemented");
    }
    
}
