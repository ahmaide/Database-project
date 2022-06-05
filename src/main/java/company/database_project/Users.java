package company.database_project;

import java.util.Locale;
import java.util.Map;

public class Users {
    private String username;
    private String password;
    public static Map<String, Users> list;
    public static Users currentUser;

    public Users(String username, String password){
        this.username = username;

        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
