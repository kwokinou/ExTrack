package umbf16cs443.extrack;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import umbf16cs443.extrack.db.DBHelper;

/**
 * Created by kwokin on 12/12/2016.
 */
public class StatsHeadlineFragment extends ListFragment {

    String[] StatsHeadlines = {
            "Grand Total",
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

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        //getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setSelector(R.drawable.pressedbuttonshape);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        StaticsActivity stats = (StaticsActivity) getActivity();

        switch (position){
            case 0:
                Toast.makeText(getActivity().getApplicationContext(), "Grand Total is $" + stats.getDB().getGrandTotal(), Toast.LENGTH_LONG).show();
                break;

            case 1:
                CategoryTotalsFrag list = new CategoryTotalsFrag();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.stats_container, list);
                transaction.commit();
                break;

            default:
                Toast.makeText(getActivity().getApplicationContext(), "Invalid Entry", Toast.LENGTH_LONG).show();
        }
    }
}
