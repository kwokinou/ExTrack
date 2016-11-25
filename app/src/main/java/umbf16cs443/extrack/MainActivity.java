package umbf16cs443.extrack;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity
        implements ViewExpFragment.OnExpSelectedListener,
                   ViewEventFragment.OnEventSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initially fill Framelayout with a simple helper message
        HelpMsgFragment newFragment = new HelpMsgFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.commit();
    }

    //user clicked on Expenses
    public void viewExps(View view){

        //update the two main buttons colors
        Button btn1 = (Button) findViewById(R.id.expensebt);
        btn1.setTextColor(Color.BLACK);
        btn1.setBackgroundResource(R.drawable.pressedbuttonshape);

        Button btn2 = (Button) findViewById(R.id.eventbt);
        btn2.setTextColor(Color.WHITE);
        btn2.setBackgroundResource(R.drawable.buttonshape);

        //replace the framelayout with the ViewExpFragment to show all expenses
        ViewExpFragment viewExpFrag = new ViewExpFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, viewExpFrag);
        transaction.commit();

    }

    //user clicked on Events
    public void viewEvents(View view){

        //update the two main buttons colors
        Button btn = (Button) findViewById(R.id.eventbt);
        btn.setTextColor(Color.BLACK);
        btn.setBackgroundResource(R.drawable.pressedbuttonshape);

        Button btn2 = (Button) findViewById(R.id.expensebt);
        btn2.setTextColor(Color.WHITE);
        btn2.setBackgroundResource(R.drawable.buttonshape);

        //replace the framelayout with the ViewEventFragment to show all categories
        ViewEventFragment viewEventFrag = new ViewEventFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, viewEventFrag);
        transaction.commit();
    }

    //user selects one of the existing Expenses for edit
    public void onExpSelected(int position){
        Intent i = new Intent(this, EditExpActivity.class);
        i.putExtra("position", position);
        startActivity(i);
    }

    //user selects one of the existing Events for edit
    public void onEventSelected(int position){
        Intent i = new Intent(this, EditEventActivity.class);
        i.putExtra("position", position);
        startActivity(i);
    }

}
