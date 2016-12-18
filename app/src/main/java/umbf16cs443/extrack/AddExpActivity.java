package umbf16cs443.extrack;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Set;

import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Category;
import umbf16cs443.extrack.db.models.Expense;
import umbf16cs443.extrack.utility.StorageHelper;

/**
 * Created by kwokin on 10/30/2016.
 */

//activity to prompt user enter new expense information
public class AddExpActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener {


    ArrayAdapter<Category> adapter; // adapter for spinner
    ArrayList<Category> catArray;   // categories array

    // New Expense Variables
    Currency exCurrency;
    // threw this up here due to scope issues
    Currency tempCurrency;

    Date exDate;
    String receiptString = null;

    // View Components for New Expense
    EditText vendor;                    // the vendor input
    EditText amount;                    // the ammount input
    TextView currencyText;              // displays currency code
    TextView dateText;                  // displays date selected
    Spinner catSpinner;                 // select category
    Category exCat;                     // expense category
    DBHelper db;

    //Image receipt attachment
    Button attachReceipt;
    ImageView receiptThumbnail;
    private static final int CAMERA_CAPTURE_IMAGE = 1;

    // Permissions Check
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_an_exp);
        setTitle("Add an Expense");

        //Initialise Attach Receipt Button
        attachReceipt = (Button)findViewById(R.id.attReceipt1);
        receiptThumbnail = (ImageView)findViewById(R.id.imageButton1);
        receiptThumbnail.setVisibility(View.INVISIBLE);

        //get permissions
        askForStoragePermission();


        db = new DBHelper(getApplicationContext());
        catArray = db.getAllCategories();
        Calendar calendar = Calendar.getInstance();


        // Set New Expense Variables To Defaults
        exDate = calendar.getTime();
        exCurrency = Currency.getInstance("USD");

        // Identify views
        vendor = (EditText) findViewById(R.id.vendor);
        amount = (EditText) findViewById(R.id.amount);
        currencyText = (TextView) findViewById(R.id.currency_text);
        dateText = (TextView) findViewById(R.id.date_text);


        // Set Currency Text
        currencyText.setText(exCurrency.toString());
        // Date Formatter Variable
        SimpleDateFormat simpleDate = new SimpleDateFormat("MM/dd/yyyy");
        dateText.setText(simpleDate.format(exDate));

        //set up spinner for category selection
        catSpinner = (Spinner) findViewById(R.id.spinner1);
        //category_names is defined in strings xml as an example to populate spinner adapter
        adapter = new ArrayAdapter<Category>(this, android.R.layout
                .simple_spinner_item,
                catArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(adapter);
        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exCat = (Category) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    //include save button in menu bar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_action_btn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            //user clicked on save to store Expense object
            //return to activity showing all Expenses
            case R.id.save:

                if(vendor.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),R.string
                            .blank_vendor_error , Toast.LENGTH_SHORT ).show();
                    return false;
                }

                if(amount.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),R.string
                            .blank_amount_error , Toast.LENGTH_SHORT ).show();
                    return false;
                }

                Expense expense = new Expense(
                        vendor.getText().toString(),        //vendor
                        exCurrency,                               //currency
                        Double.parseDouble(amount.getText().toString()), //amount
                        receiptString, //receipt
                        exDate, // date
                        exCat); //category

                Log.v(TAG,"expense:vendor = "+expense.getExVendor()+"\ncurrency = "+expense.getCurrencyCode());

                db.addExpense(expense);
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    //user clicked on add category to create a new category*******************************
    public void addCategory(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(AddExpActivity.this);

        alert.setMessage("Enter Category Name");
        alert.setTitle("Add Category");
        alert.setView(edittext);



        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String YouEditTextValue = edittext.getText().toString();
                if (YouEditTextValue.length() > 0) {
                    DBHelper db = new DBHelper(getApplicationContext());
                    Category newCat = new Category(YouEditTextValue);

                    db.addCategory(newCat);

                    catArray = db.getAllCategories();

                    adapter = new ArrayAdapter<Category>(getApplicationContext(), android.R.layout
                            .simple_spinner_item,
                            catArray);
                    adapter.setDropDownViewResource(android.R.layout
                            .simple_spinner_dropdown_item);
                    catSpinner.setAdapter(adapter);

                }

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();


    }
//end of addCategory**************************************************************************

    // add currency***************************************************************************
    public void setCurrency(View view) {

        tempCurrency = exCurrency;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Spinner currencySpinner = new Spinner(AddExpActivity.this);

        alert.setMessage("Unfortunately, due to the cruel reality of software" +
                " deadlines, version 1 of ExTrack does not support setting " +
                "currency beyond the default currency of $USD.  Check back " +
                "soon though, as we are working hard to implement all of your" +
                " favorite currencies!");
        alert.setTitle("Select Currency");
//
//        Set<Currency> allCurrencies = Currency.getAvailableCurrencies();
//        Currency[] currencies = allCurrencies.toArray(new
//                Currency[allCurrencies.size()]);
//
//
//        ArrayAdapter<Currency> currencyAdapter = new ArrayAdapter<Currency>
//                (getApplicationContext(), android.R.layout
//                        .simple_spinner_item, currencies);
//
//
//        currencyAdapter.setDropDownViewResource(android.R.layout
//                .simple_spinner_dropdown_item);
//        currencyAdapter.setDropDownViewResource(android.R.layout
//                .simple_spinner_dropdown_item);
//        currencySpinner.setAdapter(currencyAdapter);
//
//        alert.setView(currencySpinner);

        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tempCurrency = (Currency) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                exCurrency = tempCurrency;
//                currencyText.setText(exCurrency.toString());
        return;

            }
        });

//        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
        alert.show();
    }
//end of currency setting*************************************************************************


    //code for setting up DatePicker**************************************************************
    public void pickDate(View view) {
        DatePickerFragment dateFrag = new DatePickerFragment();
        dateFrag.show(getSupportFragmentManager(), "date");
    }

    public void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.transDate)).setText(dateFormat.format(calendar.getTime()));
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        // Update Date
        exDate = cal.getTime();
        // Update Date Text View
        SimpleDateFormat simpleDate = new SimpleDateFormat("MM/dd/yyyy");
        dateText.setText(simpleDate.format(exDate));
        //Notify UI of Change

    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        }
    }
//end of code for setting up DatePicker*********************************************************

    // Attach Receipt Method
    private static final String TAG = "Extrack:AddExpActivity";
    public void attachReceipt(View v){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        StorageHelper storageHelper = new StorageHelper();
        File directory = storageHelper.getPictureDirectory("IMG"); //have to pass Specific Prefic to the image name
        receiptString = directory.getAbsolutePath();
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(directory));
        startActivityForResult(cameraIntent, CAMERA_CAPTURE_IMAGE);
        Toast.makeText(getApplicationContext(), "Image saved to SD Card", Toast.LENGTH_SHORT).show();
    }

    // Permission Check for Storage
    // Storage Permission
    private void askForStoragePermission() {
        if (ContextCompat.checkSelfPermission(AddExpActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddExpActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(AddExpActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                ActivityCompat.requestPermissions(AddExpActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else {
            Toast.makeText(this, "Storage Write permission is already granted.", Toast.LENGTH_SHORT).show();
        }
        if (ContextCompat.checkSelfPermission(AddExpActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddExpActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(AddExpActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                ActivityCompat.requestPermissions(AddExpActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        } else {
            Toast.makeText(this, "Storage READ permission is already granted.", Toast.LENGTH_SHORT).show();
        }
    }
}