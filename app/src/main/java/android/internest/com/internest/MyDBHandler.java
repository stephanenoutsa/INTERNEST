package android.internest.com.internest;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scanned.db";
    public static final String TABLE_SCANNED = "scanned";
    public static final String SCANNED_COLUMN_ID = "_sid";
    public static final String SCANNED_COLUMN_TYPE = "stype";
    public static final String SCANNED_COLUMN_DETAILS = "sdetails";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String scanned = "CREATE TABLE " + TABLE_SCANNED + "(" +
                SCANNED_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                SCANNED_COLUMN_TYPE + " TEXT " + ", " +
                SCANNED_COLUMN_DETAILS + " TEXT " +
                ")";
        db.execSQL(scanned);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCANNED + ";");
        onCreate(db);
    }

    // Add a new scanned item to the Scanned table
    public void addScanned(Scanned scanned) {
        ContentValues values = new ContentValues();
        values.put(SCANNED_COLUMN_TYPE, String.valueOf(scanned.getStype()));
        values.put(SCANNED_COLUMN_DETAILS, String.valueOf(scanned.getSdetails()));
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_SCANNED, null, values);
        db.close();
    }

    // Get all scanned items from Scanned table
    public List<Scanned> getAllScanned() {
        List<Scanned> scannedList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_SCANNED + ";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToFirst();

        // Loop through all rows and add each to list
        while(!c.isAfterLast()) {
            Scanned scanned = new Scanned();
            scanned.set_sid(Integer.parseInt(c.getString(0)));
            scanned.setStype(c.getString(1));
            scanned.setSdetails(c.getString(2));

            scannedList.add(scanned);

            c.moveToNext();
        }

        return scannedList;
    }

    // Get scanned items count
    public int getScannedCount() {
        String query = "SELECT * FROM " + TABLE_SCANNED + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        try {
            return c.getCount();
        } finally {
            c.close();
            db.close();
        }
    }

}
