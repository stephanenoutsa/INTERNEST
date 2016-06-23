package android.internest.com.internest;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;

import java.io.File;

/**
 * Created by stephnoutsa on 6/23/16.
 */
public class SingleMediaScanner implements MediaScannerConnectionClient {

    private MediaScannerConnection mSC;
    private File file;

    public SingleMediaScanner(Context context, File f) {
        file = f;
        mSC = new MediaScannerConnection(context, this);
        mSC.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        mSC.scanFile(file.getAbsolutePath(), null);
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        mSC.disconnect();
    }
}
