package android.internest.com.internest;

/**
 * Created by stephnoutsa on 9/9/16.
 */
public class Made {

    // Private variables
    int _mid;
    int mNumber;

    // Empty constructor
    public Made() {

    }

    // Constructor
    public Made(int _mid, int mNumber) {
        this._mid = _mid;
        this.mNumber = mNumber;
    }

    // Constructor
    public Made(int mNumber) {
        this.mNumber = mNumber;
    }

    // Getter and Setter methods
    public int get_mid() {
        return _mid;
    }

    public void set_mid(int _mid) {
        this._mid = _mid;
    }

    public int getmNumber() {
        return mNumber;
    }

    public void setmNumber(int mNumber) {
        this.mNumber = mNumber;
    }
}
