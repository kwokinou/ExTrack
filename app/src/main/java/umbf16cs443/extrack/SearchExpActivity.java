package umbf16cs443.extrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by kwokin on 10/30/2016.
 */

//activity for user to enter new expense information
public class SearchExpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        setTitle("Search Expenses");
    }

}