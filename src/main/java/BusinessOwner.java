public class BusinessOwner extends User {

    private Business business;
    
    public BusinessOwner(String name, String username, String password, String email){
        super(name, username, password, email);
    }

    /**add activity to promote business
     * inputs - name and type of activity
     * outputs - none, activity added to business
     */
    public void addActivity(String name, String type){
        throw new RuntimeException("Not yet implemented");
    }

    /**remove activity from business
     * inputs - none
     * outputs - none, activity removed from business
     */
    public void removeActivity(){
        throw new RuntimeException("Not yet implemented");
    }
    
    public Business getBusiness(){
        throw new RuntimeException("not yet implemented");
    }

    /**removes business
     * inputs - none
     * outputs - none, business removed from business owner and airport owner's business list
     */
    public void closeBusiness(){
        throw new RuntimeException("not yet implemented");
    }
}
