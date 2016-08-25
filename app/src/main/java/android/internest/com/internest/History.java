package android.internest.com.internest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class History extends AppCompatActivity {

    private ShareActionProvider mShareActionProvider;

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
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Amsterdrum_Grotesk.ttf");
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

                listView.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                TextView scanType = (TextView) view.findViewById(R.id.scanType);
                                TextView scanDetails = (TextView) view.findViewById(R.id.scanDetails);

                                String sType = scanType.getText().toString();
                                final String sDetails = scanDetails.getText().toString();

                                if (sType.equals(getString(R.string.scanned_type_text))) {
                                    Intent intent = new Intent(context, TextDisplay.class);
                                    intent.putExtra("text", sDetails);
                                    intent.putExtra("previous", "history");
                                    startActivity(intent);
                                } else if (sType.equals(getString(R.string.url_simple))) {
                                    Intent intent = new Intent(getApplicationContext(), URLDisplay.class);
                                    intent.putExtra("url", sDetails);
                                    intent.putExtra("previous", "history");
                                    startActivity(intent);
                                }
                            }
                        }
                );

                listView.setOnItemLongClickListener(
                        new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                Scanned scanned = (Scanned) parent.getItemAtPosition(position);
                                final String _sid = "\'" + scanned.get_sid() + "\'";
                                new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_menu_delete).
                                        setTitle(R.string.delete_title).
                                        setMessage(R.string.delete_warning).
                                        setPositiveButton(R.string.delete_ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dbHandler.deleteScanned(_sid);
                                                Intent intent = new Intent(History.this, History.class);
                                                finish();
                                                overridePendingTransition(0, 0);
                                                startActivity(intent);
                                                overridePendingTransition(0, 0);
                                            }
                                        }).setNegativeButton(R.string.delete_cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();

                                return true;
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

    public void onClickShare() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_link));
        i.setType("text/plain");
        startActivity(Intent.createChooser(i, getResources().getText(R.string.share_text)));
    }

    public void onClickPoints() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        User user = dbHandler.getUser();
        String num = user.getNum();
        String dob = user.getDob();
        Intent i;
        if (num.equals("null") || dob.equals("null")) {
            i = new Intent(this, SignUp.class);
        }
        else {
            i = new Intent(this, PointsPromo.class);
            i.putExtra("back", "history");
        }

        startActivity(i);
    }

    public void onClickContact() {
        Intent i = new Intent(this, ContactUs.class);
        startActivity(i);
    }
    //////////////End of intents//////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blog, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

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

        if (id == R.id.menu_item_share) {
            onClickShare();
            return true;
        }

        if (id == R.id.go_to_points) {
            onClickPoints();
            return true;
        }

        if (id == R.id.go_to_contact) {
            onClickContact();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
