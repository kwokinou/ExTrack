package umbf16cs443.extrack;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by kwokin on 11/24/2016.
 */

//activity to prompt user edit an existing event
public class EditEventActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_an_event);
        setTitle("Edit an Event");
    }

    //include save and delete button in menu bar
    public boolean onCreateOptionsMenu(Menu menu){
        Intent mIntent = getIntent();
        int position = mIntent.getIntExtra("position", 0);
        //EDIT NEEDED:  should prefill EditText field with existing data based on selected Event position
        getMenuInflater().inflate(R.menu.edit_action_btns, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            //user clicked on save to store updated Event object
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
                        //EDIT NEEDED: code for delete expense in database
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