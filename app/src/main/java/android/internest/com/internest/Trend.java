package android.internest.com.internest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stephnoutsa on 8/6/16.
 */
public class Trend {

    // Private variables
    @SerializedName("id")
    int _tid;

    @SerializedName("count")
    int tcount;

    @SerializedName("details")
    String sdetails;

    // Empty constructor
    public Trend() {

    }

    // Constructor
    public Trend(int _tid, String sdetails) {
        this._tid = _tid;
        this.sdetails = sdetails;
    }

    // Constructor
    public Trend(String sdetails) {
        this.sdetails = sdetails;
    }

    // Getter and Setter methods
    public int get_tid() {
        return _tid;
    }

    public void set_tid(int _tid) {
        this._tid = _tid;
    }

    public String getSdetails() {
        return sdetails;
    }

    public void setSdetails(String sdetails) {
        this.sdetails = sdetails;
    }

    public int getTcount() {
        return tcount;
    }

    public void setTcount(int tcount) {
        this.tcount = tcount;
    }
}
