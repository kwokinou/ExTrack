package umbf16cs443.extrack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import umbf16cs443.extrack.db.DBHelper; //db helper
import umbf16cs443.extrack.db.models.Category; //add category model


import umbf16cs443.extrack.R;

public class AddCategoryFragment extends Fragment {

    EditText input;
    DBHelper db;

    //initial set up to prompt user to input info for new category
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true); //to show "save" action button
        return inflater.inflate(R.layout.add_a_category, container, false);
    }

    //set up "save" action button
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.save_action_btn, menu);

        super.onCreateOptionsMenu(menu,inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
       input = (EditText) getView().findViewById(R.id.category);
       Category category = new Category(input.getText().toString());
       db = new DBHelper(getContext());
       db.addCategory(category);
       return true;
    }
//    //need to implement onOptionsItemSelected to process "save".
    // An Expense object will be created and added to database.
}