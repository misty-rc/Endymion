package org.misty.rc.Endymion.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.misty.rc.Endymion.R;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/28
 * Time: 10:31
 * To change this template use File | Settings | File Templates.
 */
public class SmartViewFragment extends Fragment {
    private SharedPreferences _pref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.smart_view_frag, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
