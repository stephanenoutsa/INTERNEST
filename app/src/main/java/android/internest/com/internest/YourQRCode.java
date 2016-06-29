package android.internest.com.internest;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YourQRCode extends AppCompatActivity {

    String value;
    ImageView qrCode;
    Bitmap bitmap;
    public final static int WIDTH = 500;
    public final static int QUALITY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_qrcode);

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

        value = getIntent().getExtras().getString("value");
        qrCode = (ImageView) findViewById(R.id.qrCode);

        Thread stephThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    //runOnUiThread method used to do UI task in main thread.
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                bitmap = encodeAsBitmap(value);
                                qrCode.setImageBitmap(bitmap);
                            } catch (WriterException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

        stephThread.start();

    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? getResources().getColor(R.color.colorBlack) :
                        getResources().getColor(R.color.colorWhite);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, w, h);
        return bitmap;
    }

    public void onClickSaveCode(View view) {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String stringDate = sdf.format(date);
        String title = getResources().getString(R.string.saved_image_title_prepend) + stringDate;
        //String description = getResources().getString(R.string.saved_image_description);
        String format = getResources().getString(R.string.saved_image_format);
        String directory = getResources().getString(R.string.saved_image_directory);
        Bitmap image = ((BitmapDrawable)qrCode.getDrawable()).getBitmap();

        // Method call to save image
        saveImageToInternalStorage(image, directory, title, format);
    }

    public boolean saveImageToInternalStorage(Bitmap bitmap, String directory, String title, String format) {
        // Create imageDirectory
        File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File appPicsDir = new File (picsDir, directory);
        appPicsDir.mkdirs();
        File path = new File (appPicsDir, title + format);

        try {
           OutputStream os = new FileOutputStream(path);

            // Use the compress method on the Bitmap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY, os);

            os.close();

            new SingleMediaScanner(this, path);

            Toast.makeText(this, getString(R.string.save_success), Toast.LENGTH_LONG).show();

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.filenotfound_error), Toast.LENGTH_LONG).show();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.io_error), Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
