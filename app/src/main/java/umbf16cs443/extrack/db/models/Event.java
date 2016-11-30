package umbf16cs443.extrack.db.models;

import java.util.ArrayList;
import java.util.Date;

public class Event {

    private int eventId;
    private String eventName;
    private ArrayList<Expense> expenses;
    private long limit;
    private Date startDate;
    private Date endDate;

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

    }
    // ID Constructor Date object

    public Event(int id, String eventName, ArrayList<Expense>
            expenseList, long limit, Date startDate, Date endDate) {

        this.eventId = id;
        this.eventName = eventName;
        this.expenses = expenseList;
        this.limit = limit;
        this.startDate = startDate;
        this.endDate = endDate;
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

    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
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

    // **********************************************************************
    // Expense List Methods
    // **********************************************************************


    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    public void addExpense(Expense expense){
        if(expenses == null){
            expenses = new ArrayList<Expense>();
        }

        expenses.add(expense);
    }

    // by index
    public void deleteExpense(int index){
        if(expenses != null){
            expenses.remove(index);

        }

    }

    // by comparision
    public void deleteExpense(Expense expense){
        Expense tempExpense;

        if(expenses != null) {
            for (int i = 0; i < expenses.size(); i++) {
                tempExpense = expenses.get(i);
                if(tempExpense.equals(expense)){
                    expenses.remove(i);
                    return;
                }

            }
        }

    }

    // **********************************************************************
    // To String
    // **********************************************************************


    public String toString(){
        if(this.limit > 0) {
            return this.eventName + ": " + "start: " + "end: " + "limit:" + this
                    .limit;
        }
        return this.eventName + ": " + "start: " + "end: " + "limit: none";

    }


}
