package umbf16cs443.extrack;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Event;
import umbf16cs443.extrack.db.models.Expense;


/**
 * Created by kwokin on 10/23/2016.
 */
public class ViewEventFragment extends ListFragment {

    OnEventSelectedListener mCallback;

    DBHelper db;
    int layout;
    ArrayAdapter<Event> evAdapter;

    //sample event list
    ArrayList<Event> events;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //show action bar buttons

        db = new DBHelper(getContext());
        events = db.getAllEvents();

        //list layout for list view
        layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        evAdapter = new ArrayAdapter<Event>(getActivity(), layout, events);
        setListAdapter(evAdapter);
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
            //alertdialog to get user input for search events
            case R.id.search:
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                final EditText edittext = new EditText(getActivity());

                alert.setTitle("Enter Event Name");
                alert.setView(edittext);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String YouEditTextValue = edittext.getText().toString();
                        int i;
                        if(YouEditTextValue.length() > 0){
                            for (i = 0; i < events.size(); i++){
                                String eventName = events.get(i).getEventName();
                                if (YouEditTextValue.equalsIgnoreCase(eventName)){
                                    Intent intent = new Intent(getActivity(), EditEventActivity.class);
                                    intent.putExtra("position", i);//EditEventActivity needs position
                                    startActivity(intent);
                                    break;
                                }
                            }
                            if(i == events.size())
                                Toast.makeText(getActivity().getBaseContext(), "No Matching Value Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.show();
                break;

            case R.id.setting:
                Intent j = new Intent(getActivity(), SettingActivity.class);
                startActivity(j);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //refresh event list view
    public void updateEventListView(){
        events = db.getAllEvents();
        evAdapter = new ArrayAdapter<Event>(getActivity(), layout, events);
        setListAdapter(evAdapter);
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
