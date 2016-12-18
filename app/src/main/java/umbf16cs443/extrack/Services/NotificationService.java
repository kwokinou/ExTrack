package umbf16cs443.extrack.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import umbf16cs443.extrack.MainActivity;
import umbf16cs443.extrack.R;
import umbf16cs443.extrack.db.DBHelper;
import umbf16cs443.extrack.db.models.Event;


/**
 * Created by Sethiyaji on 27-11-2016.
 */

public class NotificationService extends IntentService {
    private static final String TAG="Extrack:Nf_Service:";
    private static final Long DELAY_TIME=1000*60*60L;
    public NotificationService() {
        super("Notification Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final int eventId = intent.getExtras().getInt("eventId");
        Log.v(TAG,"Extracted Event Id from Main Activity : "+eventId);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(DELAY_TIME);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    DBHelper db = new DBHelper(getApplicationContext());
                    Event event = db.fetchEvent(eventId);
                    long limit = 0;
                    double totalExpense = 0;
                    if(event!=null){
                        limit = event.getLimit();
                        totalExpense = event.getEventTotal();
                    }
                    if(limit>0) {
                        if (totalExpense > (limit * .8)) {
                            showNotification(limit, totalExpense, event.getEventId());
                        }
                    }
                }
            }
        });
        t.start();


    }



    private void showNotification(Long limit,Double totalExpense,Integer notificationID) {
        Log.v(TAG,"ShowNotification Method called with Limit = "+limit+",totalExpense = "+totalExpense);
        String msg = "You have spent "+totalExpense+" amount from your set limit ="+limit+".";
        String title = "Expense Reached : "+((totalExpense*100)/limit)+"%";
        NotificationCompat.Builder mBuilder;
        int nID=notificationID;
        mBuilder=new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_add_black_24dp)
                .setTicker("*"+msg+"*");

        NotificationManager mNotifyMgr=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(nID,mBuilder.build());
        Log.v(TAG,"Showing Notification for EventId = "+notificationID);
    }
}
