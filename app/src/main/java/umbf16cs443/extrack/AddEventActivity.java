package umbf16cs443.extrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Event;


//activity to prompt user enter new event information
public class AddEventActivity extends AppCompatActivity {

    EditText nameInput; //event name
    EditText limitInput; //event cost limit
    DBHelper db; //reference to database

    long limit; //event cost limit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_an_event);
        setTitle("Add an Event");

        db = new DBHelper(getApplicationContext());
        nameInput = (EditText) findViewById(R.id.event);
        limitInput = (EditText) findViewById(R.id.cost_limit);

    }

    //include save button in menu bar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_action_btn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            //user clicked on save to store new Event object
            //return to activity showing all Events
            case R.id.save:

                if(nameInput.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),R.string
                            .blank_event_error , Toast.LENGTH_SHORT ).show();
                    return false;
                }

                if(limitInput.getText().toString().equals("")) {
                    limit = 0;
                }

                else{
                    limit = Long.parseLong(limitInput.getText().toString());
                }

                // some null values for now
                Event event = new Event(
                        nameInput.getText().toString(), //name
                        null,                           //expense list
                        limit,                          //limit
                        0,                              //start date
                        0);                             // end date

                db.addEvent(event);

                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}