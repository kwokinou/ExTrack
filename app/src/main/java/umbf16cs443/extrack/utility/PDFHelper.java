package umbf16cs443.extrack.utility;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Category;
import umbf16cs443.extrack.db.models.Event;
import umbf16cs443.extrack.db.models.Expense;


/**
 * Created by Sethiyaji on 06-11-2016.
 */

public class PDFHelper {

    private static final String TAG = "Extrack:PdfHelper:";
    private static final LineSeparator UNDERLINE = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -2);
    private static final Paragraph newLine = new Paragraph("\n");
    Chunk glue = new Chunk(new VerticalPositionMark());

    File pdfFile;
    JSONObject json;
    StorageHelper storageHelper = new StorageHelper();
    //File reportDirectory = storageHelper.getReportDirectory("REPORT");
    public PDFHelper(){

    }
    private boolean status = false;

    public boolean generateReport(Event event, DBHelper db){
        pdfFile = storageHelper.getReportDirectory(event.getEventName());
        OutputStream outputStream = null;
        Document document = new Document(PageSize.LETTER);
        PdfWriter writer = null;
        try{
            Double totalAmount = 0.0;
            outputStream = new FileOutputStream(pdfFile);
            writer = PdfWriter.getInstance(document,outputStream);
            document.open();
            Paragraph reportTitle = new Paragraph(event.getEventName()+"("+event.getStartDate()+" - "+event.getEndDate()+")");
            reportTitle.setIndentationLeft(180.0f);
            reportTitle.setFont(new Font(Font.FontFamily.TIMES_ROMAN,15,Font.BOLD));

            document.add(reportTitle);
            document.add(newLine);
            document.add(UNDERLINE);

            List<Category> categoryList = db.getAllCategories();
            for(Category category:categoryList) {
                double totalCategoryAmount = 0.0;
                int count = 0;
                Paragraph cat = new Paragraph((count + 1) + ". " + category.getCatName());
                cat.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12));
                document.add(cat);
                document.add(newLine);
                List<Expense> expenseList = extractCommonExpenses(db.getExpenseByCategory(category.getId()),db.getExpensesByEvent(event));
                if(expenseList.size()>0){
                    for(Expense expense:expenseList) {
                        Paragraph exp = new Paragraph(expense.getExVendor());
                        exp.add(new Chunk(glue));
                        exp.add(expense.getCurrency().getCurrencyCode()+""+expense.getExAmount());
                        document.add(exp);
                        document.add(newLine);
                        String receiptPath = expense.getExReceipt();
                        if(receiptPath!=null) {
                            Image receipt = Image.getInstance(receiptPath);
                            receipt.setScaleToFitHeight(true);
                            receipt.setWidthPercentage(20f);
                            receipt.setScaleToFitLineWhenOverflow(true);
                            document.add(receipt);
                        }
                        totalCategoryAmount+=expense.getExAmount();
                    }
                }
                document.add(UNDERLINE);
                Paragraph total_str = new Paragraph("Total");
                total_str.add("$"+totalCategoryAmount);
                document.add(total_str);
                document.add(UNDERLINE);
                totalAmount+=totalCategoryAmount; // have to add Row for this THE FINAL ROW IN REPORT
            }

            document.add(newLine);
            document.add(newLine);
            document.add(UNDERLINE);
            Paragraph total_str = new Paragraph("Grand Total");
            total_str.add("$"+totalAmount);
            document.add(total_str);
            document.add(UNDERLINE);


            for(int i=0;i<5;i++)
                document.add(newLine);

            Paragraph developerFooter = new Paragraph("Extrack : Designed Developed and Maintained by Kwokin Ou,Jeffery,Akash Sethiya");
            developerFooter.setFont(new Font(Font.FontFamily.TIMES_ROMAN,15,Font.ITALIC));
            document.add(developerFooter);

            document.close();
            status = true;


        }catch(FileNotFoundException e){
            status = false;
            Log.e(TAG,"File Not Found ::"+e);
            e.printStackTrace();
        }catch(IOException e){
            status = false;
            Log.e(TAG,"IOException ::"+e);
        }catch(DocumentException e){
            status = false;
            Log.e(TAG,"DocumentException ::"+e);
        }
        return status;
    }

    private List<Expense> extractCommonExpenses(List<Expense> expenseByCategory, ArrayList<Expense> expensesByEvent) {
        Set<Expense> expenses = new HashSet<>();
        for(Expense e:expenseByCategory)
            expenses.add(e);
        for (Expense e:expensesByEvent)
            expenses.add(e);
        return new ArrayList<Expense>(expenses);

    }






/*    public boolean generateReport(String dataStr){
        OutputStream outputStream = null;
        Rectangle pagesize = new Rectangle(216f, 720f);
        //Document document = new Document(pagesize, 36f, 72f, 108f, 180f);
        Document document = new Document(PageSize.LETTER);
        PdfWriter writer = null;
        try{
            this.json = new JSONObject(dataStr);
            outputStream = new FileOutputStream(pdfFile);
            writer = PdfWriter.getInstance(document,outputStream);
            document.open();
            Paragraph reportTitle = new Paragraph(json.getString("event_name")+" ("+json.getString("event_startDate")+")");
            reportTitle.setIndentationLeft(180.0f);
            reportTitle.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 15,Font.BOLD));

            document.add(reportTitle);
            document.add(newLine);
            document.add(UNDERLINE);

            JSONArray expenses = json.getJSONArray("expenses");
            for(int i=0;i<expenses.length();i++){
                JSONObject obj = expenses.getJSONObject(i);
                Paragraph expense = new Paragraph((i+1)+". "+obj.getString("name"));
                expense.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12));
                document.add(expense);
                document.add(newLine);

                JSONArray fares = obj.getJSONArray("fares");
                double total_amount = 0.0;
                for(int j=0;j<fares.length();j++){
                    JSONObject json_fare = fares.getJSONObject(j);
                    Paragraph fare = new Paragraph(json_fare.getString("title"));
                    fare.add(new Chunk(glue));
                    fare.add(json.getString("currency")+""+json_fare.getString("amount"));
                    total_amount+=Double.parseDouble(json_fare.getString("amount"));
                    document.add(fare);
                    document.add(newLine);
                    *//*Image receipt = Image.getInstance("data\\cs443\\pdf\\temp.png");
                    receipt.setIndentationLeft(200f);
                    document.add(receipt);*//*
                }
                document.add(UNDERLINE);
                Paragraph total_str = new Paragraph("Total");
                total_str.add(new Chunk(glue));
                total_str.add(json.getString("currency")+""+total_amount);
                document.add(total_str);
                document.add(UNDERLINE);
            }
            document.close();
            status = true;
        }catch(FileNotFoundException e){
            status = false;
            System.out.println("StorageHelper :: PDFReport: File Not Found::"+e);
            e.printStackTrace();
        }catch(IOException e){
            status = false;
            System.out.println("StorageHelper :: PDFReport: IO::"+e);
        }catch(DocumentException e){
            status = false;
            System.out.println("StorageHelper :: PDFReport: DOCUMENTEXCEPTION::"+e);
        }catch(JSONException e){
            status = false;
            System.out.println("StorageHelper :: PDFReport: JSON Exception::"+e);
        }
        return status;
    }*/
}
