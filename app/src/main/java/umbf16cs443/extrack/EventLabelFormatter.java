package umbf16cs443.extrack;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by kwokin on 12/18/2016.
 */

public class EventLabelFormatter implements IAxisValueFormatter{
    private final String[] mLabels;

    public EventLabelFormatter(String[] labels) {
        mLabels = labels;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mLabels[(int) value];
    }
}