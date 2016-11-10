package skkk.gogogo.dakainote.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.widget.RemoteViews;

import skkk.gogogo.dakainote.Activity.NoteEditActivity.SaveNoteActivity;
import skkk.gogogo.dakainote.R;

public class CopyService extends Service {
    private NotificationManager mNotificationManager;
    private final int NOTIFICATION_ID = 0x123;
    private Notification notify;


    public CopyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mNotificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent=new Intent(CopyService.this, SaveNoteActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(CopyService.this,
                0,intent,0);

        RemoteViews remoteViews=new RemoteViews(getPackageName(),R.layout.layout_notify);

        notify = new Notification.Builder(CopyService.this)
                .setContent(remoteViews)
                .setTicker("有新的消息")
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.item_edit)
                .setContentText("打开白开水笔记")
                .setContentTitle("笔记")
//                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.item_edit))
                .build();
        notify.flags=Notification.FLAG_NO_CLEAR;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        synchronized(Thread.currentThread()) {
            mNotificationManager.notify(NOTIFICATION_ID,notify);

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}
