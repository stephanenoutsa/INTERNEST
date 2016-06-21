package android.internest.com.internest;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by stephnoutsa on 6/1/16.
 */
public class MakeCode extends Fragment {

    public MakeCode() {
        // Required empty constructor
    }

    public static MakeCode newInstance() {
        MakeCode fragment = new MakeCode();
        return fragment;
    }

    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_make_code, container, false);
        textView = (TextView) rootView.findViewById(R.id.textView);

        return rootView;
    }
}
