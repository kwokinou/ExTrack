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

import java.util.ArrayList;

import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Event;


/**
 * Created by kwokin on 10/23/2016.
 */
public class ViewEventFragment extends ListFragment {

    //sample event list
    ArrayList<Event> events;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //show action bar buttons

        DBHelper db = new DBHelper(getContext());
        events = db.getAllEvents();

        //list layout for list view
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        //set up array adapter to display events
        setListAdapter(new ArrayAdapter<Event>(getActivity(), layout, events));

    }

    //action menu buttons
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.view_action_btns, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            //user clicked on add an event action button
            //switch to new activity to get user input for new event
            case R.id.add:
                Intent i = new Intent(getActivity(), AddEventActivity.class);
                startActivity(i);
                break;

            //user clicked on search an event action button
            //switch to new activity to get user input for search events
            case R.id.search:
                Intent j = new Intent(getActivity(), SearchEventActivity.class);
                startActivity(j);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
