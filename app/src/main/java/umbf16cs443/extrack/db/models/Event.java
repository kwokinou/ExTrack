package umbf16cs443.extrack.db.models;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;

public class Event {

    private int eventId;
    private String eventName;
    private ArrayList<Expense> expenses;
    private long limit;
    private Double eventTotal;
    private Date startDate;
    private Date endDate;

    // todo add expense total and expense total method

    public Event() {

    }

    // No ID Constructor - Date object

    public Event(String eventName, ArrayList<Expense> expenseList, long
            limit, Date startDate, Date endDate) {

        this.eventName = eventName;
        this.expenses = expenseList;
        this.limit = limit;
        this.startDate = startDate;
        this.endDate = endDate;

        this.calcTotal();


    }

    // No ID Constructor - Date long

    public Event(String eventName, ArrayList<Expense> expenseList, long
            limit, long startDate, long endDate) {

        this.eventName = eventName;
        this.expenses = expenseList;
        this.limit = limit;

        if (startDate != 0) {
            this.startDate = new Date(startDate);
        }
        if (endDate != 0) {
            this.endDate = new Date(endDate);
        }

        this.calcTotal();

    }
    // ID Constructor Date object

    public Event(int id, String eventName, ArrayList<Expense>
            expenseList, long limit, Date startDate, Date
                         endDate) {

        this.eventId = id;
        this.eventName = eventName;
        this.expenses = expenseList;
        this.limit = limit;
        this.startDate = startDate;
        this.endDate = endDate;

        this.calcTotal();

    }

    // ID Constructor Date long


    public Event(int id, String eventName, ArrayList<Expense> expenseList, long
            limit, long startDate, long endDate) {

        this.eventId = id;
        this.eventName = eventName;
        this.expenses = expenseList;
        this.limit = limit;

        if (startDate != 0) {
            this.startDate = new Date(startDate);
        }
        if (endDate != 0) {
            this.endDate = new Date(endDate);
        }

        this.calcTotal();

    }

    // This is the constructor for the DB, which will store event Total

    public Event(int id, String eventName, ArrayList<Expense> expenseList, long
            limit, Double eventTotal, long startDate, long endDate) {

        this.eventId = id;
        this.eventName = eventName;
        this.expenses = expenseList;
        this.limit = limit;
        this.eventTotal = eventTotal;

        if (startDate != 0) {
            this.startDate = new Date(startDate);
        }
        if (endDate != 0) {
            this.endDate = new Date(endDate);
        }

    }

    // **********************************************************************
    // Getters and or setters
    // **********************************************************************


    public int getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
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

    public Double getEventTotal() {
        return eventTotal;
    }

    // **********************************************************************
    // Expense List Methods
    // **********************************************************************


    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }


    // codes for toast

    // 0 - added successfully
    // 1 - expense is after end date
    // 2 - event is before start date
    // 3 - no date on expense

    public int addExpense(Expense expense) {
        if (expenses == null) {
            expenses = new ArrayList<Expense>();
        }
        if (this.endDate != null) {
            if (expense.getExDate() == null) {
                return 3;
            }
            if (expense.getExDate().after(this.endDate)) {
                return 1;
            }

        }
        if (this.startDate != null) {
            if (expense.getExDate() == null) {
                return 3;
            }
            if (expense.getExDate().before(this.startDate)) {
                return 2;
            }
        }

        expenses.add(expense);
        this.calcTotal();
        return 0;
    }

    // by index
    public void deleteExpense(int index) {
        if (expenses != null) {
            expenses.remove(index);

        }
        this.calcTotal();

    }


    // by comparision
    public void deleteExpense(Expense expense) {
        Expense tempExpense;

        if (expenses != null) {
            for (int i = 0; i < expenses.size(); i++) {
                tempExpense = expenses.get(i);
                if (tempExpense.equals(expense)) {
                    expenses.remove(i);
                    return;
                }

            }
        }
        this.calcTotal();


    }

    private void calcTotal() {
        this.eventTotal = 0.0;

        if (expenses != null) {
            if (expenses.size() != 0) {
                for (Expense e : this.expenses) {
                    this.eventTotal += e.getExAmount();
                }
            }
        }
    }

    // **********************************************************************
    // To String
    // **********************************************************************


    public String toString() {
        String ret;
        if(this.limit == 0) {
            ret = String.format("%s | total: $%.2f | limit: none", this
                    .eventName, this.eventTotal);
        }
        else{
            ret = String.format("%s | total: $%.2f | limit: $%d", this
                    .eventName, this.eventTotal, this.limit);
        }
        return ret;

    }

}