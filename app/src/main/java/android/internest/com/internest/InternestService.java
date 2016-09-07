package android.internest.com.internest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by stephnoutsa on 8/31/16.
 */
public interface InternestService {
    // Start of methods for Posts
    @GET("posts")
    Call<List<Post>> getAllPosts();

    @POST("posts")
    Call<Post> addPost(@Body Post post);

    @GET("posts/{postId}")
    Call<Post> getPost(@Path("postId") int postId);
    // End of methods for Posts


    // Start of methods for Scanned
    @GET("scanned")
    Call<List<Scanned>> getAllScanned();

    @POST("scanned")
    Call<Scanned> addScanned(@Body Scanned scanned);

    @GET("scanned/{scannedId}")
    Call<Scanned> getScanned(@Path("scannedId") int scannedId);
    // End of methods for Scanned


    // Start of methods for Trends
    @GET("trends")
    Call<List<Trend>> getAllTrends();

    @POST("trends")
    Call<Trend> addTrend(@Body Trend trend);

    @GET("trends/{trendId}")
    Call<Trend> getTrend(@Path("trendId") int trendId);
    // End of methods for Trends
}
