package android.internest.com.internest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by stephnoutsa on 6/1/16.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    List<Post> postList;
    ListAdapter listAdapter;
    ListView listView;


    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_blog, container, false);

        MyDBHandler dbHandler = new MyDBHandler(getContext(), null, null, 1);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                /**
                 * Get all post items from server
                  */
                // Trailing slash is needed
                final String BASE_URL = "http://10.0.2.2:8080/internest/webapi/"; // Localhost value is 10.0.2.2

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                InternestService internestService = retrofit.create(InternestService.class);

                Call<List<Post>> call = internestService.getAllPosts();
                call.enqueue(new retrofit2.Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        int statusCode = response.code();
                        if (statusCode == 200) {
                            postList = response.body();

                            listAdapter = new BlogAdapter(getActivity(), postList);

                            listView = (ListView) rootView.findViewById(R.id.blogList);
                            listView.setAdapter(listAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                    Post post = (Post) parent.getItemAtPosition(position);
                                    String url = post.getUrl();
                                    Intent intent = new Intent(getContext(), URLDisplay.class);
                                    intent.putExtra("url", url);
                                    intent.putExtra("previous", "blog");
                                    startActivity(intent);
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        Runnable r = new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        Thread thread = new Thread(r);
        thread.start();

        return rootView;
    }
}
