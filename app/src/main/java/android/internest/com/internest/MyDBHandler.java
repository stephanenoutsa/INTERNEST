package android.internest.com.internest;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "scanned.db";
    public static final String TABLE_SCANNED = "scanned";
    public static final String SCANNED_COLUMN_ID = "_sid";
    public static final String SCANNED_COLUMN_TYPE = "stype";
    public static final String SCANNED_COLUMN_DETAILS = "sdetails";
    public static final String TABLE_POST = "posts";
    public static final String POST_COLUMN_ID = "_pid";
    public static final String POST_COLUMN_TITLE = "ptitle";
    public static final String POST_COLUMN_BODY = "pbody";
    public static final String POST_COLUMN_URL = "purl";
    public static final String TABLE_TRENDS = "trends";
    public static final String TRENDS_COLUMN_ID = "_tid";
    public static final String TRENDS_COLUMN_COUNT = "tcount";

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

        String posts = "CREATE TABLE " + TABLE_POST + "(" +
                POST_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                POST_COLUMN_TITLE + " TEXT " + ", " +
                POST_COLUMN_BODY + " TEXT " + ", " +
                POST_COLUMN_URL + " TEXT " +
                ")";
        db.execSQL(posts);

        String trends = "CREATE TABLE " + TABLE_TRENDS + "(" +
                TRENDS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                SCANNED_COLUMN_DETAILS + " TEXT " + ", " +
                TRENDS_COLUMN_COUNT + " INTEGER " +
                ")";
        db.execSQL(trends);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCANNED + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST + ";");
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
    public int getScannedCount(Context context, String sdetails) {
        String query = "SELECT * FROM " + TABLE_SCANNED + " WHERE " + SCANNED_COLUMN_DETAILS + " = "
                + sdetails + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        try {
            Toast.makeText(context, "Count is: " + c.getCount(), Toast.LENGTH_SHORT).show();
            return c.getCount();
        } finally {
            c.close();
            db.close();
        }
    }

    // Add a new post to the Post table
    public void addPost(Post post) {
        ContentValues values = new ContentValues();
        values.put(POST_COLUMN_TITLE, String.valueOf(post.getPtitle()));
        values.put(POST_COLUMN_BODY, String.valueOf(post.getPbody()));
        values.put(POST_COLUMN_URL, String.valueOf(post.getPurl()));
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_POST, null, values);
        db.close();
    }

    // Get all posts from Post table
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_POST + ";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToFirst();

        // Loop through all rows and add each to list
        while(!c.isAfterLast()) {
            Post post = new Post();
            post.set_pid(Integer.parseInt(c.getString(0)));
            post.setPtitle(c.getString(1));
            post.setPbody(c.getString(2));
            post.setPurl(c.getString(3));

            posts.add(post);

            c.moveToNext();
        }

        return posts;
    }

    // Get post items count
    public int getPostsCount() {
        String query = "SELECT * FROM " + TABLE_POST + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        try {
            return c.getCount();
        } finally {
            c.close();
            db.close();
        }
    }

    // Add a new Trend item
    public void addTrend(Context context, Trend trend) {
        ContentValues values = new ContentValues();
        values.put(SCANNED_COLUMN_DETAILS, String.valueOf(trend.getSdetails()));

        // Check if scanned item already exists
        int count = getScannedCount(context, trend.getSdetails());
        if (count == 0) {
            values.put(TRENDS_COLUMN_COUNT, 1);
        }
        else {
            values.put(TRENDS_COLUMN_COUNT, count);
        }

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_TRENDS, null, values);
        db.close();
    }

    // Get all trends from Trend table
    public List<Scanned> getTrends() {
        List<Trend> trends = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        List<Integer> top = new ArrayList<>();
        List<Scanned> scannedList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_TRENDS + ";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToFirst();

        // Loop through all rows and add each to list
        while(!c.isAfterLast()) {
            Trend trend = new Trend();
            trend.set_tid(Integer.parseInt(c.getString(0)));
            trend.setSdetails(c.getString(1));
            trend.setTcount(Integer.parseInt(c.getString(2)));

            trends.add(trend);

            counts.add(Integer.parseInt(c.getString(2)));

            c.moveToNext();
        }

        Collections.sort(counts);

        int i = counts.size();
        if (i >= 10) {
            for(int a = 0; a < 10; a++) {
                top.add(Collections.max(counts));
                counts.remove(counts.size() - 1);
                Collections.sort(counts);
            }
        }
        else {
            for(int a = 0; a < i; a++) {
                top.add(Collections.max(counts));
                counts.remove(counts.size() - 1);
                Collections.sort(counts);
            }
        }

        Collections.sort(top);

        int size = top.size();

        while (size > 0) {
            int max = Collections.max(top);
            String query1 = "SELECT * FROM " + TABLE_TRENDS + " WHERE " + TRENDS_COLUMN_COUNT + " = "
            + max + ";";
            top.remove(top.size() - 1);
            Collections.sort(top);
            Cursor c1 = db.rawQuery(query1, null);
            if (c1 != null)
                c1.moveToFirst();
            while (!c1.isAfterLast()) {
                String query2 = "SELECT * FROM " + TABLE_SCANNED + " WHERE " + SCANNED_COLUMN_DETAILS + " = "
                        + c1.getString(1) + " LIMIT 1;";
                Cursor c2 = db.rawQuery(query2, null);
                if (c2 != null)
                    c2.moveToFirst();
                Scanned scanned = new Scanned();
                scanned.set_sid(Integer.parseInt(c2.getString(0)));
                scanned.setStype(c2.getString(1));
                scanned.setSdetails(c2.getString(2));

                scannedList.add(scanned);

                c2.close();

                c1.moveToNext();
            }

            c1.close();
        }

        c.close();
        db.close();

        return scannedList;
    }

}
