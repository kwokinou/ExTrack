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

import java.util.ArrayList;

import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Event;
import umbf16cs443.extrack.db.models.Expense;

/**
 * Created by kwokin on 12/4/2016.
 */
public class EventExpsFragment extends ListFragment {

    int layout;
    Event curEvent;
    ArrayAdapter<Expense> expAdapter;
    DBHelper db;

    //refresh expense list after removal
    public void updateView(){
        expAdapter = new ArrayAdapter<Expense>(getActivity(), layout, ((EditEventActivity) getActivity()).getNewExpenses());
        setListAdapter(expAdapter);
    }

    //load up new expense list
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        curEvent = ((EditEventActivity) getActivity()).getEvent();

        layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        expAdapter = new ArrayAdapter<Expense>(getActivity(), layout, ((EditEventActivity) getActivity()).getNewExpenses());
        setListAdapter(expAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        final Expense e = ((EditEventActivity) getActivity()).getNewExpenses().get(position);

        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getActivity());
        deleteDialog.setMessage("Remove This Expense?");
        deleteDialog.setTitle("Remove Expense");

        deleteDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //remove expense from new expense list
                ((EditEventActivity) getActivity()).getNewExpenses().remove(e);

                EditEventActivity activity = (EditEventActivity) getActivity();
                //update expense count
                ((TextView) activity.findViewById(R.id.expCount)).setText(String.valueOf(activity.getNewExpenses().size()));

                //update new expense list total amount
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
}
