package umbf16cs443.extrack.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import umbf16cs443.extrack.MainActivity;
import umbf16cs443.extrack.R;
import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Event;
import umbf16cs443.extrack.utility.PDFHelper;


/**
 * Created by Sethiyaji on 06-11-2016.
 */

public class PdfService extends IntentService {

    public PdfService(){
        super("PdfGeneration Service");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        showNotification("Pdf Generation Begins","null");
        Toast.makeText(this,"Pdf Generation Begins", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Integer eventId = intent.getExtras().getInt("eventId");
        DBHelper db = new DBHelper(getApplicationContext());
        Event event = db.fetchEvent(eventId);
        PDFHelper report = new PDFHelper();
        boolean status = report.generateReport(event,db);
        if(status){
            showNotification("PDF Generated",null);
        }
    }


       private void showNotification(String title,String content) {
        NotificationCompat.Builder mBuilder;
        int nID=1;
        mBuilder=new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_add_black_24dp)
                .setTicker("*"+content+"*");

        Intent myIntent=new Intent(this, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0, myIntent, 0);
        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotifyMgr=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(nID,mBuilder.build());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        System.out.println("StorageHelper :: **************SERVICES DESTROYED*****************");
    }
}
