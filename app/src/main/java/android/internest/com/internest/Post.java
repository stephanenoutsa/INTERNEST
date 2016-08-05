package android.internest.com.internest;

/**
 * Created by stephnoutsa on 8/5/16.
 */
public class Post {

    // Private variables
    int _pid;
    String ptitle, pbody, purl;

    // Empty constructor
    public Post() {

    }

    // Constructor
    public Post(int _pid, String ptitle, String pbody, String purl) {
        this._pid = _pid;
        this.ptitle = ptitle;
        this.pbody = pbody;
        this.purl = purl;
    }

    // Constructor
    public Post(String ptitle, String pbody, String purl) {
        this.ptitle = ptitle;
        this.pbody = pbody;
        this.purl = purl;
    }

    // Getter and Setter methods
    public int get_pid() {
        return _pid;
    }

    public void set_pid(int _pid) {
        this._pid = _pid;
    }

    public String getPtitle() {
        return ptitle;
    }

    public void setPtitle(String ptitle) {
        this.ptitle = ptitle;
    }

    public String getPbody() {
        return pbody;
    }

    public void setPbody(String pbody) {
        this.pbody = pbody;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

}
