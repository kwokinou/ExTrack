package umbf16cs443.extrack;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Event;
import umbf16cs443.extrack.db.models.Expense;

/**
 * Created by kwokin on 11/24/2016.
 */

//activity to prompt user edit an existing event
public class EditEventActivity extends AppCompatActivity {
    int position;
    DBHelper db;
    ArrayList<Event> events;
    Event event;
    int layout;

    ArrayList<Expense> expenses;
    ArrayAdapter<Expense> exAdapter;
    ListFragment expenseListFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_an_event);
        setTitle("Edit an Event");

        Intent mIntent = getIntent();
        position = mIntent.getIntExtra("position", 0);

        db = new DBHelper(this);
        events = db.getAllEvents();
        event = events.get(position);

        ((EditText) findViewById(R.id.eventName)).setText(event.getEventName()); //prefill event name
        ((EditText) findViewById(R.id.limitAmt)).setText(String.valueOf(event.getLimit())); //prefill limit

        //display expense count for selected event
        if (event.getExpenses() == null)
            ((TextView) findViewById(R.id.expCount)).setText(String.valueOf(0));
        else
            ((TextView) findViewById(R.id.expCount)).setText(String.valueOf(event.getExpenses().size()));

        //display event total dollar amount
        ((TextView) findViewById(R.id.eventAmt)).setText(String.valueOf(event.getEventTotal()));


        //*******************populate expenses belong to event in ListView*********************

        expenseListFrag = new ListFragment();
        layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        expenses = event.getExpenses();

        //display a message if no expenses assigned
        if (expenses != null) {
            exAdapter = new ArrayAdapter<Expense>(this, layout, expenses);
            expenseListFrag.setListAdapter(exAdapter);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container2, expenseListFrag);
            transaction.commit();
        }

        else{
            HelpMsgFragment newFragment = new HelpMsgFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container2, newFragment);
            transaction.commit();
        }
        //*************************************************************************************
    }

    //include save and delete button in menu bar
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.edit_action_btns, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            //TODO user clicked on save to store updated Event object
            //return to activity showing all Events
            case R.id.save:
                finish();
                break;

            //user wants to deleted the selected Event object
            //prompt an alertdialog to confirm deletion
            case R.id.delete:
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
                deleteDialog.setMessage("Delete event permanently?");
                deleteDialog.setTitle("Delete Event");

                deleteDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteEvent(event);//delete Event from database
                        finish();
                    }
                });
                deleteDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                deleteDialog.show();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}