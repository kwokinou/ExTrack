package umbf16cs443.extrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.view.View;
import android.view.ViewGroup;

import umbf16cs443.extrack.db.DBHelper; //db helper
import umbf16cs443.extrack.db.models.Category; //add category model

/**
 * Created by kwokin on 10/30/2016.
 */

//activity to prompt user enter new category information
public class AddCatActivity extends AppCompatActivity {

    EditText input;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_a_category);
        setTitle("Add a Category");
    }

    //include save button in menu bar
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.save_action_btn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        input = (EditText) this.findViewById(R.id.category);
        Category category = new Category(input.getText().toString());
        db = new DBHelper(this);
        db.addCategory(category);
        db.close();

        switch (item.getItemId()){

            //user clicked on save to store Category object
            //return to activity showing all Categories
            case R.id.save:

                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}