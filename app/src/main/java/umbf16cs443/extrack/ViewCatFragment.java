package umbf16cs443.extrack;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;


/**
 * Created by kwokin on 10/23/2016.
 */
public class ViewCatFragment extends ListFragment {

    //sample category list
    String[] Categories = {
            "Cat1",
            "Cat2",
            "Cat3",
            "Cat4"
    };


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //show action bar buttons

        //list layout for list view
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

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
