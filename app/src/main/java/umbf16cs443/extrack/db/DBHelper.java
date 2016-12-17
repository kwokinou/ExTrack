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
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

//This method is pretty massive and could probably be broken up into sub-methods
//for easier readability.  That being said, I would suggest consulting the API
//documentation on the github wiki for method explanations.

//  https://github.com/kwokinou/ExTrack/wiki/DBHelper


public class DBHelper extends SQLiteOpenHelper {

    // For writing datetime
    Calendar calendar;
    Date currentTime;

    // Database Version
    private static final int DATABASE_VERSION = 11;
    // Database Name
    private static final String DATABASE_NAME = "exTrackDB";
    // Table Names
    private static final String TABLE_EXPENSES = "expenses";
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_EVENTS = "events";
    private static final String TABLE_EVENTS_TO_EXPENSES = "events_to_expenses";

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
    private static final String KEY_EXDATE = "expenses_date";
    private static final String KEY_EXCAT = "expenses_category";

    // EVENT Table - column names
    private static final String KEY_EVENT_NAME = "event_name";
    private static final String KEY_EVENT_LIMIT = "event_limit";
    private static final String KEY_EVENT_START_DATE = "event_start_date";
    private static final String KEY_EVENT_END_DATE = "event_end_date";
    private static final String KEY_EVENT_TOTAL = "event_total";

    // EVENT TO EXPENSE - mapping table column names
    private static final String KEY_EVENT_ID = "event_pk";
    private static final String KEY_EXPENSE_ID = "expense_fk";
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


    private static final String CREATE_TABLE_EXPENSES = "CREATE TABLE " +
            TABLE_EXPENSES + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_VENDOR + " TEXT," +
            KEY_CURRENCY + " INTEGER," +
            KEY_AMOUNT + " REAL," +
            KEY_RECEIPT + " TEXT," +
            KEY_EXDATE + " INTEGER," +
            KEY_EXCAT + " INTEGER," +
            KEY_CREATED_AT + " DATETIME" + ")";


    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE " +
            TABLE_EVENTS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_EVENT_NAME + " TEXT," +
            KEY_EVENT_LIMIT + " INTEGER," +
            KEY_EVENT_START_DATE + " INTEGER," +
            KEY_EVENT_END_DATE + " INTEGER," +
            KEY_EVENT_TOTAL + " REAL," +
            KEY_CREATED_AT + " DATETIME" + ")";

    private static final String CREATE_TABLE_EVENTS_TO_EXPENSES = "CREATE TABLE " +
            TABLE_EVENTS_TO_EXPENSES + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_EVENT_ID + " INTEGER," +
            KEY_EXPENSE_ID + " INTEGER," +
            KEY_CREATED_AT + " DATETIME" + ")";

    //todo relational table for events and expenses


    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_EXPENSES);
        db.execSQL(CREATE_TABLE_EVENTS);
        db.execSQL(CREATE_TABLE_EVENTS_TO_EXPENSES);


// todo implement relation tables

    }

// todo - have the on upgrade port old table over to new schema (if time)

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS_TO_EXPENSES);

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

        calendar = Calendar.getInstance();
        currentTime = calendar.getTime();

        values.put(KEY_CREATED_AT, currentTime.getTime());

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
                categoryList.add(category);
            } while (cursor.moveToNext());
        } else {
            Category none = new Category("None");
            addCategory(none);
            getAllCategories();
        }

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

        calendar = Calendar.getInstance();
        currentTime = calendar.getTime();

        values.put(KEY_CREATED_AT, currentTime.getTime());


        // updating row
        return db.update(TABLE_CATEGORIES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(category.getId())});

    }

    public void deleteCategory(Category category) {
        if (category.getCatName().equals("None")) {
            return;
        }


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
        values.put(KEY_EXDATE, expense.getExDateStamp());

        if (expense.getCategory() != null) {
            values.put(KEY_EXCAT, expense.getCategory().getId());
        } else {
            values.put(KEY_EXCAT, (byte[]) null);
        }

        calendar = Calendar.getInstance();
        currentTime = calendar.getTime();

        values.put(KEY_CREATED_AT, currentTime.getTime());

        db.insert(TABLE_EXPENSES, null, values);
        db.close();

    }

    public Expense fetchExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_EXPENSES, null,
                KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Category tempCat = null;
        String str = cursor.getString(6);

        if (str == null || str.isEmpty() || str.equalsIgnoreCase("null")) {
            tempCat = null;
        } else {
            tempCat = fetchCategory(Integer.parseInt(cursor.getString(6)));
        }

        Expense expense = new Expense(
                Integer.parseInt(cursor.getString(0)),    // id
                cursor.getString(1),                      // vendor
                cursor.getString(2),                      // currency
                Double.parseDouble(cursor.getString(3)),  // amount
                cursor.getString(4),                      // receipt
                Long.parseLong(cursor.getString(5)),      // dateStamp

                tempCat                                   // getcategories

        );
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
                Category tempCat = null;
                String cat = cursor.getString(6);

                if (cat == null || cat.isEmpty() || cat.equalsIgnoreCase("null")) {
                    tempCat = null;
                } else {
                    tempCat = fetchCategory(Integer.parseInt(cat));
                }

                Expense expense = new Expense(
                        Integer.parseInt(cursor.getString(0)),      //id
                        cursor.getString(1),                        //vendor
                        cursor.getString(2),                        //currency
                        Double.parseDouble(cursor.getString(3)),    //amount
                        cursor.getString(4),                        //receipt
                        Long.parseLong(cursor.getString(5)),      //dateStamp
                        //             fetchCategory(Integer.parseInt(cursor.getString(6)))
                        tempCat
                );
                expenseList.add(expense);
            } while (cursor.moveToNext());
        }

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

        if (expense.getCategory() != null) {
            values.put(KEY_EXCAT, expense.getCategory().getId());
        } else {
            values.put(KEY_EXCAT, (byte[]) null);
        }

        calendar = Calendar.getInstance();
        currentTime = calendar.getTime();

        values.put(KEY_CREATED_AT, currentTime.getTime());

        return db.update(TABLE_EXPENSES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(expense.getId())});

    }

    public void deleteExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSES, KEY_ID + " = ?",
                new String[]{String.valueOf(expense.getId())});

        db.delete(TABLE_EVENTS_TO_EXPENSES, KEY_EXPENSE_ID + " =?",
                new String[]{String.valueOf(expense.getId())});

        db.close();
    }


// ####################################################################### //
// Event Helper Method
// ####################################################################### //


    public void addEvent(Event event) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        // set event values
        values.put(KEY_EVENT_NAME, event.getEventName());
        values.put(KEY_EVENT_LIMIT, event.getLimit());

        if (event.getStartDate() != null) {
            values.put(KEY_EVENT_START_DATE, event.getStartDate().getTime());
        } else {
            values.put(KEY_EVENT_START_DATE, 0);

        }
        if (event.getEndDate() != null) {
            values.put(KEY_EVENT_END_DATE, event.getEndDate().getTime());
        } else {
            values.put(KEY_EVENT_END_DATE, 0);

        }

        calendar = Calendar.getInstance();
        currentTime = calendar.getTime();

        values.put(KEY_EVENT_TOTAL, event.getEventTotal());

        values.put(KEY_CREATED_AT, currentTime.getTime());

        long eventID = db.insert(TABLE_EVENTS, null, values);


        // todo map events to expenses

        if (event.getExpenses() != null) {


            ArrayList<Expense> eventExpenses = event.getExpenses();

            for (Expense e : eventExpenses) {
                ContentValues exvalues = new ContentValues();
                exvalues.put(KEY_EVENT_ID, eventID);
                exvalues.put(KEY_EXPENSE_ID, e.getId());
                db.insert(TABLE_EVENTS_TO_EXPENSES, null, exvalues);

            }


            // todo add events to mapping database
            db.close();
        }
    }

    public Event fetchEvent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Expense> expenses = new ArrayList<Expense>();

        Cursor cursor = db.query(TABLE_EVENTS, null,
                KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Event event = new Event(
                Integer.parseInt(cursor.getString(0)),   // id
                cursor.getString(1),                     // name
                expenses,                                // expenses
                Long.parseLong(cursor.getString(2)),     // limit
                Long.parseLong(cursor.getString(3)),     // start date
                Long.parseLong(cursor.getString(4))      // end date
        );

        ArrayList<Expense> eventExpenses = new ArrayList<Expense>();

        int eventID = event.getEventId();

        cursor = db.query(TABLE_EVENTS_TO_EXPENSES, null, KEY_EVENT_ID + "=" +
                String.valueOf
                        (eventID), null, null, null, null);

        if (cursor.moveToFirst()) {
            do {

                int expenseID = Integer.parseInt(cursor.getString(2));
                Expense nextExpense = fetchExpense(expenseID);
                event.addExpense(nextExpense);

            } while (cursor.moveToNext());

            event.setExpenses(eventExpenses);

        }

        return event;

    }


    public ArrayList<Event> getAllEvents() {

        ArrayList<Event> eventList = new ArrayList<Event>();
        ArrayList<Expense> expenses = new ArrayList<Expense>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor expenseCursor;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Event event = new Event(
                        Integer.parseInt(cursor.getString(0)),   // id
                        cursor.getString(1),                     // name
                        expenses,                                    // expenses
                        Long.parseLong(cursor.getString(2)),     // limit
                        Long.parseLong(cursor.getString(3)),     // start date
                        Long.parseLong(cursor.getString(4))      // end date
                );

                int eventID = event.getEventId();

                expenseCursor = db.query(TABLE_EVENTS_TO_EXPENSES, null,
                        KEY_EVENT_ID + "=" +
                                String.valueOf
                                        (eventID), null, null, null, null);

                if (expenseCursor.moveToFirst()) {
                    do {

                        int expenseID = Integer.parseInt(expenseCursor.getString(2));
                        Expense nextExpense = fetchExpense(expenseID);
                        event.addExpense(nextExpense);

                    } while (expenseCursor.moveToNext());
                }
                eventList.add(event);

            } while (cursor.moveToNext());


        }


        return eventList;

    }

    public int getEventCount() {
        int num;

        String countQuery = "SELECT  * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        num = cursor.getCount();
        cursor.close();
        // return count
        return num;

    }

    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // set event values
        values.put(KEY_EVENT_NAME, event.getEventName());
        values.put(KEY_EVENT_LIMIT, event.getLimit());
        if (event.getStartDate() != null) {
            values.put(KEY_EVENT_START_DATE, event.getStartDate().getTime());
        }
        if (event.getEndDate() != null) {
            values.put(KEY_EVENT_END_DATE, event.getEndDate().getTime());
        }

        calendar = Calendar.getInstance();
        currentTime = calendar.getTime();

        values.put(KEY_CREATED_AT, currentTime.getTime());

        db.delete(TABLE_EVENTS_TO_EXPENSES, KEY_EVENT_ID + " =?",
                new String[]{String.valueOf(event.getEventId())});

        int eventID = event.getEventId();

        if (event.getExpenses() != null) {

            ContentValues exvalues = new ContentValues();

            ArrayList<Expense> eventExpenses = event.getExpenses();

            for (Expense e : eventExpenses) {
                exvalues.put(KEY_EVENT_ID, eventID);
                exvalues.put(KEY_EXPENSE_ID, e.getId());
                db.insert(TABLE_EVENTS_TO_EXPENSES, null, exvalues);
            }
            
        }

        eventID = db.update(TABLE_EVENTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(event.getEventId())});

        db.close();

        return eventID;

    }

    public void deleteEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, KEY_ID + " = ?",
                new String[]{String.valueOf(event.getEventId())});

        db.delete(TABLE_EVENTS_TO_EXPENSES, KEY_EVENT_ID + " =?",
                new String[]{String.valueOf(event.getEventId())});

        db.close();
    }


    public ArrayList<Event> getEventsByExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Event> events = new ArrayList<>();
        int expenseID = expense.getId();

        Cursor cursor = db.query(TABLE_EVENTS_TO_EXPENSES, null,
                KEY_EXPENSE_ID + "=" +
                        String.valueOf
                                (expenseID), null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Event event = fetchEvent(Integer.parseInt(cursor.getString(1)));
                events.add(event);

            } while (cursor.moveToNext());
        }
        return events;

    }

    // ***********************************************************************
    // STATISTICS METHODS
    // ***********************************************************************


    // Returns the sum of all expenses

    public Double getGrandTotal() {

        Double grandTotal = 0.0;
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                grandTotal += Double.parseDouble(cursor.getString(3));

            } while (cursor.moveToNext());
        }

        return grandTotal;

    }

    public Hashtable<Category, Double> getCategoryTotals() {
        Hashtable<Category, Double> totalsByCategory = new
                Hashtable<Category, Double>();


        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Index of all possible Categories
        ArrayList<Category> catIndex = getAllCategories();


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                if (cursor.getString(6) != null) {

                    Category tempCat = fetchCategory(Integer.parseInt(cursor.getString(6)));
                    int tempIndex = catIndex.indexOf(tempCat);

                    double catAmount = Double.parseDouble(cursor.getString(3));

                    // This is a weird workaround as hashtables and
                    // arraylists work differently.  Despite overriding the
                    // .equals method of category, hashtable defines equality
                    // based on object id not .equals.  Arraylist, however,
                    // does and therefor can be checked against to see if
                    // tempkey is in the db.

                    if (totalsByCategory.containsKey(catIndex.get(tempIndex))) {
                        double newAmount = totalsByCategory.get(catIndex.get
                                (tempIndex));
                        newAmount += catAmount;
                        Double updatedAmount = new Double(newAmount);

                        totalsByCategory.remove(catIndex.get(tempIndex));
                        totalsByCategory.put(catIndex.get(tempIndex),
                                newAmount);

                    } else {
                        double newAmount = catAmount;
                        totalsByCategory.put(catIndex.get(tempIndex), (new
                                Double(newAmount)));
                    }
                }
            } while (cursor.moveToNext());
        }

        return totalsByCategory;

    }

    // ************************************************************************
    // QUery Methods
    // ************************************************************************

    public ArrayList<Expense> getExpensesByDate(Date start, Date end){
        ArrayList<Expense> expenses = new ArrayList<Expense>();
        long startDate;
        long endDate;

        String expenseQuery = "SELECT  * FROM " + TABLE_EXPENSES + " WHERE ";
        SQLiteDatabase db = this.getReadableDatabase();


        if(start == null && end == null){
            return getAllExpenses();
        }
        else if(start != null){
            startDate = start.getTime();
            expenseQuery = expenseQuery + KEY_EXDATE + " > " + startDate;

        }
        else if(end != null){
            endDate = end.getTime();
            expenseQuery = expenseQuery + KEY_EXDATE + " < " + endDate;

        }
        else{
            startDate = start.getTime();
            endDate = end.getTime();
            expenseQuery = expenseQuery + KEY_EXDATE + " BETWEEN " +startDate
            + " AND " + endDate;
        }

        Cursor cursor = db.rawQuery(expenseQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Category tempCat = null;
                String cat = cursor.getString(6);

                if (cat == null || cat.isEmpty() || cat.equalsIgnoreCase("null")) {
                    tempCat = null;
                } else {
                    tempCat = fetchCategory(Integer.parseInt(cat));
                }

                Expense expense = new Expense(
                        Integer.parseInt(cursor.getString(0)),      //id
                        cursor.getString(1),                        //vendor
                        cursor.getString(2),                        //currency
                        Double.parseDouble(cursor.getString(3)),    //amount
                        cursor.getString(4),                        //receipt
                        Long.parseLong(cursor.getString(5)),      //dateStamp
                        //             fetchCategory(Integer.parseInt(cursor.getString(6)))
                        tempCat
                );
                expenses.add(expense);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return expenses;
    }


    public ArrayList<Expense> getExpensesByCategory(Category category) {

        ArrayList<Expense> expenses = new ArrayList<Expense>();
        SQLiteDatabase db = this.getWritableDatabase();
        int catID = category.getId();

        Cursor cursor = db.query(TABLE_EVENTS_TO_EXPENSES, null,
                KEY_EXCAT + "=" +
                        String.valueOf
                                (catID), null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Category tempCat = null;
                String cat = cursor.getString(6);

                if (cat == null || cat.isEmpty() || cat.equalsIgnoreCase("null")) {
                    tempCat = null;
                } else {
                    tempCat = fetchCategory(Integer.parseInt(cat));
                }

                Expense expense = new Expense(
                        Integer.parseInt(cursor.getString(0)),      //id
                        cursor.getString(1),                        //vendor
                        cursor.getString(2),                        //currency
                        Double.parseDouble(cursor.getString(3)),    //amount
                        cursor.getString(4),                        //receipt
                        Long.parseLong(cursor.getString(5)),      //dateStamp
                        //             fetchCategory(Integer.parseInt(cursor.getString(6)))
                        tempCat
                );
                expenses.add(expense);

            } while (cursor.moveToNext());
        }
        return expenses;

    }





}