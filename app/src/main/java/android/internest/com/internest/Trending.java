package android.internest.com.internest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;

import java.util.List;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_trending, container, false);

        final MyDBHandler dbHandler = new MyDBHandler(getContext(), null, null, 1);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // Get all trending scanned items from database
                List<Scanned> trends = dbHandler.getTrends();

                // Get the scanned items into an adapter's list
                ListAdapter listAdapter = new CustomAdapter(getContext(), trends);

                // Set the adapter to display the scanned items
                ListView listView = (ListView) rootView.findViewById(R.id.trends);
                listView.setAdapter(listAdapter);

                listView.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                TextView scanType = (TextView) view.findViewById(R.id.scanType);
                                TextView scanDetails = (TextView) view.findViewById(R.id.scanDetails);

                                String sType = scanType.getText().toString();
                                final String sDetails = scanDetails.getText().toString();

                                if (sType.equals(getString(R.string.scanned_type_text))) {
                                    Intent intent = new Intent(getContext(), TextDisplay.class);
                                    intent.putExtra("text", sDetails);
                                    intent.putExtra("previous", "trending");
                                    startActivity(intent);
                                } else if (sType.equals(getString(R.string.url_simple))) {
                                    Intent intent = new Intent(getContext(), URLDisplay.class);
                                    intent.putExtra("url", sDetails);
                                    intent.putExtra("previous", "trending");
                                    startActivity(intent);
                                }
                            }
                        }
                );
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
