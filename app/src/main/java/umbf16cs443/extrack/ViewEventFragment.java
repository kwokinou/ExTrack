package umbf16cs443.extrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * Created by kwokin on 11/24/2016.
 */
public class ViewEventFragment extends ListFragment {

    OnEventSelectedListener mCallback;

    //sample event list
    String[] Events = {
            "Event1",
            "Event2",
            "Event3",
            "Event4"
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //show action bar buttons

        //list layout for list view
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        //set up array adapter to display events
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Events));
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

            case R.id.setting:
                Intent k = new Intent(getActivity(), SettingActivity.class);
                startActivity(k);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

//*****************************Edit an Event by selecting it in the ListView*********************
    //enables mainactivity to update when user selects an Event fromm Event listView
    //when users selects an Event, display new activity for user to edit that Event's information
    public interface OnEventSelectedListener{
        public void onEventSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnEventSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnEventSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        mCallback.onEventSelected(position);
    }
//***************end of edit Event**************************************************************
}
