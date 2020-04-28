package hexageeks.daftar.models;

public class User {
    public String id;
    public String fName;
    public String lName;
    public String dob;
    public String role;

    public static User instance = null;

    private User(String id, String fName, String lName, String dob, String role) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
        this.role = role;
    }

    public static User getInstance() {
        return instance;
    }

    public static User setInstance(String id, String fName, String lName, String dob, String role) {
        instance = new User(id, fName,lName, dob, role);
        return instance;
    }

    @Override
    public String toString() {
        return String.format("User: [ %s, %s, %s, %s, %s]", id, fName, lName, dob, role);
    }
}
