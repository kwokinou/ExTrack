package umbf16cs443.extrack.db.models;

import java.util.Currency;
import java.util.Date;

public class Expense {

    private int id;
    private String exVendor;
    private String exCurrencyCode;
    private Double exAmount;
    private String exReceipt;
    private long exDateStamp; //replace this with date picker object

    // Constructors
    public Expense() {


    }

    public Expense(int id, String exVendor, String exCurrencyCode, Double
            exAmount, String exReceipt, long dateStamp) {

        this.id = id;
        this.exVendor = exVendor;
        this.exCurrencyCode = exCurrencyCode;
        this.exAmount = exAmount;
        this.exReceipt = exReceipt;
        this.exDateStamp = dateStamp;
    }


    public Expense(int id, String exVendor, String exCurrencyCode, Double
            exAmount, String exReceipt, Date date) {

        this.id = id;
        this.exVendor = exVendor;
        this.exCurrencyCode = exCurrencyCode;
        this.exAmount = exAmount;
        this.exReceipt = exReceipt;
        this.exDateStamp = date.getTime();
    }

    public Expense(int id, String exVendor, Currency currency, Double
            exAmount, String exReceipt, long dateStamp) {

        this.id = id;
        this.exVendor = exVendor;
        this.exCurrencyCode = currency.getCurrencyCode();
        this.exAmount = exAmount;
        this.exReceipt = exReceipt;
        this.exDateStamp = dateStamp;
    }

    public Expense(int id, String exVendor, Currency currency, Double
            exAmount, String exReceipt, Date date) {

        this.id = id;
        this.exVendor = exVendor;
        this.exCurrencyCode = currency.getCurrencyCode();
        this.exAmount = exAmount;
        this.exReceipt = exReceipt;
        this.exDateStamp = date.getTime();
    }


    // Helper Methods for converting Objects to primitives for database

    public void setDate(Date date){
        this.exDateStamp = date.getTime();
    }

    public void setCurrency(Currency currency){
        this.exCurrencyCode = currency.getCurrencyCode();

    }

    public Currency getCurrency(){
        Currency currency = null;
        currency = currency.getInstance(exCurrencyCode);
        return currency;
    }

    public Date getDate(){
        Date date = new Date(exDateStamp);
        return date;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExVendor() {
        return exVendor;
    }

    public void setExVendor(String exVendor) {
        this.exVendor = exVendor;
    }

    public String getExCurrencyCode() {
        return exCurrencyCode;
    }

    public void setExCurrencyCode(String exCurrencyCode) {
        this.exCurrencyCode = exCurrencyCode;
    }

    public Double getExAmount() {
        return exAmount;
    }

    public void setExAmount(Double exAmount) {
        this.exAmount = exAmount;
    }

    public String getExReceipt() {
        return exReceipt;
    }

    public void setExReceipt(String exReceipt) {
        this.exReceipt = exReceipt;
    }

    public long getExDateStamp() {
        return exDateStamp;
    }

    public void setExDateStamp(long exDateStamp) {
        this.exDateStamp = exDateStamp;
    }
}

