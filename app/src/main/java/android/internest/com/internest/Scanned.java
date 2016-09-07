package android.internest.com.internest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stephnoutsa on 7/7/16.
 */
public class Scanned {

    // Private variables
    @SerializedName("id")
    int _sid;

    @SerializedName("type")
    String stype;

    @SerializedName("details")
    String sdetails;

    // Empty constructor
    public Scanned() {

    }

    // Constructor
    public Scanned(int _sid, String stype, String sdetails) {
        this._sid = _sid;
        this.stype = stype;
        this.sdetails = sdetails;
    }

    // Constructor
    public Scanned(String stype, String sdetails) {
        this.stype = stype;
        this.sdetails = sdetails;
    }

    // Getter and Setter methods
    public int get_sid() {
        return _sid;
    }

    public void set_sid(int _sid) {
        this._sid = _sid;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getSdetails() {
        return sdetails;
    }

    public void setSdetails(String sdetails) {
        this.sdetails = sdetails;
    }
}
