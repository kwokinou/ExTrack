package umbf16cs443.extrack;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by kwokin on 12/18/2016.
 */

public class MyCurrencyFormatter implements IValueFormatter {

    private DecimalFormat mFormat;

    public MyCurrencyFormatter() {
        mFormat = new DecimalFormat("###,###,###.##");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        // write your logic here
        return "$" + mFormat.format(value);
    }
}