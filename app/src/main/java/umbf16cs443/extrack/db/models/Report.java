package umbf16cs443.extrack.db.models;

import java.util.Date;
import java.util.Calendar;

public class Report {

    int id;

    String reportName;

    int startYear;
    int startMonth;
    int startDay;

    int endYear;
    int endMonth;
    int endDay;

    Date startDate;
    Date endDate;


    public Report(){


    }

    public Report(int id, String reportName, int startYear, int startMonth,
                  int startDay, int endYear, int endMonth, int endDay) {
        this.id = id;
        this.reportName = reportName;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.endYear = endYear;
        this.endMonth = endMonth;
        this.endDay = endDay;

        Calendar calendar = Calendar.getInstance();
        calendar.set(startYear, startMonth, startDay);
        this.startDate = calendar.getTime();

        calendar.set(endYear, endMonth, endDay);
        this.endDate = calendar.getTime();
    }

    public Report(String reportName, int startYear, int startMonth,
                  int startDay, int endYear, int endMonth, int endDay) {

        this.reportName = reportName;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.endYear = endYear;
        this.endMonth = endMonth;
        this.endDay = endDay;

        Calendar calendar = Calendar.getInstance();
        calendar.set(startYear, startMonth, startDay);
        this.startDate = calendar.getTime();

        calendar.set(endYear, endMonth, endDay);
        this.endDate = calendar.getTime();

    }

    public Report(String reportName, Date startDate, Date endDate) {

        this.reportName = reportName;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        this.startDate = startDate;

        this.startYear = calendar.get(Calendar.YEAR);
        this.startMonth = calendar.get(Calendar.MONTH);
        this.startDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(endDate);
        this.endDate = endDate;

        this.endYear = calendar.get(Calendar.YEAR);
        this.endMonth = calendar.get(Calendar.MONTH);
        this.endDay = calendar.get(Calendar.DAY_OF_MONTH);


    }

//todo date getters and setters


    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public int getEndYear() {
        return endYear;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public int getEndDay() {
        return endDay;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;


    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
