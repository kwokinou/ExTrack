package umbf16cs443.extrack;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
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
public class CategoryTotalsFrag extends ListFragment {

    DBHelper db;
    ArrayList<Double> catList;
    ArrayAdapter<String> exAdapter;

    Collection c;


    String[] arr;

    Hashtable<Category, Double> catHash;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //show action bar buttons

        //set up list layout
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;




        db = new DBHelper(getContext());
        //catList = db.getAllCategories();

        catHash = db.getCategoryTotals();

        catList = new ArrayList<Double>(catHash.values());

        c = catHash.keySet();
        Iterator<Category> itr = c.iterator();
        arr = new String[c.size()];

        for (int i = 0; i < arr.length; i++)
            arr[i] = itr.next() + " --- " + Double.valueOf(catList.get(i));

        exAdapter = new ArrayAdapter<String>(getActivity(), layout, arr);
        setListAdapter(exAdapter);
    }
}
