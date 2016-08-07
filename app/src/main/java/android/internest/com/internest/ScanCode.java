package android.internest.com.internest;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanCode extends AppCompatActivity {

    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        dbHandler = new MyDBHandler(this, null, null, 1);

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt(getResources().getString(R.string.scanner_prompt_text));
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
        intentIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scan = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scan != null) {
            final String contents = scan.getContents();

            Trend trend = new Trend(contents);
            dbHandler.addTrend(trend);

            if (contents != null) {
                if (URLUtil.isHttpsUrl(contents) || URLUtil.isHttpUrl(contents)) {
                    Scanned scanned = new Scanned(getString(R.string.scanned_type_url), contents);
                    dbHandler.addScanned(scanned);
                    intent = new Intent(getApplicationContext(), URLDisplay.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("url", contents);
                    intent.putExtra("previous", "blog");
                    startActivity(intent);
                } else {
                    Scanned scanned = new Scanned(getString(R.string.scanned_type_text), contents);
                    dbHandler.addScanned(scanned);
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
            Toast.makeText(this, getResources().getString(R.string.empty_scan),
                    Toast.LENGTH_LONG).show();
        }
    }
}
