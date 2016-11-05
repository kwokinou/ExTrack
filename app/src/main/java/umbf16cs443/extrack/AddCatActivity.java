package umbf16cs443.extrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by kwokin on 10/30/2016.
 */

//activity to prompt user enter new category information
public class AddCatActivity extends AppCompatActivity {
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