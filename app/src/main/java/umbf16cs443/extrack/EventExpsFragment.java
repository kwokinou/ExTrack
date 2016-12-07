package umbf16cs443.extrack;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    ArrayList<Expense> expenses;
    ArrayAdapter<Expense> expAdapter;
    DBHelper db;

    public void updateView(){

        expenses = curEvent.getExpenses();
        expAdapter = new ArrayAdapter<Expense>(getActivity(), layout, expenses);
        setListAdapter(expAdapter);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        db = ((EditEventActivity) getActivity()).getDb();
        curEvent = ((EditEventActivity) getActivity()).getEvent();
        //expenses = curEvent.getExpenses();
        //expenses = ((EditEventActivity) getActivity()).getExpenses();

        expenses = curEvent.getExpenses();

        layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        expAdapter = new ArrayAdapter<Expense>(getActivity(), layout, expenses);
        setListAdapter(expAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        final Expense e = expenses.get(position);
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getActivity());
        deleteDialog.setMessage("Remove This Expense?");
        deleteDialog.setTitle("Remove Expense");

        deleteDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                curEvent.deleteExpense(e);
                updateView();
                db.updateEvent(curEvent);
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
