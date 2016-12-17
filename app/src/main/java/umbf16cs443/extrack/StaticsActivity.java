package umbf16cs443.extrack;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Category;
import umbf16cs443.extrack.db.models.Expense;

/**
 * Created by kwokin on 12/12/2016.
 */
public class StaticsActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener{

    DBHelper db;
    boolean startD = true; //determine if start date or end date is selected

    TextView tv;
    ArrayList<Expense> resultExps;
    ArrayAdapter<Expense> expAdapter;
    double total = 0; //display total for exps in a time frame
    DecimalFormat df = new DecimalFormat("#.##");
    Date startDate;// = new GregorianCalendar(2016, Calendar.DECEMBER, 1).getTime();
    Date endDate;// = new GregorianCalendar(2016, Calendar.DECEMBER, 1).getTime();


    Category cat;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);
        setTitle("Statistics");

        db = new DBHelper(this);
        String grandTotalDisplay = String.format("%.2f", db.getGrandTotal());


        ((TextView) findViewById(R.id.grand_total)).setText("Grand Total: $ "
                + grandTotalDisplay);
        Calendar calendar = Calendar.getInstance();

        // Set start and end date to default
        startDate = calendar.getTime();
        endDate = calendar.getTime();
    }

    public DBHelper getDB(){return db;}

    public Date getStartDate(){return startDate;}

    public Date getEndDate() {return endDate;}

    public void setCategory(Category c){ cat = c;}



    public void findExpsByCat(View view){
        int total = 0;
        ArrayList<Expense> result;
        ArrayAdapter<Expense> adapter;


        ListView lv = (ListView) findViewById(R.id.resultList);

        //call to get exps in a time frame

        result = db.getExpensesByCategory(cat);

        //calculate total value of these exps
        for (Expense e : result)
            total += e.getExAmount();

        //display total value in UI
        ((TextView) findViewById(R.id.totalVal)).setText("$" + df.format(total));

        //feed exp list in UI
        adapter = new ArrayAdapter<Expense>(this, android.R.layout.simple_list_item_1, result);
        lv.setAdapter(adapter);

    }


//code for displaying expenses in a time frame*****************************************************
    //user clicks on start date
    public void pickStartDate(View view) {
        startD = true;
        DatePickerFragment picker = new DatePickerFragment();
        picker.show(getSupportFragmentManager(), "date");
    }

    //user clicks on end date
    public void pickEndDate(View view) {
        startD = false;
        DatePickerFragment picker = new DatePickerFragment();
        picker.show(getSupportFragmentManager(), "date");
    }

    //user wants to see result
    public void showExpsInTimeFrame(View view){
        ListView lv = (ListView) findViewById(R.id.expsListInTimeFrame);

        //call to get exps in a time frame
        resultExps = db.getExpensesByDate(startDate, endDate);

        //calculate total value of these exps
        for(Expense e: resultExps)
            total += e.getExAmount();


        String totalDisplay = String.format("$ %.2f", total);

        ((TextView) findViewById(R.id.totalByDates)).setText(totalDisplay);
        total = 0;

        //feed exp list in UI
        expAdapter = new ArrayAdapter<Expense>(this, android.R.layout.simple_list_item_1, resultExps);
        lv.setAdapter(expAdapter);
    }

    public void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.startDateText)).setText(dateFormat.format(calendar.getTime()));
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        if(startD){
            startDate = cal.getTime();
            tv = (TextView) findViewById(R.id.startDateText);
            SimpleDateFormat simpleDate = new SimpleDateFormat("MM/dd/yyyy");
            tv.setText(simpleDate.format(startDate));
        }

        else {
            endDate = cal.getTime();
            tv = (TextView) findViewById(R.id.endDateText);
            SimpleDateFormat simpleDate = new SimpleDateFormat("MM/dd/yyyy");
            tv.setText(simpleDate.format(endDate));
        }

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
//****************************End of DatePicker code*************************************************


}
