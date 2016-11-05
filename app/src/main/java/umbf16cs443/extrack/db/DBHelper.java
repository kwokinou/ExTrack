package umbf16cs443.extrack.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import umbf16cs443.extrack.db.models.*; //add all models to helper


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//This method is pretty massive and could probably be broken up into sub-methods
//for easier readability.  That being said, I would suggest consulting the API
//documentation on the github wiki for method explanations.

//  https://github.com/kwokinou/ExTrack/wiki/DBHelper



public class DBHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "exTrackDB";
    // Table Names
    private static final String TABLE_EXPENSES = "expenses";
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_REPORTS = "reports";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // EXPENSES TABLE



    // CATEGORIES Table - column names
    private static final String KEY_CAT_NAME = "category_name";

    // REPORTS Table - column names
    // Coming Soon

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

// ####################################################################### //
// DATABASE CREATION STRINGS
// ####################################################################### //
// Just one big concatination of sub-strings to make variables for SQL
// creation statements

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " +
            TABLE_CATEGORIES + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_CAT_NAME + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    //todo expense table
    private static final String CREATE_TABLE_EXPENSES = "CREATE TABLE " +
            TABLE_EXPENSES + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_CAT_NAME + " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";

    //todo report table

    //todo relational tables


    @Override
    public void onCreate(SQLiteDatabase db) {


        //todo: these

        // creating required tables
//        db.execSQL(CREATE_TABLE_EXPENSES);
        db.execSQL(CREATE_TABLE_CATEGORIES);
//        db.execSQL(CREATE_TABLE_REPORTS);

// todo implement relation tables

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORTS);

        // create new tables
        onCreate(db);
    }


// ####################################################################### //
// Category Helper Method
// ####################################################################### //

    // add a Category to table
    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CAT_NAME, category.getCatName());

        db.insert(TABLE_CATEGORIES, null, values);
        db.close();

    }

    // fetch a Category
    public Category fetchCategory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORIES, new String[]{KEY_ID,
                        KEY_CAT_NAME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Category category = new Category(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        return category;
    }

    // list all Categories
    public List<Category> getAllCategories() {

        List<Category> categoryList = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(Integer.parseInt(cursor.getString(0)));
                category.setCatName(cursor.getString(1));
                // Adding contact to list
                categoryList.add(category);
            } while (cursor.moveToNext());
        }

        // return contact list
        return categoryList;

    }

    public int getCategoriesCount() {
        int num;

        String countQuery = "SELECT  * FROM " + TABLE_CATEGORIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        num = cursor.getCount();
        cursor.close();

        // return count
        return num;

    }

    public int updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CAT_NAME, category.getCatName());

        // updating row
        return db.update(TABLE_CATEGORIES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(category.getId())});

    }

    public void deleteCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORIES, KEY_ID + " = ?",
                new String[]{String.valueOf(category.getId())});
        db.close();
    }

}


// ####################################################################### //
// Expense Helper Method
// ####################################################################### //




// ####################################################################### //
// Report Helper Method
// ####################################################################### //


//coming soon