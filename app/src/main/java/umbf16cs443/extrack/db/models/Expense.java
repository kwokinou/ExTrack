package umbf16cs443.extrack.db.models;

import java.util.Currency;
import java.util.Date;
//project specific imports
import umbf16cs443.extrack.db.models.Category;

public class Expense {

    // DB Safe Variables
    private int id;
    private String exVendor;
    private String exCurrencyCode;
    private Double exAmount;
    private String exReceipt;

    private long exDateStamp;

    // Object variables for usefulness

    private Date exDate;

    // Category array for mapping

    private Category exCategory;


    // Constructors
    public Expense() {

    }

    public Expense(int id, String exVendor, Currency currency, Double
            exAmount, String exReceipt, long exDateStamp,
                   Category category) {
        this.id = id;
        this.exVendor = exVendor;

        if(currency != null) {
            this.exCurrencyCode = currency.getCurrencyCode();
        }

        this.exAmount = exAmount;
        this.exReceipt = exReceipt;

        this.exDateStamp = exDateStamp;


        this.exDate = new Date(exDateStamp);
        this.exCategory = category;


    }

    public Expense(String exVendor, Currency currency, Double
            exAmount, String exReceipt, long exDateStamp,
                   Category category) {

        this.exVendor = exVendor;
        if(currency != null) {
            this.exCurrencyCode = currency.getCurrencyCode();
        }
        this.exAmount = exAmount;
        this.exReceipt = exReceipt;

        this.exDateStamp = exDateStamp;


        this.exDate = new Date(exDateStamp);
        this.exCategory = category;


    }




    public Expense(String exVendor, Currency currency, Double
            exAmount, String exReceipt, Date date,
                   Category category) {


        this.exVendor = exVendor;

        if(currency != null) {
            this.exCurrencyCode = currency.getCurrencyCode();
        }
        this.exAmount = exAmount;
        this.exReceipt = exReceipt;

        this.exDate = date;

        this.exDateStamp = exDate.getTime();
        this.exCategory = category;


    }

    public Expense(int id, String exVendor, Currency currency, Double
            exAmount, String exReceipt, Date date,
                   Category category) {

        this.id = id;
        this.exVendor = exVendor;

        if(currency != null) {
            this.exCurrencyCode = currency.getCurrencyCode();
        }

        this.exAmount = exAmount;
        this.exReceipt = exReceipt;

        this.exDate = date;

        this.exDateStamp = exDate.getTime();
        this.exCategory = category;


    }

    public Expense(int id, String exVendor, String currency, Double
            exAmount, String exReceipt, long exDateStamp,
                   Category category) {
        this.id = id;
        this.exVendor = exVendor;
        this.exCurrencyCode = currency;
        this.exAmount = exAmount;
        this.exReceipt = exReceipt;
        this.exDateStamp = exDateStamp;

        this.exCategory = category;


    }

    public Expense(int id, String exVendor, String currency, Double
            exAmount, String exReceipt, Date date,
                   Category category) {

        this.id = id;
        this.exVendor = exVendor;
        this.exCurrencyCode = currency;
        this.exAmount = exAmount;
        this.exReceipt = exReceipt;
        this.exDate = date;

        if(date != null){
            this.exDateStamp = exDate.getTime();
        }

        this.exCategory = category;

    }


    // Helper Methods for converting Objects to primitives for database


    public void clearCategory() {
        this.exCategory = null;

    }

    public Category getCategory(){
        return this.exCategory;
    }

    public void setCategory(Category category){
        this.exCategory = category;
    }


    public void setExDate(Date date) {

        this.exDate = date;
        this.exDateStamp = exDate.getTime();

    }

    public void setExDate(long dateStamp) {
        this.exDateStamp = dateStamp;
        this.exDate = new Date(dateStamp);

    }


    public void setCurrency(Currency currency) {
        this.exCurrencyCode = currency.getCurrencyCode();

    }

    public Currency getCurrency() {
        Currency currency = null;
        currency = currency.getInstance(exCurrencyCode);
        return currency;
    }

    public void setCurrencyCode(String currency) {
        this.exCurrencyCode = currency;

    }

    public String getCurrencyCode() {

        return this.exCurrencyCode;
    }


    public Date getExDate() {
        return exDate;
    }

    public long getExDateStamp() {
        return exDateStamp;

    }

    public Category getExCategory() {
        return exCategory;
    }

    public void setExCategory(Category exCategory) {
        this.exCategory = exCategory;
    }

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

    public void removeExReceipt() {
        this.exReceipt = null;
    }

    public String toString(){

        if(!(exCategory == null)){
            return "$" + exAmount.toString() + "  -  " + this.exVendor + " - " +
                    exCategory.toString();
        }

        return "$" + exAmount.toString() + "  -  " + this.exVendor + " - " +
                "none";

    }

}




