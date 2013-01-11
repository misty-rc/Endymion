package org.misty.rc.Endymion;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

public class EndymionActivity extends Activity {
    private Context _context;
    private EventListener _listener;
    private GestureDetector _detector;
    private ViewFlipper _flipper;
    private SharedPreferences _pref;
    private Resources _res;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        _context = getApplicationContext();
        _flipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        _res = getResources();
        _pref = PreferenceManager.getDefaultSharedPreferences(_context);

        checkInit();

        _listener = new EventListener(_context, _flipper);
        _detector = new GestureDetector(_context, _listener);
    }

    private void checkInit() {
        if(! _pref.getBoolean(_res.getString(R.string.pref_initialized), false)) {
            setDefaultPreference();
        }
    }

    private void setDefaultPreference() {
        SharedPreferences.Editor editor = _pref.edit();
        editor.putBoolean(_res.getString(R.string.pref_page_ejection), true);
        editor.putBoolean(_res.getString(R.string.pref_initialized), true);

        editor.commit();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return _detector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }
}
