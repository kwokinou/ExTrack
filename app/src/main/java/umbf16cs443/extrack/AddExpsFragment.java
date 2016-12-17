package umbf16cs443.extrack;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Event;
import umbf16cs443.extrack.db.models.Expense;

/**
 * Created by kwokin on 12/4/2016.
 */
public class AddExpsFragment extends ListFragment {

    int layout;
    Event curEvent;
    DBHelper db;
    ArrayList<Expense> newExpenses, allExpenses, unAddedExpenses;
    ArrayAdapter<Expense> expAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        curEvent = ((EditEventActivity) getActivity()).getEvent();
        allExpenses = ((EditEventActivity)getActivity()).getAllExpenses();
        newExpenses = ((EditEventActivity) getActivity()).getNewExpenses();

        unAddedExpenses = new ArrayList<>();

        //determine expenses that are not included in the new expense list and show to user
        for (Expense e : allExpenses)
                unAddedExpenses.add(e);

        for (int i = 0; i < newExpenses.size(); i++)
            for (int j = 0; j < unAddedExpenses.size(); j++)
                if(unAddedExpenses.get(j).getId() == newExpenses.get(i).getId())
                    unAddedExpenses.remove(j);

        layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        expAdapter = new ArrayAdapter<Expense>(getActivity(), layout, unAddedExpenses);
        setListAdapter(expAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        final Expense e = unAddedExpenses.get(position);
        final int pos = position;

        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getActivity());
        deleteDialog.setMessage("Add This Expense?");
        deleteDialog.setTitle("Add Expense");

        deleteDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //add selected expense to new expense list
                if (!((EditEventActivity) getActivity()).getNewExpenses().contains(e))
                    ((EditEventActivity) getActivity()).getNewExpenses().add(e);

                //remove from unadded expense list
                unAddedExpenses.remove(pos);

                EditEventActivity activity = (EditEventActivity) getActivity();
                //update expense count
                ((TextView) activity.findViewById(R.id.expCount)).setText(String.valueOf(activity.getNewExpenses().size()));

                //update new expense total amt
                Double amt = 0.0;
                for (int i = 0; i < activity.getNewExpenses().size(); i++)
                    amt += activity.getNewExpenses().get(i).getExAmount();

                //display event total dollar amount
                ((TextView) activity.findViewById(R.id.eventAmt)).setText(String.valueOf(amt));

                    updateView();
            }
        });
        deleteDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        deleteDialog.show();

    }

    //refresh unadded expense list after adding to new expense
    public void updateView(){
        expAdapter = new ArrayAdapter<Expense>(getActivity(), layout, unAddedExpenses);
        setListAdapter(expAdapter);
    }
}
