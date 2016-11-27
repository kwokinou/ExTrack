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

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Category;
import umbf16cs443.extrack.db.models.Expense;

/**
 * Created by kwokin on 11/24/2016.
 */
public class EditExpActivity extends AppCompatActivity {
    Spinner spinner;//select category
    ArrayAdapter<Category> adapter; //adapter for spinner
    ArrayList<Category> catArray;

    ArrayList<Expense> expenses;
    DBHelper db;
    Expense exp; //used to retrieve the expense selected by user
    int position; //used to retrieve the position of the expense selected by user
    //Category prevCat, curCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_an_exp);
        setTitle("Edit an Expense");
        Intent mIntent = getIntent();
        position = mIntent.getIntExtra("position", 0); //retrieve selected position

        //TODO need to display currency, and receipt img based on the given position selected by user
        db = new DBHelper(this);
        expenses = db.getAllExpenses();
        catArray = db.getAllCategories();

        exp = expenses.get(position);
      //  prevCat = exp.getCategory();

        ((EditText) findViewById(R.id.vendor2)).setText(exp.getExVendor());//display saved vendor name

        ((EditText) findViewById(R.id.amount2)).setText(exp.getExAmount().toString());//display saved amount

        //set up spinner for category selection.
        //TODO need to display saved category in spinner
        spinner = (Spinner) findViewById(R.id.spinner2);

        adapter = new ArrayAdapter<Category>(this, android.R.layout
                .simple_spinner_item,
                catArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
       // spinner.setSelection(exp.getCategory().getId());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
              //  Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){}
        });
    }

    //include save and delete button in menu bar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_action_btns, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            //user clicked on save to store updated Expense object
            //return to activity showing all Expenses
            case R.id.save:
                EditText vendor = (EditText) findViewById(R.id.vendor2);
                EditText amount = (EditText) findViewById(R.id.amount2);

                //TODO update selected expense with entered data
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
    public void addCategory(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(EditExpActivity.this);

        alert.setMessage("Enter Category Name");
        alert.setTitle("Add Category");
        alert.setView(edittext);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String YouEditTextValue = edittext.getText().toString();
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


    //code for setting up DatePicker**************************************************************
    public void pickDate(View view) {

        //EDIT NEEDED: this should show the previously selected date.
        AddExpActivity.DatePickerFragment dateFrag = new AddExpActivity.DatePickerFragment();
        dateFrag.show(getSupportFragmentManager(), "date");
    }

    public void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.transDate2)).setText(dateFormat.format(calendar.getTime()));
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
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

