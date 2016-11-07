package skkk.gogogo.dakainote.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import skkk.gogogo.dakainote.Activity.NoteEditActivity.SaveNoteActivity;
import skkk.gogogo.dakainote.R;

public class CopyService extends Service {
    private NotificationManager mNotificationManager;
    private final int NOTIFICATION_ID=0x123;

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
        ClipboardManager cb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cb.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                Intent intent=new Intent(CopyService.this, SaveNoteActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(CopyService.this,
                        0,intent,0);
                Notification notify=new Notification.Builder(CopyService.this)
                        .setAutoCancel(true)
                        .setTicker("有新的消息")
                        .setSmallIcon(R.drawable.item_edit)
                        .setContentText("打开白开水笔记")
                        .setContentTitle("一条新的消息")
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .build();

                synchronized (Thread.currentThread()) {
                    try {
                        mNotificationManager.notify(NOTIFICATION_ID,notify);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
