package umbf16cs443.extrack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kwokin on 12/17/2016.
 */
public class EvtsByExpFrag extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.stats_spinner_listfrag, container, false);

        return rootView;
    }


}
