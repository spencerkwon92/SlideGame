package univ.major.project.assign.project_1.ui;

import android.content.Context;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

import java.util.ArrayList;

import univ.major.project.assign.project_1.R;

public class preference extends PreferenceActivity {

    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        PreferenceScreen ps = getPreferenceManager().createPreferenceScreen(this);
        SwitchPreference music = new SwitchPreference(this);
        music.setTitle(R.string.mu_title);
        music.setSummaryOn(R.string.mu_on);
        music.setSummaryOff(R.string.mu_off);
        music.setDefaultValue(true);
        music.setKey("MUSIC_PREF");

        SwitchPreference effectS = new SwitchPreference(this);
        effectS.setTitle(R.string.effect_title);
        effectS.setSummaryOn(R.string.effect_on);
        effectS.setSummaryOff(R.string.effect_off);
        effectS.setDefaultValue(true);
        effectS.setKey("SOUND_EFFECT");

        ListPreference tokSpeed = new ListPreference(this);
        tokSpeed.setTitle(R.string.ts_title);
        tokSpeed.setSummary(R.string.ts_sum);
        tokSpeed.setKey("TOKSPEED_PREF");
        tokSpeed.setEntries(R.array.ts_rates);
        String[] values = {"50", "100", "150"};
        tokSpeed.setEntryValues(values);
        tokSpeed.setDefaultValue("100");

        ListPreference theme = new ListPreference(this);
        theme.setTitle(R.string.theme_title);
        theme.setSummary(R.string.theme_Summary);
        theme.setKey("THEME_PREF");
        theme.setEntries(R.array.theme_entries);
        String[] thm_values = {"100", "200", "300"};
        theme.setEntryValues(thm_values);
        tokSpeed.setDefaultValue("100");

        ps.addPreference(music);
        ps.addPreference(effectS);
        ps.addPreference(tokSpeed);
        ps.addPreference(theme);

        setPreferenceScreen(ps);
    }

    public static boolean soundOn(Context c){
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("MUSIC_PREF", true);
    }

    public static boolean effectOn(Context c){
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("SOUND_EFFECT", true);
    }

    public static int getSpeed(Context c){
        return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(c).getString("TOKSPEED_PREF", "100"));

    }
    public static int theme(Context c){
        return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(c).getString("THEME_PREF", "100"));

    }

}
