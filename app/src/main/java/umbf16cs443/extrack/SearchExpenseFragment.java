package umbf16cs443.extrack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by kwokin on 10/23/2016.
 */
public class SearchExpenseFragment extends Fragment {

    // fragment to prompt user to enter info for search
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_activity, container, false);
    }
}
