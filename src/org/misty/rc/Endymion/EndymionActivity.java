package org.misty.rc.Endymion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

public class EndymionActivity extends Activity {
    private Context _context;
    private GestureListener _listener;
    private GestureDetector _detector;
    private ViewFlipper _flipper;
    private SharedPreferences _pref;
    private Resources _res;
    private MediaManager _manager;

    //private static Uri _fileUri;
    //private static Drive _service;
    //private GoogleAccountCredential _credential;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        _context = getApplicationContext();
        _flipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        _res = getResources();
        _pref = PreferenceManager.getDefaultSharedPreferences(_context);

        checkInit();

        _listener = new GestureListener(_context, _flipper);
        _detector = new GestureDetector(_context, _listener);

        //drive test
        //_credential = GoogleAccountCredential.usingOAuth2(this, DriveScopes.DRIVE);

        // test
        _manager = new MediaManager(_context);
        _manager.initTest();
        Log.d("Endymion", "ExternalFileDir-null: " + getExternalFilesDir(null));
        Log.d("Endymion", "ExternalFileDir-mydata: " + getExternalFilesDir("mydata"));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_preference:
                Intent intent = new Intent(_context, EndymionPreferenceActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
