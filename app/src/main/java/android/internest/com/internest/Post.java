package android.internest.com.internest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stephnoutsa on 8/5/16.
 */
public class Post {

    // Private variables
    @SerializedName("id")
    int id;

    @SerializedName("author")
    String author;

    @SerializedName("created")
    String created;

    @SerializedName("url")
    String url;

    @SerializedName("thumbnail")
    String thumbnail;

    @SerializedName("title")
    String title;

    @SerializedName("body")
    String body;

    // Empty constructor
    public Post() {

    }

    // Constructor
    public Post(int id, String author, String created, String url, String thumbnail, String title, String body) {
        this.id = id;
        this.author = author;
        this.created = created;
        this.url = url;
        this.thumbnail = thumbnail;
        this.title = title;
        this.body = body;
    }

    // Constructor
    public Post(String author, String created, String url, String thumbnail, String title, String body) {
        this.author = author;
        this.created = created;
        this.url = url;
        this.thumbnail = thumbnail;
        this.title = title;
        this.body = body;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

}
