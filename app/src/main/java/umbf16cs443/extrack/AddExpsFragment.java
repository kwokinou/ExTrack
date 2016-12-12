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
    ArrayList<Expense> newExpenses, allExpenses;
    ArrayList<Expense> eventExpenses, unAddedExpenses;
    //ArrayList<Expense> unAddedExpenses = new ArrayList<>();
    ArrayAdapter<Expense> expAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // db = ((EditEventActivity) getActivity()).getDb();
        curEvent = ((EditEventActivity) getActivity()).getEvent();
        allExpenses = ((EditEventActivity)getActivity()).getAllExpenses();
        //expenses = curEvent.getExpenses();
        newExpenses = ((EditEventActivity) getActivity()).getNewExpenses();

  //      eventExpenses = curEvent.getExpenses();

        unAddedExpenses = new ArrayList<>();

        //if (eventExpenses.size()==0)
          //  unAddedExpenses = expenses;

        //for(int i = 0; i < expenses.size(); i++)
          //  unAddedExpenses.add(i, expenses.get(i));

        //else{
            Iterator<Expense> itr = allExpenses.iterator();
           // //Iterator<Expense> itr2 = curExpenses.iterator();

            while(itr.hasNext()){
                Expense e = itr.next();
                if(!newExpenses.contains(e) && !unAddedExpenses.contains(e))
                    unAddedExpenses.add(e);
            }


       // newExpenses = curEvent.getNewExpenses();
       // unAddedExpenses = curEvent.getUnAddedExpenses();

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
                //if(eventExpenses == null){
                  //  int id = curEvent.getEventId();
             //       curEvent.addExpense(e);
               //     db.updateEvent(curEvent);
                 //   curEvent = db.fetchEvent(id);
                    //expenses.add(e);
                    //unAddedExpenses.remove(e);

                //((EditEventActivity) getActivity()).getUnAddedExpenses().remove(e);

                //newExpenses.add(e);
                if (!((EditEventActivity) getActivity()).getNewExpenses().contains(e))
                    ((EditEventActivity) getActivity()).getNewExpenses().add(e);



                EditEventActivity activity = (EditEventActivity) getActivity();
                //prefill expense count
                ((TextView) activity.findViewById(R.id.expCount)).setText(String.valueOf(activity.getNewExpenses().size()));


                Double amt = 0.0;
                for (int i = 0; i < activity.getNewExpenses().size(); i++)
                    amt += activity.getNewExpenses().get(i).getExAmount();

                //display event total dollar amount
                ((TextView) activity.findViewById(R.id.eventAmt)).setText(String.valueOf(amt));




                    updateView();
                //}

                //else Toast.makeText(getActivity().getApplicationContext(), "Expense already in event", Toast.LENGTH_SHORT).show();
            }
        });
        deleteDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        deleteDialog.show();

    }

    public void updateView(){
        unAddedExpenses.clear();
        Iterator<Expense> itr = allExpenses.iterator();


        while(itr.hasNext()){
            Expense e = itr.next();
            if(!newExpenses.contains(e) && !unAddedExpenses.contains(e))
                unAddedExpenses.add(e);
        }

        expAdapter = new ArrayAdapter<Expense>(getActivity(), layout, unAddedExpenses);
        setListAdapter(expAdapter);
    }
}
