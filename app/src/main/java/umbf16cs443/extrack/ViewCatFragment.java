package umbf16cs443.extrack;

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
    String[] Categories;

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
            case R.id.add:
                //update framelayout with addCatFrag
                AddCategoryFragment addCatFrag = new AddCategoryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, addCatFrag);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            //user clicked on search an expense action button
            case R.id.search:
                //update framelayout with searchCatFrag
                SearchCategoryFragment searchExpFrg = new SearchCategoryFragment();
                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                transaction1.replace(R.id.fragment_container, searchExpFrg);
                transaction1.addToBackStack(null);
                transaction1.commit();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
