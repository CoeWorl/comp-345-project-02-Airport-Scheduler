package activity;
import layout.Business;

public class Activity {
    private String name;
    private String type;
    private Business location;
    private boolean active;

    public Activity(String name, String type, Business location){
        this.name = name;
        this.type = type;
        this.location = location;
        this.active = true;
    }

    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public Business getLocation(){
        return location;
    }

    /**checks if activity is current
     * input - none
     * output - boolean
     */
    public boolean isActive(){
        return active;
    }

    /**when owner removes activity from business, activity set to inactive
     * input - none
     * output - none, active set to false
     */
    public void endActivity(){
        active = false;
    }
}
