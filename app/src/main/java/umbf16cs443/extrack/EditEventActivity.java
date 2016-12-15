package umbf16cs443.extrack;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

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

    ArrayList<Expense> allExpenses;
    ArrayList<Expense> newExpenses; //new Expense list for event
    ArrayList<Expense> curExpenses;

//*******getters for fragment******************************************
    public Event getEvent(){
        return event;
    }

    public ArrayList<Expense> getAllExpenses(){
        return allExpenses;
    }

    public ArrayList<Expense> getNewExpenses() {return newExpenses;}

    public DBHelper getDb(){
        return db;
    }
//*********************************************************************

    //feed in fragment for user to add expenses
    public void addExpenses(View view){
        //update the two main buttons colors
        Button btn1 = (Button) findViewById(R.id.addExps);
        btn1.setTextColor(Color.BLACK);
        btn1.setBackgroundResource(R.drawable.pressedbuttonshape);

        Button btn2 = (Button) findViewById(R.id.curExps);
        btn2.setTextColor(Color.WHITE);
        btn2.setBackgroundResource(R.drawable.buttonshape);

        AddExpsFragment addExpsFragment = new AddExpsFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container2, addExpsFragment);
        transaction.commit();
    }

    //feed in fragment for event's current expense list
    //prompt user to delete existing expenses
    public void showCurExpenses(View view){
        //update the two main buttons colors
        Button btn1 = (Button) findViewById(R.id.curExps);
        btn1.setTextColor(Color.BLACK);
        btn1.setBackgroundResource(R.drawable.pressedbuttonshape);

        Button btn2 = (Button) findViewById(R.id.addExps);
        btn2.setTextColor(Color.WHITE);
        btn2.setBackgroundResource(R.drawable.buttonshape);

        if(newExpenses.size() > 0){
            EventExpsFragment eventExpsFragment = new EventExpsFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container2, eventExpsFragment);
            transaction.commit();
        }

        else{
            HelpMsgFragment newFragment = new HelpMsgFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container2, newFragment);
            transaction.commit();
        }
    }

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

        allExpenses = db.getAllExpenses();
        curExpenses = event.getExpenses();

        newExpenses = new ArrayList<>();

        //default newExpenses should include the current expense list
        for(Expense e : curExpenses)
            newExpenses.add(e);

        ((EditText) findViewById(R.id.eventName)).setText(event.getEventName()); //prefill event name
        ((EditText) findViewById(R.id.limitAmt)).setText(String.valueOf(event.getLimit())); //prefill limit

        //prefill expense count
        ((TextView) findViewById(R.id.expCount)).setText(String.valueOf(event.getExpenses().size()));

        //display event total dollar amount
        ((TextView) findViewById(R.id.eventAmt)).setText(String.valueOf(event.getEventTotal()));

        //display event's expenses by default
        showCurExpenses(null);
    }

    //include save and delete button in menu bar
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.edit_event_menu_btns, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            //return to activity showing all Events
            case R.id.saveEvent:

                //save the updated Event info
                event.setEventName(((EditText)findViewById(R.id.eventName)).getText().toString());
                event.setLimit(Long.valueOf(((EditText)findViewById(R.id.limitAmt)).getText().toString()));

                event.setExpenses(newExpenses);//update expense list

                db.updateEvent(event);

                finish();
                break;

            //TODO generate pdf
            case R.id.pdf:
                break;

            //user wants to deleted the selected Event object
            //prompt an alertdialog to confirm deletion
            case R.id.deleteEvent:
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