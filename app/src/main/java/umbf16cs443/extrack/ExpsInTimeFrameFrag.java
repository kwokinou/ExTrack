package umbf16cs443.extrack;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import umbf16cs443.extrack.db.models.Expense;

/**
 * Created by kwokin on 12/15/2016.
 */
public class ExpsInTimeFrameFrag extends Fragment {

    Date start;
    Date end;
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.exps_in_a_time_frame, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //dafault start and end date to totay's date
        tv = (TextView) getActivity().findViewById(R.id.startDateText);

        start = ((StaticsActivity)getActivity()).getStartDate();
        SimpleDateFormat simpleDate = new SimpleDateFormat("MM/dd/yyyy");
        tv.setText(simpleDate.format(start));

        tv = (TextView) getActivity().findViewById(R.id.endDateText);

        end = ((StaticsActivity)getActivity()).getEndDate();
        SimpleDateFormat simpleDate2 = new SimpleDateFormat("MM/dd/yyyy");
        tv.setText(simpleDate2.format(end));

    }



}
