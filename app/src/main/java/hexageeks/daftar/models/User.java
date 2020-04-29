package hexageeks.daftar.models;

public class User {
    public String id;
    public String fName;
    public String lName;
    public String dob;
    public String role;
    public String token;

    public static User instance = null;

    private User(String id, String fName, String lName, String dob, String role, String token) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
        this.role = role;
        this.token = token;
    }

    public static User getInstance() {
        return instance;
    }

    public static User setInstance(String id, String fName, String lName, String dob, String role, String token) {
        instance = new User(id, fName,lName, dob, role, token);
        return instance;
    }

    @Override
    public String toString() {
        return String.format("User: [ %s, %s, %s, %s, %s]", id, fName, lName, dob, role);
    }
}
