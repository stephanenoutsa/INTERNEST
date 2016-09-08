package android.internest.com.internest;


import com.google.gson.annotations.SerializedName;

public class User {

    // Private variables
    @SerializedName("id")
    int _uid;

    @SerializedName("number")
    String num;

    @SerializedName("dob")
    String dob;

    @SerializedName("gender")
    String gender;

    @SerializedName("points")
    int points;

    // Empty Constructor
    public User() {

    }

    // Constructor
    public User(int _uid, String num, String dob, String gender, int points) {
        this._uid = _uid;
        this.num = num;
        this.dob = dob;
        this.gender = gender;
        this.points = points;
    }

    // Constructor
    public User(String num, String dob, String gender, int points) {
        this.num = num;
        this.dob = dob;
        this.gender = gender;
        this.points = points;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
