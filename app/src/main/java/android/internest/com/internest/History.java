package android.internest.com.internest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class History extends AppCompatActivity {

    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    List<Scanned> scannedList;
    ListView listView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Digory_Doodles_PS.ttf");
        toolbarTitle.setTypeface(typeface);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // Get all scanned items from database
                scannedList = dbHandler.getAllScanned();

                // Reverse the order of the scanned items
                Collections.reverse(scannedList);

                // Get the scanned items into an adapter's list
                ListAdapter listAdapter = new CustomAdapter(context, scannedList);

                // Set the adapter to display the scanned items
                listView = (ListView) findViewById(R.id.scannedList);
                listView.setAdapter(listAdapter);
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

    }

    ////////////Intents for menu items////////////
    public void onClickScan() {
        Intent i = new Intent(this, ScanCode.class);
        startActivity(i);
    }

    public void onClickHistory() {
        Intent i = new Intent(this, History.class);
        startActivity(i);
    }
    //////////////End of intents//////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.go_to_scan) {
            onClickScan();
            return true;
        }

        if (id == R.id.go_to_history) {
            onClickHistory();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
