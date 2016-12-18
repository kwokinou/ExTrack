package umbf16cs443.extrack;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Currency;
import android.icu.util.ULocale;
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
import java.util.Date;

import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Expense;


/**
 * Created by kwokin on 10/23/2016.
 */
@android.support.annotation.RequiresApi(api = Build.VERSION_CODES.N)
public class ViewExpFragment extends ListFragment {
    OnExpSelectedListener mCallback;

    Date currentDate = new Date();
    DBHelper db;
    int layout;
    ArrayList<Expense> expenses;
    ArrayAdapter<Expense> exAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //show action bar buttons

        //set up list layout
        layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        db = new DBHelper(getContext());
        expenses = db.getAllExpenses();

        exAdapter = new ArrayAdapter<Expense>(getActivity(), layout, expenses);
        setListAdapter(exAdapter);
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
            //switch to new activity to get user input for new expense
            case R.id.add:
                Intent i = new Intent(getActivity(), AddExpActivity.class);
                startActivity(i);
                break;

            case R.id.stats:
                Intent j = new Intent(getActivity(), StatsActivity.class);
                startActivity(j);
                break;

            //user clicked on search an expense action button
            //alertdialog to get user input for search expenses
            case R.id.search:
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                final EditText edittext = new EditText(getActivity());

                alert.setTitle("Enter Vendor Name");
                alert.setView(edittext);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String YouEditTextValue = edittext.getText().toString();
                        int i;
                        if(YouEditTextValue.length() > 0){
                            for (i = 0; i < expenses.size(); i++){
                                String vendor = expenses.get(i).getExVendor();
                                if (YouEditTextValue.equalsIgnoreCase(vendor)){
                                    Intent intent = new Intent(getActivity(), EditExpActivity.class);
                                    intent.putExtra("position", i);//EditExpActivity needs position
                                    startActivity(intent);
                                    break;
                                }
                            }
                            if(i == expenses.size())
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
                Intent k = new Intent(getActivity(), SettingActivity.class);
                startActivity(k);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //update Expense ListView
    public void updateExpListView(){

        expenses = db.getAllExpenses();
        exAdapter = new ArrayAdapter<Expense>(getActivity(), layout, expenses);
        setListAdapter(exAdapter);
    }

    //*****************************Edit an Expense by selecting it in the ListView******************
    //enables mainactivity to update when user selects an Expense fromm Expense listView
    //when users selects an Expense, display new activity for user to edit that Expense's information
    public interface OnExpSelectedListener{
        public void onExpSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnExpSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnExpSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        mCallback.onExpSelected(position);
    }
//***************end of edit Expense**************************************************************

}
