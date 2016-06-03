package android.internest.com.internest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by stephnoutsa on 6/1/16.
 */
public class Trending extends Fragment {

    public Trending() {
        // Required empty constructor
    }

    public static Trending newInstance() {
        Trending fragment = new Trending();
        return fragment;
    }

    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_trending, container, false);
        textView = (TextView) rootView.findViewById(R.id.textView3);

        return rootView;
    }
}
