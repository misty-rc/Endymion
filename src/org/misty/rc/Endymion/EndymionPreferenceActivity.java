package org.misty.rc.Endymion;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/11
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class EndymionPreferenceActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.prefs_header, target);
    }

    public static class GeneralPrefFragment extends ExtendedPreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences_general);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }
    }

    public static class ViewerPrefFragment extends ExtendedPreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences_viewer);

            // set summary
            //String val = getPrefValue(R.string.pref_page_ejection);
            setSummary(R.string.pref_page_ejection);

            //Preference pref = findPreference(getString(R.string.pref_page_ejection));
            //String key = getString(R.string.pref_page_ejection);
            //String val = pref.getSharedPreferences().getString(key, null);
            //pref.setSummary(val);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals(getString(R.string.pref_page_ejection))) {
                Preference pref = findPreference(key);
                pref.setSummary(sharedPreferences.getString(key, "NULL"));
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }
    }
}
