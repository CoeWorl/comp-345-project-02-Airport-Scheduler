package layout;

import activity.Activity; 


public class Business extends POI{
    private Activity activity;
    private String type;
    private String hours;

    public Business(String name, Terminal terminal, String type, String hours){
        super(name, terminal);
        this.activity = null;
        this.type = type;
        this.hours = hours;
    }

    public String getType(){
        return type;
    }

    public String getHours(){
        return hours;
    }

    public Activity getActivity(){
        return activity;
    }

    /**checks if business already has activity and adds if not
     * input - name and type of activity
     * output - none, activity added to business
     * @throws IllegalArgumentException if business already has activity
     */
    public void addActivity(String name, String type){
        if(activity == null){
            activity = new Activity(name, type, this);
        }else{
            throw new IllegalArgumentException("Business already has activity");
        }
    }

    /**removes activity if business has one
     * input - none
     * output - none, activity removed from business
     * @throws IllegalArgumentException if business does not have activity
     */
    public void removeActivity(){
        if(activity != null){
            activity.endActivity();
            activity = null;
        }else{
            throw new IllegalArgumentException("Business does not have activity");
        }
    }

    /**checks if business currently has an activity
     * input - none
     * output - boolean
     */
    public boolean hasActivity(){
        if(activity != null){
            return true;
        }else{
            return false;
        }
    }

    public void updateHours(String hours){
        this.hours = hours;
    }
}
