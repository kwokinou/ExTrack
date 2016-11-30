package umbf16cs443.extrack.db;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by me on 11/17/16.
 */

public abstract class DateHelpers {

    public Date intsToDate(int year, int month, int day){

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        // converts calendar to date
        return calendar.getTime();

    }

    public long intsToDateStamp(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        //converts calendar to date object to date stamp
        return calendar.getTime().getTime();

    }

}
