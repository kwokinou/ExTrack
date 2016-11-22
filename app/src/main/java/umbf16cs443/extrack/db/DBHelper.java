package umbf16cs443.extrack.db;

// BE SURE TO CREDIT ICON CREATOR!!

//<div>Icons made by <a href="http://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>


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

//    private static final String TABLE_REPORTS = "reports";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";


    // CATEGORIES Table - column names
    private static final String KEY_CAT_NAME = "category_name";

    // EXPENSES Table - column names
    private static final String KEY_VENDOR = "expenses_vendor";
    private static final String KEY_CURRENCY = "expenses_currency";
    private static final String KEY_AMOUNT = "expenses_amount";
    private static final String KEY_RECEIPT = "expenses_receipt";
    //    private static final String KEY_EXYEAR = "expense_year";
//    private static final String KEY_EXMONTH = "expense_month";
//   private static final String KEY_EXDAY = "expense_day";
    private static final String KEY_EXDATE = "expenses_date";
    private static final String KEY_EXCAT = "expenses_category";

    // REPORTS Table - column names
    private static final String KEY_REPORT_NAME = "report_name";
    //start date
    //end date


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
            TABLE_EXPENSES + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_VENDOR + " TEXT," +
            KEY_CURRENCY + " INTEGER," +
            KEY_AMOUNT + " REAL," +
            KEY_RECEIPT + " TEXT," +
            //          KEY_EXYEAR + " INTEGER" +
            //           KEY_EXMONTH + " INTEGER" +
            //           KEY_EXDAY + " INTEGER" +
            KEY_EXDATE + " INTEGER" +
            KEY_EXCAT  + " INTEGER" +
            KEY_CREATED_AT + " DATETIME" + ")";

    //todo report table

    //todo relational tables


    @Override
    public void onCreate(SQLiteDatabase db) {

        //todo: these

        // creating required tables
        db.execSQL(CREATE_TABLE_EXPENSES);
        db.execSQL(CREATE_TABLE_CATEGORIES);
//        db.execSQL(CREATE_TABLE_REPORTS);

// todo implement relation tables

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORTS);

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
    public ArrayList<Category> getAllCategories() {

        ArrayList<Category> categoryList = new ArrayList<Category>();
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


// ####################################################################### //
// Expense Helper Method
// ####################################################################### //

    public void addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_VENDOR, expense.getExVendor());
        values.put(KEY_CURRENCY, expense.getExCurrencyCode());
        values.put(KEY_AMOUNT, expense.getExAmount());
        values.put(KEY_RECEIPT, expense.getExReceipt());
//        values.put(KEY_EXYEAR, expense.getExYear());
//        values.put(KEY_EXMONTH, expense.getExMonth());
//        values.put(KEY_EXDAY, expense.getExDay());
        values.put(KEY_EXDATE, expense.getExDateStamp());


        if(expense.getCategory() != null) {
            values.put(KEY_EXCAT, expense.getCategory().getId());
        }

        db.insert(TABLE_EXPENSES, null, values);
        db.close();

        //todo map categories
    }

    public Expense fetchExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_EXPENSES, null,
                KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Expense expense = new Expense(
                Integer.parseInt(cursor.getString(0)),      //id
                cursor.getString(1),                        //vendor
                cursor.getString(2),                        //currency
                Double.parseDouble(cursor.getString(3)),    //amount
                cursor.getString(4),                        //receipt
                Long.parseLong(cursor.getString(5)),      //dateStamp

                null                                        //todo getcategories


        );
        // return contact
        return expense;
    }

    public ArrayList<Expense> getAllExpenses() {

        ArrayList<Expense> expenseList = new ArrayList<Expense>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense(
                        Integer.parseInt(cursor.getString(0)),      //id
                        cursor.getString(1),                        //vendor
                        cursor.getString(2),                        //currency
                        Double.parseDouble(cursor.getString(3)),    //amount
                        cursor.getString(4),                        //receipt
                        Long.parseLong(cursor.getString(5)),      //dateStamp
           //             fetchCategory(Integer.parseInt(cursor.getString(6)))
                        null //TODO FIX CATEGORIES
                );
                expenseList.add(expense);
            } while (cursor.moveToNext());
        }

        // return contact list
        return expenseList;

    }

    public int getExpenseCount() {
        int num;

        String countQuery = "SELECT  * FROM " + TABLE_EXPENSES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        num = cursor.getCount();
        cursor.close();

        // return count
        return num;

    }

    public int updateExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_VENDOR, expense.getExVendor());
        values.put(KEY_CURRENCY, expense.getExCurrencyCode());
        values.put(KEY_AMOUNT, expense.getExAmount());
        values.put(KEY_RECEIPT, expense.getExReceipt());
        values.put(KEY_EXDATE, expense.getExDateStamp());
        values.put(KEY_EXCAT, expense.getCategory().getId());

        return db.update(TABLE_EXPENSES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(expense.getId())});

    }

    public void deleteExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSES, KEY_ID + " = ?",
                new String[]{String.valueOf(expense.getId())});
        db.close();
    }


// ####################################################################### //
// Report Helper Method
// ####################################################################### //

}
//coming soon