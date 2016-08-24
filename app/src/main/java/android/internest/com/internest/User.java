package android.internest.com.internest;



public class User {

    // Private variables
    int _uid;
    String email, dob;

    // Empty Constructor
    public User() {

    }

    // Constructor
    public User(int _uid, String email, String dob) {
        this._uid = _uid;
        this.email = email;
        this.dob = dob;
    }

    // Constructor
    public User(String email, String dob) {
        this.email = email;
        this.dob = dob;
    }

    // Getter and Setter methods
    public int get_uid() {
        return _uid;
    }

    public void set_uid(int _uid) {
        this._uid = _uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
