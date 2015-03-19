package androidassignment.crossover.com.androidassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "db_auction";

    // Table Names
    private static final String TABLE_USER = "tb_users";
    private static final String TABLE_AUCTION_ITEM = "tb_auction_items";
    private static final String TABLE_BID = "tb_bid";

    // Common column names
    private static final String TABLE_USER_KEY_ID = "id";
    private static final String USER_NAME = "user_name";
    private static final String HASHED_PASSWORD = "hashed_password";
    private static final String EMAIL = "email";

    // NOTES Table - column nmaes
    private static final String TABLE_AUCTION_ITEM_KEY_ID = "id";
    private static final String TITLE = "title";
    private static final String CATEGORY = "category";
    private static final String DESCRIPTION = "description";
    private static final String MIN_BID = "min_bid";
    private static final String END_DATE = "end_date";
    private static final String LOCATION = "location";
    private static final String USER_ID = "user_id";

    private static final String TABLE_BID_KEY_ID = "id";
    private static final String TABLE_BID_USER_ID = "user_id";
    private static final String TABLE_BID_AUCTION_ITEM_ID = "auction_item_id";
    private static final String AMOUNT = "amount";


    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + TABLE_USER_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_NAME
            + " VARCHAR(45)," + HASHED_PASSWORD + " VARCHAR(256)," + EMAIL
            + " VARCHAR(50)" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_AUCTION_ITEM = "CREATE TABLE " + TABLE_AUCTION_ITEM
            + "(" + TABLE_AUCTION_ITEM_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TITLE
            + " VARCHAR(80)," + CATEGORY + " VARCHAR(45)," + MIN_BID + " VARCHAR(45),"
            + END_DATE + " DATETIME," + LOCATION + " VARCHAR(45),"
            + USER_ID + " INTEGER," + DESCRIPTION
            + " TEXT" + ")";

    private static final String CREATE_TABLE_BID = "CREATE TABLE " + TABLE_BID
            + "(" + TABLE_BID_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TABLE_BID_USER_ID
            + " INTEGER," + TABLE_BID_AUCTION_ITEM_ID + " INTEGER," + AMOUNT + " VARCHAR(45)"
            + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_AUCTION_ITEM);
        db.execSQL(CREATE_TABLE_BID);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUCTION_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_BID);
        // create new tables
        onCreate(db);
    }

    public Boolean InsertUser(UserProfile user) {


        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.getName());
        values.put(HASHED_PASSWORD, user.getHashedPassword());
        values.put(EMAIL, user.getEmail());
        long temp = database.insert(TABLE_USER, null, values);
        database.close();
        if (temp == -1)
            return false;
        else
            return true;

    }

    public int ImageIndex() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  MAX(" + TABLE_AUCTION_ITEM_KEY_ID + ") AS " + TABLE_AUCTION_ITEM_KEY_ID + "  FROM " + TABLE_USER + "';";

        Cursor c = null;
        int index = 0;
        try {
            c = db.rawQuery(selectQuery, null);
            c.moveToFirst();
            index = c.getInt(c.getColumnIndex(TABLE_AUCTION_ITEM_KEY_ID));
        } catch (Exception e) {
        }

        db.close();
        return (index + 1);
    }

    public int InsertAuctionItem(AuctionItem item) {


        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, item.getTitle());
        values.put(CATEGORY, item.getCategory());
        values.put(DESCRIPTION, item.getDescription());
        values.put(MIN_BID, item.getMinBid());
        values.put(END_DATE, item.getEndDate());
        values.put(LOCATION, item.getLocation());
        values.put(USER_ID, item.getUserId());


        long temp = database.insert(TABLE_AUCTION_ITEM, null, values);
        database.close();
        return (int)temp;

    }

    public Boolean VerifyUser(UserProfile user) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + USER_NAME + " = '" + user.getName() + "';";

        Cursor c = null;
        Boolean check = false;
        try {
            c = db.rawQuery(selectQuery, null);
            c.moveToFirst();
            check = user.getHashedPassword().equals(c.getString(c.getColumnIndex(HASHED_PASSWORD)));
        } catch (Exception e) {
        }
        if (c != null)

            db.close();
        if (check)
            return true;
        else
            return false;

    }

    public int GetUserId(String UserName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  "+TABLE_USER_KEY_ID+" FROM " + TABLE_USER + " WHERE "
                + USER_NAME + " = '" + UserName + "';";

        Cursor c = null;
        int index = 0;
        try {
            c = db.rawQuery(selectQuery, null);
            c.moveToFirst();
            index = c.getInt(c.getColumnIndex(TABLE_USER_KEY_ID));
        } catch (Exception e) {
        }
        if (c != null)

            db.close();
        return index;
    }

    public int ChangeUserPassword(String userName, String NewPassword) {

        SQLiteDatabase db = this.getReadableDatabase();


        ContentValues values = new ContentValues();
        values.put(HASHED_PASSWORD, NewPassword);

        // updating row
        return db.update(TABLE_USER, values, USER_NAME + " = ?",
                new String[]{String.valueOf(userName)});
    }

    public List<AuctionItem> getActiveAuctions() {
        List<AuctionItem> Items = new ArrayList<AuctionItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_AUCTION_ITEM;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                AuctionItem item = new AuctionItem();
                item.setImageLoc(c.getInt(c.getColumnIndex(TABLE_AUCTION_ITEM_KEY_ID)));
                item.setTitle((c.getString(c.getColumnIndex(TITLE))));
                item.setCategory((c.getString(c.getColumnIndex(CATEGORY))));
                item.setDescription((c.getString(c.getColumnIndex(DESCRIPTION))));
                item.setMinBid((c.getString(c.getColumnIndex(MIN_BID))));
                item.setEndDate((c.getString(c.getColumnIndex(END_DATE))));
                item.setLocation((c.getString(c.getColumnIndex(LOCATION))));
                item.setUserId((c.getInt(c.getColumnIndex(USER_ID))));

                // adding to todo list
                Items.add(item);
            } while (c.moveToNext());
        }

        return Items;
    }
}