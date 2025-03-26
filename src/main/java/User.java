public abstract class User {
    private String name;
    private String username;
    private String password;
    private String email;

    public User(String name, String username, String password, String email){
        throw new RuntimeException("Not Yet Implemented");
    }

    public String getName(){
        throw new RuntimeException("Not yet implemented");
    }

    public String getUsername(){
        throw new RuntimeException("Not yet implemented");
    }

    public String getEmail(){
        throw new RuntimeException("Not yet implemented");
    }

    /**Ensures correct account credentials to login
     * input - username and password
     * output - boolean
     */
    public boolean checkCredentials(String username, String password){
        throw new RuntimeException("Not Yet Implemented");
    }

    public void updateName(String name){
        throw new RuntimeException("Not yet implemented");
    }

    public void updateUsername(String username){
        throw new RuntimeException("Not yet implemented");
    }

    /**Checks old password before allowing password update
     * inputs - prev password and new password
     * updates password
    */
    public void updatePassword(String prev, String psswrd){
        throw new RuntimeException("Not yet implemented");
    }

    public void updateEmail(String email){
        throw new RuntimeException("Not yet implemented");
    }

    /**checks if email is valid before it can be set for account
     * input - email
     * output - boolean
     */
    public boolean validEmail(String email){
        throw new RuntimeException("not yet implemented");
    }

}
