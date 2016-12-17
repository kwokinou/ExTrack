package umbf16cs443.extrack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import umbf16cs443.extrack.db.models.Category;

/**
 * Created by kwokin on 12/17/2016.
 */
public class ExpsByCatFrag extends Fragment {

    Spinner mSpinner;
    ArrayAdapter<Category> adapter;
    ArrayList<Category> catArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.stats_spinner_listfrag, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ((TextView)(getActivity()).findViewById(R.id.tv1)).setText("Select Category");

        mSpinner = (Spinner) getActivity().findViewById(R.id.statSpinner);

        catArray = ((StaticsActivity) getActivity()).getDB().getAllCategories();

        adapter = new ArrayAdapter<Category>(getActivity(), android.R.layout
                .simple_spinner_item,
                catArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((StaticsActivity)getActivity()).setCategory((Category)parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


}
