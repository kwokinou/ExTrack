package umbf16cs443.extrack;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Category;
import umbf16cs443.extrack.db.models.Expense;

/**
 * Created by kwokin on 10/30/2016.
 */

//activity to prompt user enter new expense information
public class EditExpActivity extends AppCompatActivity {


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

    ArrayList<Expense> expenses;
    DBHelper db;
    Expense exp; //Expense to be edited
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_an_exp);
        setTitle("Edit an Expense");
        Intent mIntent = getIntent();
        position = mIntent.getIntExtra("position", 0); //retrieve selected position


        db = new DBHelper(this);
        expenses = db.getAllExpenses();
        exp = expenses.get(position);
        catArray = db.getAllCategories();
        Calendar calendar = Calendar.getInstance();

        ((EditText) findViewById(R.id.vendor)).setText(exp.getExVendor());//display saved vendor name

        ((EditText) findViewById(R.id.amount)).setText(exp.getExAmount().toString());//display saved amount

        //TODO display previously filled in expense date, category, and event
        // Set New Expense Variables To Defaults
        exDate = calendar.getTime();

        exCurrency = Currency.getInstance("USD");
        exCurrency = exp.getCurrency();

        // Identify views
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
        getMenuInflater().inflate(R.menu.edit_action_btns, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            //user clicked on save to store edited Expense object
            //return to activity showing all Expenses
            case R.id.save:

                //TODO NEED TO UPDATE DATE, CATEGORY AND EVENT
                vendor = (EditText) findViewById(R.id.vendor);
                amount = (EditText) findViewById(R.id.amount);

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

                finish();
                break;

            //user wants to delete the selected Expense object
            //display alertdialog to confirm deletion
            case R.id.delete:
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
                deleteDialog.setMessage("Delete expense permanently?");
                deleteDialog.setTitle("Delete Expense");

                deleteDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteExpense(exp);//delete Expense from database
                        finish();
                    }
                });
                deleteDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                deleteDialog.show();
                break;


            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    //user clicked on add category to create a new category*******************************
    public void addCategory(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(EditExpActivity.this);

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
        final Spinner currencySpinner = new Spinner(EditExpActivity.this);

        alert.setMessage("Select the currency that corresponds to this " +
                "expense.");
        alert.setTitle("Select Currency");

        Set<Currency> allCurrencies = Currency.getAvailableCurrencies();
        Currency[] currencies = allCurrencies.toArray(new
                Currency[allCurrencies.size()]);


        ArrayAdapter<Currency> currencyAdapter = new ArrayAdapter<Currency>
                (getApplicationContext(), android.R.layout
                        .simple_spinner_item, currencies);

        currencyAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        currencyAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        currencySpinner.setAdapter(currencyAdapter);

        alert.setView(currencySpinner);

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
                exCurrency = tempCurrency;
                currencyText.setText(exCurrency.toString());
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();
    }
//end of currency*********************************************************************************

    //code for setting up DatePicker**************************************************************
    public void pickDate2(View view) {
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
}