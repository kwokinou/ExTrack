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

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

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

        for (int i = 0; i < arr.length; i++)
            arr[i] = itr.next() + " --- $" + Double.valueOf(catList.get(i)) + " --- " + df.format(catList.get(i)/db.getGrandTotal()*100) + "%";

        lv = (ListView) (getActivity().findViewById(R.id.catTotalList));

        exAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arr);

        lv.setAdapter(exAdapter);
    }
}