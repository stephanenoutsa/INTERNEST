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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephnoutsa on 6/1/16.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

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

                List<Post> blogList;

                // Get all post items from database
                blogList = new ArrayList<>();
                Post post1 = new Post(1, "title1", "post1", "url1");
                blogList.add(post1);
                Post post2 = new Post(2, "title2", "post2", "url2");
                blogList.add(post2);
                Post post3 = new Post(3, "title3", "post3", "url3");
                blogList.add(post3);
                Post post4 = new Post(4, "title4", "post4", "url4");
                blogList.add(post4);

                // Reverse the order of the post items
                //Collections.reverse(blogList);

                ListAdapter listAdapter = new BlogAdapter(getActivity(), blogList);

                ListView listView = (ListView) rootView.findViewById(R.id.blogList);
                listView.setAdapter(listAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        Post post = (Post) parent.getItemAtPosition(position);
                        String url = post.getPurl();
                        Intent intent = new Intent(getContext(), URLDisplay.class);
                        intent.putExtra("url", url);
                        intent.putExtra("previous", "blog");
                        startActivity(intent);
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
