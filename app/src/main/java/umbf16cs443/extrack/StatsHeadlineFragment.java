package umbf16cs443.extrack;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import umbf16cs443.extrack.db.DBHelper;

/**
 * Created by kwokin on 12/12/2016.
 */
public class StatsHeadlineFragment extends ListFragment {

    String [] StatsHeadlines = {
            "All Category Totals",
            "Expenses in a Time Frame",
            "Events that Share an Expense"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, StatsHeadlines));
    }

    @Override
    public void onStart() {
        super.onStart();
        //indicate the selected choice
        getListView().setSelector(R.drawable.pressedbuttonshape);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        StaticsActivity stats = (StaticsActivity) getActivity();

        switch (position){
          //  case 0:
            //    Toast.makeText(getActivity().getApplicationContext(), "Grand Total is $" + stats.getDB().getGrandTotal(), Toast.LENGTH_LONG).show();
            case 0:
                CategoryTotalsFrag list = new CategoryTotalsFrag();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.stats_container, list);
                transaction.commit();
                break;

            case 1:
                ExpsInTimeFrameFrag expsInTimeFrameFrag = new ExpsInTimeFrameFrag();

                //CategoryTotalsFrag list2 = new CategoryTotalsFrag();
                FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.stats_container, expsInTimeFrameFrag );
                transaction2.commit();
                break;


            default:
                Toast.makeText(getActivity().getApplicationContext(), "Invalid Entry", Toast.LENGTH_SHORT).show();
        }
    }
}
