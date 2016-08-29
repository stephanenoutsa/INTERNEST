package android.internest.com.internest;



public class User {

    // Private variables
    int _uid;
    String num, dob, gender;

    // Empty Constructor
    public User() {

    }

    // Constructor
    public User(int _uid, String num, String dob, String gender) {
        this._uid = _uid;
        this.num = num;
        this.dob = dob;
    }

    // Constructor
    public User(String num, String dob, String gender) {
        this.num = num;
        this.dob = dob;
    }

    // Getter and Setter methods
    public int get_uid() {
        return _uid;
    }

    public void set_uid(int _uid) {
        this._uid = _uid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
