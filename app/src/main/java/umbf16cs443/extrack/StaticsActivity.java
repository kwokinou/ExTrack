package umbf16cs443.extrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import umbf16cs443.extrack.db.DBHelper;

/**
 * Created by kwokin on 12/12/2016.
 */
public class StaticsActivity extends AppCompatActivity {

    DBHelper db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);
        setTitle("Statistics");

        db = new DBHelper(this);
    }

    public DBHelper getDB(){return db;}
}
