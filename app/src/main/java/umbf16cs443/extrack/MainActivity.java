package umbf16cs443.extrack;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import umbf16cs443.extrack.Services.NotificationService;
import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Event;

public class MainActivity extends AppCompatActivity
        implements ViewExpFragment.OnExpSelectedListener,
        ViewEventFragment.OnEventSelectedListener{

    boolean expView = true;
    ViewExpFragment viewExpFrag;
    ViewEventFragment viewEventFrag;
    private static final String TAG="Extrac:MainActivity:";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DBHelper db = new DBHelper(this);
        //calling Notification Service
        Intent notificationServiceIntent = new Intent(this, NotificationService.class);
        Bundle notificationBundle = new Bundle();

        Event event = db.getCurrentActiveEvent();
        Log.v(TAG,"Id Current Active Event = "+event.getEventId());
        if(event!=null){
            notificationBundle.putInt("eventId",event.getEventId());
        }else{
            notificationBundle.putInt("eventId",0);
        }
        notificationServiceIntent.putExtras(notificationBundle);
        startService(notificationServiceIntent);
        Log.v(TAG,"Notification Service Called");

        viewExps(null);//Show Expenses listFragment by default
    }

    //user clicked on Expenses
    public void viewExps(View view){

        expView = true;

        //update the two main buttons colors
        Button btn1 = (Button) findViewById(R.id.expensebt);
        btn1.setTextColor(Color.BLACK);
        btn1.setBackgroundResource(R.drawable.pressedbuttonshape);

        Button btn2 = (Button) findViewById(R.id.eventbt);
        btn2.setTextColor(Color.WHITE);
        btn2.setBackgroundResource(R.drawable.buttonshape);

        //replace the framelayout with the ViewExpFragment to show all expenses
        viewExpFrag = new ViewExpFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, viewExpFrag);
        transaction.commit();

    }

    //user clicked on Events
    public void viewEvents(View view){

        expView = false;

        //update the two main buttons colors
        Button btn = (Button) findViewById(R.id.eventbt);
        btn.setTextColor(Color.BLACK);
        btn.setBackgroundResource(R.drawable.pressedbuttonshape);

        Button btn2 = (Button) findViewById(R.id.expensebt);
        btn2.setTextColor(Color.WHITE);
        btn2.setBackgroundResource(R.drawable.buttonshape);

        //replace the framelayout with the ViewEventFragment to show all categories
        viewEventFrag = new ViewEventFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, viewEventFrag);
        transaction.commit();
    }

    //user selects one of the existing Expenses for edit
    public void onExpSelected(int position){
        Intent i = new Intent(this, EditExpActivity.class);
        i.putExtra("position", position);//EditExpActivity needs position
        startActivity(i);
    }

    //user selects one of the existing Events for edit
    public void onEventSelected(int position){
        Intent i = new Intent(this, EditEventActivity.class);
        i.putExtra("position", position);//EditExpActivity needs position
        startActivity(i);
    }

    //Update Expense or Event ListView when returning from last activity
    @Override
    public void onResume(){
        super.onResume();
        if(expView==true){
            viewExpFrag.updateExpListView();
        }
        else if(expView == false) viewEventFrag.updateEventListView();
    }
}
