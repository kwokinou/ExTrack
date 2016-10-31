package umbf16cs443.extrack;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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

        Button btn2 = (Button) findViewById(R.id.categorybt);
        btn2.setTextColor(Color.WHITE);
        btn2.setBackgroundResource(R.drawable.buttonshape);

        //replace the framelayout with the ViewExpFragment to show all expenses
        ViewExpFragment viewExpFrag = new ViewExpFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, viewExpFrag);
        transaction.commit();

    }

    //user clicked on Categories
    public void viewCats(View view){

        //update the two main buttons colors
        Button btn = (Button) findViewById(R.id.categorybt);
        btn.setTextColor(Color.BLACK);
        btn.setBackgroundResource(R.drawable.pressedbuttonshape);

        Button btn2 = (Button) findViewById(R.id.expensebt);
        btn2.setTextColor(Color.WHITE);
        btn2.setBackgroundResource(R.drawable.buttonshape);

        //replace the framelayout with the ViewCatFragment to show all categories
        ViewCatFragment viewExpFrag = new ViewCatFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, viewExpFrag);
        transaction.commit();
    }
}
