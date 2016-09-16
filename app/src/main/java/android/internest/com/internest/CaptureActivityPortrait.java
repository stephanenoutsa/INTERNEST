package android.internest.com.internest;



import android.content.Intent;

import com.journeyapps.barcodescanner.CaptureActivity;

public class CaptureActivityPortrait extends CaptureActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, Blog.class);
        startActivity(i);
    }
}
