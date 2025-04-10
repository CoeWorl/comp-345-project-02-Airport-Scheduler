import java.util.ArrayList;

public abstract class User {
    public enum Overall_Preferences {
        FOOD,
        BEVERAGES,
        SHOPPING,
        RECREATION
    }
    public enum Food_Preferences {
        FAST_FOOD,
        VEGAN,
        KOSHER,
        HALAL,
        SIT_DOWN,
        SNACKS,
        DESSERTS
    }

    public enum Beverage_Preferences {
        ALCOHOL,
        COFFEE,
        TEA,
        SODA,
        JUICE
    }

    public enum Shopping_Preferences {
        CLOTHING,
        ELECTRONICS,
        BOOKS,
        TOYS,
        SOUVENIRS
    }
    public enum Recreation_Preferences {
        GAMES,
        MOVIES,
        MUSIC,
        ART,
        SPORTS
    }

    private ArrayList<Overall_Preferences> overall_preferences;
    private String name;
    private String username;
    private String password;
    private String email;

    public User(String name, String username, String password, String email){
        this.name = name;
        this.username = username;
        this.password = password;
        if(validEmail(email)){
            this.email = email;
        }else{
            throw new IllegalArgumentException("Invalid email");
        }
        overall_preferences = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    /**Ensures correct account credentials to login
     * input - username and password
     * output - boolean
     */
    public boolean checkCredentials(String username, String password){
        if(this.username.equals(username) && this.password.equals(password)){
            return true;
        }else{
            return false;
        }
    }

    public void setOverall_preferences(ArrayList<Overall_Preferences> overall_preferences) {
        this.overall_preferences = overall_preferences;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void updateUsername(String username){
        this.username = username;
    }

    /**Checks old password before allowing password update
     * inputs - prev password and new password
     * updates password
     * @throws IllegalArgumentException if old password is incorrect
    */
    public void updatePassword(String prev, String psswrd){
        boolean check = checkCredentials(username, prev);
        if(check){
            password = psswrd;
        }else{
            throw new IllegalArgumentException("Incorrect password");
        }
    }

    /**checks if new email is valid and updates if so
     * input - email
     * output - none
     * @throws IllegalArgumentException if email is invalid
     */
    public void updateEmail(String email){
        if(validEmail(email)){
            this.email = email;
        }else{
            throw new IllegalArgumentException("Invalid email");
        }
    }

    /**checks if email is valid before it can be set for account
     * input - email
     * output - boolean
     */
    public static boolean validEmail(String email){
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        int atPosition = email.indexOf('@');
        int dotPosition = email.lastIndexOf('.');

        // Checks if '@' exists and is not the first or last character
        if (atPosition <= 0 || atPosition >= email.length() - 1) {
            return false;
        }

        // Checks if '.' exists after '@' and is not the last character
        if (dotPosition < atPosition || dotPosition >= email.length() - 1) {
            return false;
        }

        // checks part that starts with a valid character
        char firstChar = email.charAt(0);
        if (!Character.isLetterOrDigit(firstChar)) {
            return false;
        }

        // checks no spaces exist in the email
        if (email.contains(" ")) {
            return false;
        }

        // checks "@" is followed by a valid domain
        if (email.charAt(atPosition + 1) == '.' || dotPosition == atPosition + 1) {
            return false;
        }

        // checks no consecutive dots exist
        if (email.contains("..")) {
            return false;
        }

        return true;
    }

    public String toString(){
        return "Name: " + name + "\nUsername: " + username + "\nEmail: " + email;
    }

}
