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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

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

    PieChart pieChart;
    List<PieEntry> entries;

    PieDataSet set;
    PieData data;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.category_totals, container, false);
        entries = new ArrayList<>();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        pieChart = (PieChart) getActivity().findViewById(R.id.piegraph);

        db = new DBHelper(getContext());

        //display all categories with their total values and % to the grand total
        catHash = db.getCategoryTotals();

        catList = new ArrayList<Double>(catHash.values());

        c = catHash.keySet();
        Iterator<Category> itr = c.iterator();
        arr = new String[c.size()];

        for (int i = 0; i < arr.length; i++) {
            Category c = itr.next();
            entries.add(new PieEntry((float) (catList.get(i) / db.getGrandTotal()), c.getCatName()));
            arr[i] = c + " --- $" + Double.valueOf(catList.get(i)) + " --- " + df.format(catList.get(i) / db.getGrandTotal() * 100) + "%";
        }

        lv = (ListView) (getActivity().findViewById(R.id.catTotalList));

        exAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arr);

        lv.setAdapter(exAdapter);

        //set up for PieChart***************************
        set = new PieDataSet(entries, "");
        set.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set.setValueTextSize(10f);
        data = new PieData(set);
        data.setValueFormatter(new MyPercentFormatter());
        pieChart.setNoDataText("Your Result Here");
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterText("Categories Anaylsis");
        pieChart.setDescription(null);
        pieChart.invalidate(); //display
        //***********************************************

    }
}