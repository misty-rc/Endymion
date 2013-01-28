package org.misty.rc.Endymion.fragment;

import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/16
 * Time: 11:14
 * To change this template use File | Settings | File Templates.
 */
public class ExtendedPreferenceFragment extends PreferenceFragment {

    protected void setSummary(int key) {
        Preference _pref = findPreference(getString(key));
        String _prefkey = getString(key);
        String _val = _pref.getSharedPreferences().getString(_prefkey, null);
        _pref.setSummary(_val);
    }
}
