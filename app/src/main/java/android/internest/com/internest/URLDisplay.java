package android.internest.com.internest;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class URLDisplay extends AppCompatActivity {

    private ShareActionProvider mShareActionProvider;

    String url, previous = "";
    WebView webView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urldisplay);

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

        url = getIntent().getExtras().getString("url");
        previous = getIntent().getExtras().getString("previous");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Enable javascript for webview and display webpage
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 5.1.1; Nexus 5 " +
                "Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65" +
                " Mobile Safari/537.36");
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getScheme().equals("market")) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        Activity host = (Activity) view.getContext();
                        host.startActivity(intent);
                        return true;
                    } catch (ActivityNotFoundException e) {
                        // Google Play app is not installed, you may want to open the app store link
                        Uri uri = Uri.parse(url);
                        view.loadUrl("https://play.google.com/store/apps/" + uri.getHost() + "?" + uri.getQuery());
                        return false;
                    }

                }

                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    // Navigate within WebView
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (previous.equals("blog")) {
                Intent intent = new Intent(this, Blog.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
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
            i.putExtra("back", "urlDisplay");
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
