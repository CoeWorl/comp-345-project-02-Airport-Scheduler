import java.util.HashSet;

public class Owner extends User{

    private HashSet<Business> businesses;

    public Owner(String name, String username, String password, String email){
        super(name, username, password, email);
    }

    public HashSet<Business> getBusinesses(){
        throw new RuntimeException("not yet implemented");
    }

    /**adds new business to set
     * input - business
     * checks if business already exists in set and adds if not
     * output - void
     * @throws IllegalArgumentException if business already exists
     */
    public void addBusiness(Business business){
        throw new RuntimeException("not yet implemented");
    }

    /**removes business from set
     * input - business
     * checks if business exists in set and removes if so
     * output - void
     * @throws IllegalArgumentException if business does not exist
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

    /**add activity to promote business
     * inputs - business and name and type of activity
     * checks if business exists in set and adds activity if so
     * outputs - none, activity added to business
     * @throws IllegalArgumentException if business does not exist
     */
    public void addActivity(Business business, String name, String type){
        throw new RuntimeException("Not yet implemented");
    }

    /**remove activity from business
     * inputs - none
     * checks if business exists in set and removes activity if so
     * outputs - none, activity removed from business
     * @throws IllegalArgumentException if business not in set
     * @throws IllegalArgumentException if activity is null
     */
    public void removeActivity(Business business){
        throw new RuntimeException("Not yet implemented");
    }

    /**returns all businesses of type restaurant
     * input - none
     * output - set of businesses
     */
    public HashSet<Business> getRestaurants(){
        throw new RuntimeException("not yet implemented");
    }

    /**returns all businesses of type shop
     * input - none
     * output - set of businesses
     */
    public HashSet<Business> getShops(){
        throw new RuntimeException("not yet implemented");
    }
    
}
