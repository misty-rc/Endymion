package org.misty.rc.Endymion;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/11
 * Time: 11:44
 * To change this template use File | Settings | File Templates.
 */
public class GestureListener implements GestureDetector.OnGestureListener {

    private Context _context;
    private ViewFlipper _flipper;
    private Resources _res;
    private SharedPreferences _pref;

    private Animation inFromLeft;
    private Animation inFromRight;
    private Animation outToLeft;
    private Animation outToRight;

    private GestureListener _listener;

    public GestureListener(Context context, ViewFlipper flipper) {
        this._context = context;
        this._flipper = flipper;
        //this._pref = _context.getSharedPreferences(D.pref.NAME, Context.MODE_PRIVATE);
        this._pref = PreferenceManager.getDefaultSharedPreferences(_context);
        this._res = _context.getResources();

        inFromLeft = AnimationUtils.loadAnimation(_context, R.anim.slide_in_from_left);
        inFromRight = AnimationUtils.loadAnimation(_context, R.anim.slide_in_from_right);
        outToLeft = AnimationUtils.loadAnimation(_context, R.anim.slide_out_to_left);
        outToRight = AnimationUtils.loadAnimation(_context, R.anim.slide_out_to_right);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float dx = Math.abs(e1.getX() - e2.getX());
        float dy = Math.abs(e1.getY() - e2.getY());
        String _key = _res.getString(R.string.pref_page_ejection, null);
        String _default = _res.getString(R.string.pref_page_ejection_val_leftToRight, null);
        String _ejection = _pref.getString(_key, _default);

        if(dx > dy) {
            if(velocityX > 0) {
                // →
                _flipper.setInAnimation(inFromLeft);
                _flipper.setOutAnimation(outToRight);
                Log.d("Endymion", "fling: left to right");
                if(_ejection.equals(_default)) {
                    _flipper.showNext();
                    Log.d("Endymion", "showNext");
                } else {
                    _flipper.showPrevious();
                    Log.d("Endymion", "showPrevious");
                }
            } else {
                // ←
                _flipper.setInAnimation(inFromRight);
                _flipper.setOutAnimation(outToLeft);
                Log.d("Endymion", "fling: right to left");
                if(_ejection.equals(_default)) {
                    _flipper.showPrevious();
                    Log.d("Endymion", "showPrevious");
                } else {
                    _flipper.showNext();
                    Log.d("Endymion", "showNext");
                }
            }
            return true;
        }
        return false;
    }
}
