package org.misty.rc.Endymion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/16
 * Time: 13:22
 * To change this template use File | Settings | File Templates.
 */
public class MediaManager {
    private Context _context;
    private SharedPreferences _pref;

    public MediaManager(Context context) {
        this._context = context;
    }

    public void initTest() {
        //_pref = PreferenceManager.getDefaultSharedPreferences(_context);
    }

    private void getDataPath() {
        //SharedPreferences.Editor editor = _pref.edit();
        
    }



}
