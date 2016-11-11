package umbf16cs443.extrack;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.List;

import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Category;

/**
 * Created by kwokin on 10/23/2016.
 */
public class ViewCatFragment extends ListFragment {

    //sample category list
    String[] Categories = {};


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //show action bar buttons

        //list layout for list view
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        //generate category list with db helper
        //TODO maybe this should be an arrau of categories not strings
        DBHelper db = new DBHelper(getContext());
        Categories = new String[db.getCategoriesCount()];
        List<Category> cats = db.getAllCategories();

        for(int i = 0; i < cats.size(); i++){
            Categories[i] = cats.get(i).getCatName();
        }





        //set up arry adapter to display categories
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Categories));
    }

    //action menu buttons
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.view_action_btns, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            //user clicked on add an expense action button
            //switch to new activity to get user input for new category
            case R.id.add:
                Intent i = new Intent(getActivity(), AddCatActivity.class);
                startActivity(i);
                break;

            //user clicked on search an expense action button
            //switch to new activity to get user input for search categories
            case R.id.search:
                Intent j = new Intent(getActivity(), SearchCatActivity.class);
                startActivity(j);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
