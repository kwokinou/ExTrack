package umbf16cs443.extrack;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Category;
import umbf16cs443.extrack.db.models.Expense;

/**
 * Created by kwokin on 12/13/2016.
 */


public class CategoryTotalsFrag extends Fragment {
    ListView lv;
    DBHelper db;
    ArrayList<Double> catList;
    ArrayAdapter<String> exAdapter;
    DecimalFormat df = new DecimalFormat("#.##");
    Collection c;
    String[] arr;

    Hashtable<Category, Double> catHash;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.category_totals, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        db = new DBHelper(getContext());

        catHash = db.getCategoryTotals();

        catList = new ArrayList<Double>(catHash.values());

        c = catHash.keySet();
        Iterator<Category> itr = c.iterator();
        arr = new String[c.size()];

        List<BarDataSet> catDataSets

        for (int i = 0; i < arr.length; i++) {

            String catName = itr.next().toString();
            arr[i] = catName + " --- $" + Double.valueOf(catList.get(i)) + " " +
                    "--- " + df.format(catList.get(i) / db.getGrandTotal() * 100) + "%";

            List<BarEntry> cattotalentry = new ArrayList<>();
            cattotalentry.add(new BarEntry(i, Double.valueOf(catList.get(i));
            BarDataSet cattotalset = new BarDataSet(cattotalentry,
                    catName);


        }

        lv = (ListView) (getActivity().findViewById(R.id.catTotalList));

        exAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arr);

        BarChart catTotalsChart = (BarChart) view.findViewById(R.id.cattotalchart);


        //for()


        BarData cattotaldata = new BarData(cattotalset);


        catTotalsChart.setData(cattotaldata);
        catTotalsChart.setFitBars(true);
        catTotalsChart.invalidate();

//        data.setBarWidth(0.9f); // set custom bar width
//        chart.setData(data);
//        chart.setFitBars(true); // make the x-axis fit exactly all bars
//        chart.invalidate(); // refresh


        lv.setAdapter(exAdapter);
    }
}