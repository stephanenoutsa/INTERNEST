package android.internest.com.internest;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanCode extends AppCompatActivity {

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

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt(getResources().getString(R.string.scanner_prompt_text));
        intentIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scan = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scan != null) {
            /*String codeContents = getResources().getString(R.string.code_contents);
            codeContents += scan.getContents();
            codeContents += getResources().getString(R.string.code_format);
            codeContents += scan.getFormatName();
            Toast.makeText(this, codeContents, Toast.LENGTH_LONG).show();*/

            String contents = scan.getContents();
            if (contents.contains(getString(R.string.url_placeholder_text))) {
                Toast.makeText(this, "It is a url", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Either it is just text, or it is junk", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.empty_scan),
                    Toast.LENGTH_LONG).show();
        }
    }

}
