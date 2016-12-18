package umbf16cs443.extrack;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Category;
import umbf16cs443.extrack.db.models.Event;
import umbf16cs443.extrack.db.models.Expense;

import static android.R.attr.entries;

public class StatsActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    DBHelper db;
    boolean startD = true; //determine if start date or end date is selected
    TextView tv; //used to update selected date for user
    ArrayList<Expense> resultExps; //used for displaying exps in a timeframe
    ArrayAdapter<Expense> expAdapter; //used for displaying exps in a timeframe
    double total = 0; //display total for exps in a time frame
    DecimalFormat df = new DecimalFormat("#.##"); //two decimal places for dollar value
    Date startDate;
    Date endDate;

    Category cat; //used for displaying exps under a category
    Expense exp; //used for displaying events under an exp

    //*****variables for expensesByCategory piechart
    PieChart pieChart2;
    List<PieEntry> entries2;
    PieDataSet set2;
    PieData data2;
    //*********************************************

    //variables for eventsByExpense bargraph*********
    BarChart barChart;
    List<BarEntry> entries3;
    BarDataSet set3;
    BarData data3;
    //******************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        db = new DBHelper(this);

        setTitle("Grand Total: $" + String.format("%.2f", db.getGrandTotal()));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Calendar calendar = Calendar.getInstance();

        // Set start and end date to default
        startDate = calendar.getTime();
        endDate = calendar.getTime();


    }

//**********************activity getters and setters***************************************
    public DBHelper getDB(){return db;}

    public Date getStartDate(){return startDate;}

    public Date getEndDate() {return endDate;}

    public void setCategory(Category c){ cat = c;}

    public void setExpense(Expense e){exp = e;}

//******************************************************************************************

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position){
                case 0:
                    return new CategoryTotalsFrag();
                case 1:
                    return new ExpsInTimeFrameFrag();
                case 2:
                    return new ExpsByCatFrag();
                case 3:
                    return new EvtsByExpFrag();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Catetory Totals";
                case 1:
                    return "Exps by Date";
                case 2:
                    return "Exps by Cat";
                case 3:
                    return "Evts by Exp";
            }
            return null;
        }
    }

//********************display events under the same expense***************************************
    public void findEvtsByExp(View view){
        double total = 0;
        ArrayList<Event> result;
        ArrayAdapter<Event> adapter;

        ListView lv = (ListView) findViewById(R.id.resultList2);

        //call to get exps in a time frame
        result = db.getEventsByExpense(exp);

        barChart = (BarChart) findViewById(R.id.bargraph);
        entries3 = new ArrayList<>();

        for (int i = 0; i < result.size(); i++)
            entries3.add(new BarEntry((float) i, (float) result.get(i).getEventTotal().doubleValue()));

        String[] eventNames = new String[result.size()];
        for (int i =0; i < eventNames.length; i++)
            eventNames[i] = result.get(i).getEventName();

        set3 = new BarDataSet(entries3, "");

        set3.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data3 = new BarData(set3);
        data3.setValueFormatter(new MyCurrencyFormatter());
        set3.setValueTextSize(9f);
        barChart.setData(data3);
        barChart.setFitBars(true);
        barChart.setDrawValueAboveBar(true);
        barChart.setDescription(null);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.getLegend().setEnabled(false);
        barChart.getXAxis().setValueFormatter(new EventLabelFormatter(eventNames));

        barChart.invalidate();

        ((TextView) findViewById(R.id.totalVal2)).setText("");

        //feed exp list in UI
        adapter = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_1, result);
        lv.setAdapter(adapter);
    }
//***********************************************************************************************

//***************display expenses under the same category*****************************************
    public void findExpsByCat(View view){
        double total = 0;
        ArrayList<Expense> result;
        ArrayAdapter<Expense> adapter;

        ListView lv = (ListView) findViewById(R.id.resultList);

        //call to get exps in a time frame
        result = db.getExpensesByCategory(cat);

        //initiate piegraph elements
        pieChart2 = (PieChart) findViewById(R.id.piegraph2);
        entries2 = new ArrayList<>();

        //calculate total value of these exps
        for (Expense e : result) {
            total += e.getExAmount();
        }

        for (Expense e: result)
            entries2.add(new PieEntry((float) ((e.getExAmount().doubleValue())/total), e.getExVendor()));

        //display total value in UI
        ((TextView) findViewById(R.id.totalVal)).setText("$" + df.format(total));

        //feed exp list in UI
        adapter = new ArrayAdapter<Expense>(this, android.R.layout.simple_list_item_1, result);
        lv.setAdapter(adapter);

        //display piegraph
        set2 = new PieDataSet(entries2, "");
        set2.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set2.setValueTextSize(9f);
        data2 = new PieData(set2);
        data2.setValueFormatter(new MyPercentFormatter());
        pieChart2.setNoDataText("Your Result Here");
        pieChart2.setData(data2);
        pieChart2.setUsePercentValues(true);
        pieChart2.setCenterText("Expenses Anaylsis");
        pieChart2.setDescription(null);
        pieChart2.invalidate(); //display

    }
//************************************************************************************************

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

    //datepicker code******************************************************************************
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
