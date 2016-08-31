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
    @GET("posts")
    Call<List<Post>> getAllPosts();

    @POST("posts")
    Call<Post> addPost(@Body Post post);

    @GET("posts/{postId}")
    Call<Post> getPost(@Path("postId") int postId);
}
