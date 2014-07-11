package net.kazukioishi.automannerplus;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class HeadphoneReceiverService extends Service {
	static HeadphoneReceiver receiver;
	final int ONGOING_NOTIFICATION = 1;
	public HeadphoneReceiverService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		//register reciver
		IntentFilter receiverFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
		receiver = new HeadphoneReceiver();
		registerReceiver(receiver, receiverFilter);
		Log.i("AutoMannerPlus", "reciver register OK.");
        //start Foreground
        Notification notification = new Notification(R.drawable.ic_launcher, "AutoMannerPlus now running...",System.currentTimeMillis());
        Intent notificationIntent = new Intent(this, SettingsScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, getText(R.string.app_name),"AutoMannerPlus now running...", pendingIntent);
        startForeground(ONGOING_NOTIFICATION, notification);
	}
	
	/*
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
        return Service.START_STICKY_COMPATIBILITY;
	}
	*/
	
	@Override
    public void onDestroy() {
		unregisterReceiver(receiver);
		Log.i("AutoMannerPlus", "service dead.");
	}
}
