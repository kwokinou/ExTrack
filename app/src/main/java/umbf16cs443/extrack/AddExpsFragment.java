package umbf16cs443.extrack;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    ArrayList<Expense> expenses;
    ArrayList<Expense> curExpenses;
    ArrayList<Expense> unAddedExpenses = new ArrayList<Expense>();
    ArrayAdapter<Expense> expAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        curEvent = ((EditEventActivity) getActivity()).getEvent();
        //expenses = curEvent.getExpenses();
        expenses = ((EditEventActivity) getActivity()).getExpenses();

        curExpenses = curEvent.getExpenses();

        if (curExpenses == null)
            unAddedExpenses = expenses;

        else{
            Iterator<Expense> itr = expenses.iterator();
            //Iterator<Expense> itr2 = curExpenses.iterator();

            while(itr.hasNext()){
                Expense e = itr.next();
                if(!curExpenses.contains(e))
                    unAddedExpenses.add(e);
            }
        }



        layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        expAdapter = new ArrayAdapter<Expense>(getActivity(), layout, unAddedExpenses);
        setListAdapter(expAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        final Expense e = unAddedExpenses.get(position);

        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getActivity());
        deleteDialog.setMessage("Add This Expense?");
        deleteDialog.setTitle("Add Expense");

        deleteDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(curExpenses == null || !curExpenses.contains(e)){
                    curEvent.addExpense(e);

                }

                else Toast.makeText(getActivity().getApplicationContext(), "Expense already in event", Toast.LENGTH_SHORT).show();
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
