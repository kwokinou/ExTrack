package umbf16cs443.extrack.db.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Set;

import umbf16cs443.extrack.db.models.Category;

public class Expense {

    // DB Safe Variables
    private int id;
    private String exVendor;
    private String exCurrencyCode;
    private Double exAmount;
    private String exReceipt;
    private int exYear;
    private int exMonth;
    private int exDay;

    // Object variables for usefulness

    private Date exDate;

    // Category array for mapping

    private Set<Category> exCategories;


    // Constructors
    public Expense() {

    }

    public Expense(String exVendor, Currency currency, Double
            exAmount, String exReceipt, int exYear, int exMonth, int exDay,
                   Category[] categories) {

        this.exVendor = exVendor;
        this.exCurrencyCode = currency.getCurrencyCode();
        this.exAmount = exAmount;
        this.exReceipt = exReceipt;
        this.exYear = exYear;
        this.exMonth = exMonth;
        this.exDay = exDay;

        //needs a date conversion method
        Calendar calendar = Calendar.getInstance();
        calendar.set(exYear, exMonth, exDay);
        exDate = calendar.getTime();

        if (categories != null) {
            for (int i = 0; i < categories.length; i++) {
                this.exCategories.add(categories[i]);
            }
        }

    }


    public Expense(int id, String exVendor, Currency currency, Double
            exAmount, String exReceipt, int exYear, int exMonth, int exDay,
                   Category[] categories) {

        this.id = id;
        this.exVendor = exVendor;
        this.exCurrencyCode = currency.getCurrencyCode();
        this.exAmount = exAmount;
        this.exReceipt = exReceipt;
        this.exYear = exYear;
        this.exMonth = exMonth;
        this.exDay = exDay;

        //needs a date conversion method
        Calendar calendar = Calendar.getInstance();
        calendar.set(exYear, exMonth, exDay);
        exDate = calendar.getTime();

        if (categories != null) {
            for (int i = 0; i < categories.length; i++) {
                this.exCategories.add(categories[i]);
            }
        }

    }

    public Expense(int id, String exVendor, String currency, Double
            exAmount, String exReceipt, int exYear, int exMonth, int exDay,
                   Category[] categories) {

        this.id = id;
        this.exVendor = exVendor;
        this.exCurrencyCode = currency;
        this.exAmount = exAmount;
        this.exReceipt = exReceipt;
        this.exYear = exYear;
        this.exMonth = exMonth;
        this.exDay = exDay;


        Calendar calendar = Calendar.getInstance();
        calendar.set(exYear, exMonth, exDay);
        exDate = calendar.getTime();

        if (categories != null) {
            for (int i = 0; i < categories.length; i++) {
                this.exCategories.add(categories[i]);
            }
        }

    }


    public Expense(int id, String exVendor, Currency currency, Double
            exAmount, String exReceipt, Date date, Category[] categories) {

        this.id = id;
        this.exVendor = exVendor;
        this.exCurrencyCode = currency.getCurrencyCode();
        this.exAmount = exAmount;
        this.exReceipt = exReceipt;

        this.exDate = date;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        this.exYear = calendar.get(Calendar.YEAR);
        this.exMonth = calendar.get(Calendar.MONTH);
        this.exDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (categories != null) {
            for (int i = 0; i < categories.length; i++) {
                this.exCategories.add(categories[i]);
            }
        }
    }

    public Expense(int id, String exVendor, String currency, Double
            exAmount, String exReceipt, Date date, Category[] categories) {

        this.id = id;
        this.exVendor = exVendor;
        this.exCurrencyCode = currency;
        this.exAmount = exAmount;
        this.exReceipt = exReceipt;

        this.exDate = date;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        this.exYear = calendar.get(Calendar.YEAR);
        this.exMonth = calendar.get(Calendar.MONTH);
        this.exDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (categories != null) {
            for (int i = 0; i < categories.length; i++) {
                this.exCategories.add(categories[i]);
            }
        }
    }

    //needs a date based constructor

    // Helper Methods for converting Objects to primitives for database

    public void addCategory(Category category) {
        this.exCategories.add(category);
    }

    public void removeCategory(Category category) {
        this.exCategories.remove(category);

    }

    public void clearCategories() {
        this.exCategories.clear();

    }

    public void setDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        this.exYear = calendar.get(Calendar.YEAR);
        this.exMonth = calendar.get(Calendar.MONTH);
        this.exDay = calendar.get(Calendar.DAY_OF_MONTH);

        this.exDate = date;


    }

    public void setDate(int exYear, int exMonth, int exDay) {
        this.exYear = exYear;
        this.exMonth = exMonth;
        this.exDay = exDay;

        Calendar calendar = Calendar.getInstance();
        calendar.set(exYear, exMonth, exDay);
        this.exDate = calendar.getTime();


    }


    public void setCurrency(Currency currency) {
        this.exCurrencyCode = currency.getCurrencyCode();

    }

    public Currency getCurrency() {
        Currency currency = null;
        currency = currency.getInstance(exCurrencyCode);
        return currency;
    }

    public int getExYear() {
        return exYear;
    }

    public void setExYear(int exYear) {
        this.exYear = exYear;
    }

    public int getExMonth() {
        return exMonth;
    }

    public void setExMonth(int exMonth) {
        this.exMonth = exMonth;
    }

    public int getExDay() {
        return exDay;
    }

    public void setExDay(int exDay) {
        this.exDay = exDay;
    }

    public Date getExDate() {
        return exDate;
    }

    public void setExDate(Date exDate) {
        this.exDate = exDate;
    }

    public Set<Category> getExCategories() {
        return exCategories;
    }

    public void setExCategories(Set<Category> exCategories) {
        this.exCategories = exCategories;
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

    public void removeExReceipt() {
        this.exReceipt = null;
    }


}




