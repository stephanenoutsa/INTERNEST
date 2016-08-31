package android.internest.com.internest;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {

    SQLiteDatabase db = null;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scanned.db";
    public static final String TABLE_SCANNED = "scanned";
    public static final String SCANNED_COLUMN_ID = "_sid";
    public static final String SCANNED_COLUMN_TYPE = "stype";
    public static final String SCANNED_COLUMN_DETAILS = "sdetails";
    public static final String TABLE_POST = "posts";
    public static final String POST_COLUMN_ID = "id";
    public static final String POST_COLUMN_AUTHOR = "author";
    public static final String POST_COLUMN_DATE = "created";
    public static final String POST_COLUMN_URL = "url";
    public static final String POST_COLUMN_THUMBNAIL = "thumbnail";
    public static final String POST_COLUMN_TITLE = "title";
    public static final String POST_COLUMN_BODY = "body";
    public static final String TABLE_TRENDS = "trends";
    public static final String TRENDS_COLUMN_ID = "_tid";
    public static final String TRENDS_COLUMN_COUNT = "tcount";
    public static final String TABLE_USER = "users";
    public static final String USER_COLUMN_ID = "_uid";
    public static final String USER_COLUMN_NUM = "number";
    public static final String USER_COLUMN_DOB = "dob";
    public static final String USER_COLUMN_GEN = "gender";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        String scanned = "CREATE TABLE " + TABLE_SCANNED + "(" +
                SCANNED_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                SCANNED_COLUMN_TYPE + " TEXT " + ", " +
                SCANNED_COLUMN_DETAILS + " TEXT " +
                ")";
        db.execSQL(scanned);

        String posts = "CREATE TABLE " + TABLE_POST + "(" +
                POST_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                POST_COLUMN_AUTHOR + " TEXT " + ", " +
                POST_COLUMN_DATE + " TEXT " + ", " +
                POST_COLUMN_URL + " TEXT " + ", " +
                POST_COLUMN_THUMBNAIL + " TEXT " + ", " +
                POST_COLUMN_TITLE + " TEXT " + ", " +
                POST_COLUMN_BODY + " TEXT " +
                ")";
        db.execSQL(posts);

        String trends = "CREATE TABLE " + TABLE_TRENDS + "(" +
                TRENDS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                SCANNED_COLUMN_DETAILS + " TEXT " + ", " +
                TRENDS_COLUMN_COUNT + " INTEGER " +
                ")";
        db.execSQL(trends);

        String user = "CREATE TABLE " + TABLE_USER + "(" +
                USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                USER_COLUMN_NUM + " TEXT " + ", " +
                USER_COLUMN_DOB + " TEXT " + ", " +
                USER_COLUMN_GEN + " TEXT " +
                ")";
        db.execSQL(user);

        // Add placeholder values for User table
        User user1 = new User("null", "null", "null");
        addUser(user1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCANNED + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRENDS + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER + ";");
        onCreate(db);
    }

    // Add a new scanned item to the Scanned table
    public void addScanned(Scanned scanned) {
        ContentValues values = new ContentValues();
        values.put(SCANNED_COLUMN_TYPE, String.valueOf(scanned.getStype()));
        values.put(SCANNED_COLUMN_DETAILS, String.valueOf(scanned.getSdetails()));
        if (db == null) {
            db = getWritableDatabase();
        }
        db.insert(TABLE_SCANNED, null, values);
    }

    // Get all scanned items from Scanned table
    public List<Scanned> getAllScanned() {
        List<Scanned> scannedList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_SCANNED + ";";

        if (db == null) {
            db = getReadableDatabase();
        }

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
    public int getScannedCount(String sdetails) {
        String query = "SELECT * FROM " + TABLE_SCANNED + " WHERE " + SCANNED_COLUMN_DETAILS + " = \'"
                + sdetails + "\';";
        if (db == null) {
            db = getReadableDatabase();
        }
        Cursor c = db.rawQuery(query, null);

        try {
            return c.getCount();
        } finally {
            c.close();
        }
    }

    // Delete a scanned item
    public void deleteScanned(String id) {
        String query = "DELETE FROM " + TABLE_SCANNED + " WHERE " + SCANNED_COLUMN_ID + " = " + id + ";";
        if (db == null) {
            db = getWritableDatabase();
        }
        try {
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add a new post to the Post table
    public void addPost(Post post) {
        ContentValues values = new ContentValues();
        values.put(POST_COLUMN_AUTHOR, String.valueOf(post.getAuthor()));
        values.put(POST_COLUMN_DATE, String.valueOf(post.getCreated()));
        values.put(POST_COLUMN_URL, String.valueOf(post.getUrl()));
        values.put(POST_COLUMN_THUMBNAIL, String.valueOf(post.getThumbnail()));
        values.put(POST_COLUMN_TITLE, String.valueOf(post.getTitle()));
        values.put(POST_COLUMN_BODY, String.valueOf(post.getBody()));
        if (db == null) {
            db = getWritableDatabase();
        }
        db.insert(TABLE_POST, null, values);
    }

    // Get all posts from Post table
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_POST + ";";

        if (db == null) {
            db = getReadableDatabase();
        }

        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToFirst();

        // Loop through all rows and add each to list
        while(!c.isAfterLast()) {
            Post post = new Post();
            post.setId(Integer.parseInt(c.getString(0)));
            post.setAuthor(c.getString(1));
            post.setCreated(c.getString(2));
            post.setUrl(c.getString(3));
            post.setThumbnail(c.getString(4));
            post.setTitle(c.getString(5));
            post.setBody(c.getString(6));

            posts.add(post);

            c.moveToNext();
        }

        return posts;
    }

    // Get post items count
    public int getPostsCount() {
        String query = "SELECT * FROM " + TABLE_POST + ";";
        if (db == null) {
            db = getReadableDatabase();
        }
        Cursor c = db.rawQuery(query, null);

        try {
            return c.getCount();
        } finally {
            c.close();
        }
    }

    // Add a new Trend item
    public void addTrend(Trend trend) {
        ContentValues values = new ContentValues();

        // Check if scanned item already exists
        int count = getScannedCount(trend.getSdetails());
        if (count == 0) {
            values.put(TRENDS_COLUMN_COUNT, 1);
        }
        else {
            // Update Trend item
            deleteTrend(trend.getSdetails());
            values.put(TRENDS_COLUMN_COUNT, count);
        }
        values.put(SCANNED_COLUMN_DETAILS, String.valueOf(trend.getSdetails()));

        if (db == null) {
            db = getWritableDatabase();
        }

        db.insert(TABLE_TRENDS, null, values);
    }

    // Get all trends from Trend table
    public List<Scanned> getTrends() {
        List<Integer> counts = new ArrayList<>();
        List<Integer> top = new ArrayList<>();
        List<Scanned> scannedList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_TRENDS + ";";

        if (db == null) {
            db = getReadableDatabase();
        }

        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToFirst();

        // Loop through all rows and add each to list
        while(!c.isAfterLast()) {
            counts.add(Integer.parseInt(c.getString(2)));

            c.moveToNext();
        }

        Collections.sort(counts);

        int i = counts.size();
        if (i >= 10) {
            for(int a = 0; a < 10; a++) {
                Integer cObject = Collections.max(counts);
                top.add(cObject);
                counts.remove(counts.size() - 1);
                Collections.sort(counts);
            }
        }
        else {
            while (counts.size() > 0) {
                Integer cObject = Collections.max(counts);
                top.add(cObject);
                counts.remove(counts.size() - 1);
                Collections.sort(counts);
            }
        }

        Collections.sort(top);

        while (top.size() > 0) {
            int max;
            if (top.size() == 1) {
                max = top.get(0);
            }
            else {
                max = Collections.max(top);
            }
            String query1 = "SELECT * FROM " + TABLE_TRENDS + " WHERE " + TRENDS_COLUMN_COUNT + " = "
            + max + ";";
            top.remove(top.size() - 1);
            Collections.sort(top);
            Cursor c1 = db.rawQuery(query1, null);
            if (c1 != null)
                c1.moveToFirst();
            while (!c1.isAfterLast()) {
                String query2 = "SELECT * FROM " + TABLE_SCANNED + " WHERE " + SCANNED_COLUMN_DETAILS + " = \'"
                        + c1.getString(1) + "\' LIMIT 1;";
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

        return scannedList;
    }

    // Delete a Trend item
    public void deleteTrend(String sdetails) {
        if (db == null) {
            db = getWritableDatabase();
        }
        db.delete(TABLE_TRENDS, SCANNED_COLUMN_DETAILS + " = \'" + sdetails + "\'", null);
    }

    // Add a new user to the User table
    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_NUM, String.valueOf(user.getNum()));
        values.put(USER_COLUMN_DOB, String.valueOf(user.getDob()));
        values.put(USER_COLUMN_GEN, String.valueOf(user.getGender()));
        if (db == null) {
            db = getWritableDatabase();
        }
        db.insert(TABLE_USER, null, values);
    }

    // Get user from User table
    public User getUser() {
        if (db == null) {
            db = getReadableDatabase();
        }
        String query = "SELECT * FROM " + TABLE_USER + " WHERE 1;";
        Cursor c = db.rawQuery(query, null);
        if (c == null)
            return null;
        c.moveToLast();
        int _uid = Integer.parseInt(c.getString(c.getColumnIndex(USER_COLUMN_ID)));
        String num = c.getString(c.getColumnIndex(USER_COLUMN_NUM));
        String dob = c.getString(c.getColumnIndex(USER_COLUMN_DOB));
        String gender = c.getString(c.getColumnIndex(USER_COLUMN_GEN));
        User user = new User(_uid, num, dob, gender);
        try {
            return user;
        } finally {
            c.close();
        }
    }

    // Delete user from User table
    public void deleteUser() {
        if (db == null) {
            db = getWritableDatabase();
        }
        String query = "DELETE FROM " + TABLE_USER + " WHERE 1;";
        db.execSQL(query);

        // Add placeholder values for User table
        User user = new User("null", "null", "null");
        addUser(user);
    }

}
