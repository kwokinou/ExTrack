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
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by kwokin on 11/24/2016.
 */
public class EditExpActivity extends AppCompatActivity {
    Spinner spinner;//select category
    ArrayAdapter<CharSequence> adapter; //adapter for spinner

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_an_exp);
        setTitle("Edit an Expense");
        Intent mIntent = getIntent();
        int position = mIntent.getIntExtra("position", 0);
        //EDIT NEEDED: need to display vendor, currency type, amount, and receipt img based on
        //the given position selected by user

        //set up spinner for category selection.
        //EDIT NEEDED: spinner should show the previously selected Category
        spinner = (Spinner) findViewById(R.id.spinner2);
        //category_names is defined in strings xml as an example to populate spinner adapter
        adapter = ArrayAdapter.createFromResource(this, R.array.category_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" selected", Toast.LENGTH_LONG).show();
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
                        //EDIT NEEDED: code for delete expense
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

        //EDIT NEEDED: this should pull from the selected Exp's Category based on the provided position
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

