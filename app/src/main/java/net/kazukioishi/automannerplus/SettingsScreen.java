package net.kazukioishi.automannerplus;

import java.util.List;

import android.app.ActivityManager;
import android.app.ApplicationErrorReport.RunningServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.PreferenceActivity;


public class SettingsScreen extends PreferenceActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        Intent intent = new Intent(this, HeadphoneReceiverService.class);
        startService(intent);
    }
}
