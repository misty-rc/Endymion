package org.misty.rc.Endymion;

import android.app.ActionBar;
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
import org.misty.rc.Endymion.fragment.FolderViewFragment;
import org.misty.rc.Endymion.fragment.SmartViewFragment;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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

    private static final int PREFERENCE_ACTIVITY = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        _context = getApplicationContext();
        //_res = getResources();

        _pref = PreferenceManager.getDefaultSharedPreferences(_context);
        checkInit(false);

        // move to mediaviewer
        //_flipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        //_listener = new GestureListener(_context, _flipper);
        //_detector = new GestureDetector(_context, _listener);

        //drive test
        //_credential = GoogleAccountCredential.usingOAuth2(this, DriveScopes.DRIVE);

        // tab test
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab()
                .setText("Folders")
                .setTabListener(new TopFragmentTabListener<FolderViewFragment>(
                        this, "folder", FolderViewFragment.class
                ))
        );

        actionBar.addTab(actionBar.newTab()
                .setText("Smart")
                .setTabListener(new TopFragmentTabListener<SmartViewFragment>(
                        this, "smart", SmartViewFragment.class
                ))
        );
    }

    private void checkInit(boolean init) {
        if(init) {
            setDefaultPreference();
        }
        if(! _pref.getBoolean(getString(R.string.pref_initialized), false)) {
            setDefaultPreference();
        }
    }

    private void setDefaultPreference() {
        SharedPreferences.Editor editor = _pref.edit();
        editor.clear();
        editor.putString(getString(R.string.pref_page_ejection), getString(R.string.pref_page_ejection_val_leftToRight));
        editor.putBoolean(getString(R.string.pref_initialized), true);
        editor.putStringSet(getString(R.string.pref_folder_path), new TreeSet<String>());
        editor.commit();
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return _detector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
//    }

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
                //startActivity(intent);
                startActivityForResult(intent, PREFERENCE_ACTIVITY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Endymion", "on activity result" + requestCode + "," + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        //Bundle _bundle = data.getExtras();
        switch (requestCode) {
            case PREFERENCE_ACTIVITY:
                // 戻ってきたら再描画
        }
    }
}
