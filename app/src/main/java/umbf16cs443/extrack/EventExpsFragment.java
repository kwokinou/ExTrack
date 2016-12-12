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
    ArrayList<Expense> newExpenses;
    ArrayAdapter<Expense> expAdapter;
    DBHelper db;

    public void updateView(){

        //expenses = curEvent.getExpenses();
        expAdapter = new ArrayAdapter<Expense>(getActivity(), layout, ((EditEventActivity) getActivity()).getNewExpenses());
        setListAdapter(expAdapter);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        db = new DBHelper(getActivity());

      //  db = ((EditEventActivity) getActivity()).getDb();
        curEvent = ((EditEventActivity) getActivity()).getEvent();

        //expenses = ((EditEventActivity) getActivity()).getNewEventExpenses();

        //expenses = ((EditEventActivity) getActivity()).getEventExpenses();


        layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

       // expAdapter = new ArrayAdapter<Expense>(getActivity(), layout, curEvent.getExpenses());

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
                //int id = curEvent.getEventId();
                //curEvent.deleteExpense(e);
                //expenses.remove(i);

                //db.updateEvent(curEvent);
                //curEvent = db.fetchEvent(id);
                //ArrayList<Expense> temp = ((EditEventActivity)getActivity()).getUnAddedExpenses();
                //if (!temp.contains(e))
                  //  temp.add(e);
                //((EditEventActivity)getActivity()).getEventExpenses().remove(e);

                //expenses.remove(e);

                ((EditEventActivity) getActivity()).getNewExpenses().remove(e);

                //if (!((EditEventActivity) getActivity()).getUnAddedExpenses().contains(e))
                 //   ((EditEventActivity) getActivity()).getUnAddedExpenses().add(e);


                EditEventActivity activity = (EditEventActivity) getActivity();
                //prefill expense count
                ((TextView) activity.findViewById(R.id.expCount)).setText(String.valueOf(activity.getNewExpenses().size()));


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
