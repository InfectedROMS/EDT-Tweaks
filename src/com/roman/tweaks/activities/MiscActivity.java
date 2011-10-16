
package com.roman.tweaks.activities;

import com.roman.tweaks.R;
import com.roman.tweaks.ShellInterface;


import android.content.Context;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.util.Log;

public class MiscActivity extends PreferenceActivity implements OnPreferenceChangeListener {
    String pref;

    Context mContext;

    
    private static final String PREF_CAPACITIVE_BACKLIGHT = "pref_capacitive_backlight";

    
    ListPreference mCapacitiveBacklight;
    
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getApplicationContext();
        addPreferencesFromResource(R.xml.misc_prefs);
        PreferenceScreen prefs = getPreferenceScreen();

   
        mCapacitiveBacklight = (ListPreference) prefs.findPreference(PREF_CAPACITIVE_BACKLIGHT);  
        mCapacitiveBacklight.setOnPreferenceChangeListener(this);          

    }    

    

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mCapacitiveBacklight) {
            int valCapacitiveBacklight = Integer.valueOf((String) newValue);

            Settings.System.putInt(getContentResolver(), "tweaks_capacitive_backlight",
            		valCapacitiveBacklight);
            
            if (ShellInterface.isSuAvailable()) {
				ShellInterface.runCommand("echo '"+valCapacitiveBacklight+"' > /sys/devices/platform/leds-pm8058/leds/button-backlight/currents");
			}     
            
            
            return true;
        } 

        return false;
    }
}
