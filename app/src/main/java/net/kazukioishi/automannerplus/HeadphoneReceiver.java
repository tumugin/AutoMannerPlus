package net.kazukioishi.automannerplus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.util.Log;

public class HeadphoneReceiver extends BroadcastReceiver {
	public static final String RINGERMODEPREF = "RingerMode";
	
	public HeadphoneReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		//Log.i("AutoMannerPlus", "Event recived.");
		//If not enabled
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		boolean appEnabled = prefs.getBoolean("enableApp", false);
		if(appEnabled == false){
			Log.i("AutoMannerPlus", "App not enabled.");
			return;
		}
		//manner mode state
		AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		
		//If connected
		if(intent.getAction().equals("android.intent.action.HEADSET_PLUG")){
			if(intent.getExtras().getInt("state") != 1){
				return;
			}
			Log.i("AutoMannerPlus", "connect event.");
			int audioMode = manager.getRingerMode();
			Editor editor = prefs.edit();
			editor.putInt(RINGERMODEPREF, audioMode);
			editor.commit();
			//If manner mode(vibrate or silent)
			if(audioMode == AudioManager.RINGER_MODE_SILENT | audioMode == AudioManager.RINGER_MODE_VIBRATE){
				//set to normal
				manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				Log.i("AutoMannerPlus", "set to manner mode off");
			}
		}
		
		//If disconnected
		if(intent.getAction().equals("android.media.AUDIO_BECOMING_NOISY")){
			Log.i("AutoMannerPlus", "disconnect event.");
			//get manner mode state
			int audioMode = manager.getRingerMode();
			//If not manner or silent
			if(audioMode != AudioManager.RINGER_MODE_SILENT | audioMode != AudioManager.RINGER_MODE_VIBRATE){
				//get last state from pref
				int lastAudioMode = prefs.getInt(RINGERMODEPREF, -1);
				//if setting is nothing then exit
				if(lastAudioMode == -1){
					Log.i("AutoMannerPlus", "No setting found.");
					return;
				}
				//revert to last setting
				manager.setRingerMode(lastAudioMode);
				Log.i("AutoMannerPlus", "Audio mode revert OK.");
			}
			//restart service(”O‚Ì‚½‚ß)
			Intent headphoneservice = new Intent(context, HeadphoneReceiverService.class);
			context.startService(headphoneservice);
			Log.i("AutoMannerPlus", "Service wake up.");
		}
	}
}
