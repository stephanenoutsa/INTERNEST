package android.internest.com.internest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScanCode extends AppCompatActivity {

    MyDBHandler dbHandler;
    Scanned newScanned;
    Trend newTrend;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Amsterdrum_Grotesk.ttf");
        toolbarTitle.setTypeface(typeface);
        setSupportActionBar(toolbar);

        // Set navigation icon
        toolbar.setNavigationIcon(R.drawable.icon);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        dbHandler = new MyDBHandler(this, null, null, 1);

        // Check if user is connected
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            IntentIntegrator intentIntegrator = new IntentIntegrator(this);
            intentIntegrator.setPrompt(getResources().getString(R.string.scanner_prompt_text));
            intentIntegrator.setOrientationLocked(false);
            intentIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
            intentIntegrator.initiateScan();
        } else {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Blog.class);
            startActivity(i);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scan = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scan != null) {
            final String contents = scan.getContents();

            // Update user's points if user exists
            User oldUser = dbHandler.getUser();
            String num = oldUser.getNum();
            String dob = oldUser.getDob();
            String gender = oldUser.getGender();
            int oldpoints = oldUser.getPoints();
            if (num.equals("null") && dob.equals("null") && gender.equals("null") && oldpoints == 0) {
                Toast.makeText(this, getString(R.string.no_user), Toast.LENGTH_LONG).show();
            } else {
                int newPoints = oldpoints + 2;
                User newUser = new User(num, dob, gender, newPoints);
                dbHandler.deleteUser();
                dbHandler.addUser(newUser);
            }

            if (contents != null) {
                /**
                 * Add trend item to server
                 */
                Trend trend = new Trend(contents);

                // Trailing slash is needed
                final String BASE_URL = "http://192.168.43.170:8080/tracker/webapi/"; // Localhost value is 10.0.2.2

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                InternestService internestService = retrofit.create(InternestService.class);

                Call<Trend> call = internestService.addTrend(trend);
                call.enqueue(new Callback<Trend>() {
                    @Override
                    public void onResponse(Call<Trend> call, Response<Trend> response) {
                        int statusCode = response.code();
                        if (statusCode == 200) {
                            newTrend = response.body();

                            Toast.makeText(context, newTrend.getSdetails(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, getString(R.string.serverconn_error), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Trend> call, Throwable t) {
                        Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                if (URLUtil.isHttpsUrl(contents) || URLUtil.isHttpUrl(contents)) {
                    Scanned scanned = new Scanned(getString(R.string.scanned_type_url), contents);

                    // Add scanned item to app database
                    dbHandler.addScanned(scanned);

                    // Add scanned item to server database
                    Call<Scanned> call1 = internestService.addScanned(scanned);
                    call1.enqueue(new Callback<Scanned>() {
                        @Override
                        public void onResponse(Call<Scanned> call, Response<Scanned> response) {
                            int statusCode = response.code();
                            if (statusCode == 200) {
                                newScanned = response.body();
                                Toast.makeText(context, getString(R.string.scan_added), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, getString(R.string.serverconn_error), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Scanned> call, Throwable t) {
                            Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    intent = new Intent(getApplicationContext(), URLDisplay.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("url", contents);
                    intent.putExtra("previous", "blog");
                    startActivity(intent);
                } else {
                    Scanned scanned = new Scanned(getString(R.string.scanned_type_text), contents);

                    // Add scanned item to app database
                    dbHandler.addScanned(scanned);

                    // Add scanned item to server database
                    Call<Scanned> call1 = internestService.addScanned(scanned);
                    call1.enqueue(new Callback<Scanned>() {
                        @Override
                        public void onResponse(Call<Scanned> call, Response<Scanned> response) {
                            int statusCode = response.code();
                            if (statusCode == 200) {
                                newScanned = response.body();
                                Toast.makeText(context, "Item added to remote database", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Couldn't update server", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Scanned> call, Throwable t) {
                            Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    intent = new Intent(this, TextDisplay.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("text", contents);
                    intent.putExtra("previous", "blog");
                    startActivity(intent);
                }
            }
            else {
                finish();
            }
        }
        else {
            Toast.makeText(this, getString(R.string.empty_scan),
                    Toast.LENGTH_LONG).show();
        }
    }
}
