package layout;



public class Business extends POI{
    private Activity activity;
    private String type;
    private String hours;

    public Business(String name, Terminal terminal, String type, String hours){
        super(name, terminal);
        this.activity = null;
    }

    public String getType(){
        throw new RuntimeException("not yet implemented");
    }

    public String getHours(){
        throw new RuntimeException("not yet implemented");
    }

    public Activity getActivity(){
        throw new RuntimeException("not yet implemented");
    }

    /**checks if business already has activity and adds if not
     * input - name and type of activity
     * output - none, activity added to business
     * @throws IllegalArgumentException if business already has activity
     */
    public void addActivity(String name, String type){
        throw new RuntimeException("not yet implemented");
    }

    /**removes activity if business has one
     * input - none
     * output - none, activity removed from business
     * @throws IllegalArgumentException if business does not have activity
     */
    public void removeActivity(){
        throw new RuntimeException("not yet implemented");
    }

    /**checks if business currently has an activity
     * input - none
     * output - boolean
     */
    public boolean hasActivity(){
        throw new RuntimeException("not yet implemented");
    }

    public void updateHours(String hours){
        throw new RuntimeException("not yet implemented");
    }
}
