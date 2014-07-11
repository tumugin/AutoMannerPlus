package net.kazukioishi.automannerplus;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OnBootReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
        Notification notification = new Notification(R.drawable.ic_launcher, "AutoMannerPlus is running.",System.currentTimeMillis());
		Intent headphoneservice = new Intent(context, HeadphoneReceiverService.class);
		context.startService(headphoneservice);

		Log.i("AutoMannerPlus", "Service wake up.");
	}

}
