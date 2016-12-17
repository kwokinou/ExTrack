package umbf16cs443.extrack.utility;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sethiyaji on 27-11-2016.
 */

public class StorageHelper {

    File rootDir = Environment.getExternalStorageDirectory();
    File imageDir = new File(rootDir.getAbsolutePath()+"/Extrack/receipts");
    File reportDir = new File(rootDir.getAbsolutePath()+"/Extrack/reports");
    private static final String TAG = "Extrack:StorageHelper:";
    public StorageHelper(){
        super();
    }

    public File getPictureDirectory(String eventName){
        if(!imageDir.exists()){
            imageDir.mkdirs();
        }
        File imageFile = new File(imageDir,getReceiptImageName(eventName));
        return imageFile;
    }

    private String getReceiptImageName(String prefix) {
        SimpleDateFormat suffix = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return prefix+"_"+suffix.format(new Date()).toString()+".jpg";
    }

    private String getReportName(String prefix) {
        SimpleDateFormat suffix = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return prefix+"_"+suffix.format(new Date()).toString()+".pdf";
    }

    public File getReportDirectory(String reportName){
        if(!reportDir.exists()){
            Log.v(TAG,"StorageHelper :: Report Directory Creating :: "+reportDir.mkdirs());

        }else{
            Log.v(TAG,"StorageHelper :: Report Directory already Created");
        }
        File reportFile = new File(reportDir,getReportName(reportName));
        Log.v(TAG,"Directory Path="+reportFile.getAbsolutePath());
        return reportFile;
    }
}
