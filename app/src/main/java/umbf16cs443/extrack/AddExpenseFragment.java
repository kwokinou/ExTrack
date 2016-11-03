package umbf16cs443.extrack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import umbf16cs443.extrack.db.models.Expense;


public class AddExpenseFragment extends Fragment {

    Expense newExpense;

    //initial set up to prompt user to input info for new expense
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true); //to show "save" action button
        return inflater.inflate(R.layout.add_an_exp, container, false);
    }

    //set up "save" action button
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.save_action_btn, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    //need to implement onOptionsItemSelected to process "save".
    // An Expense object will be created and added to database.
}