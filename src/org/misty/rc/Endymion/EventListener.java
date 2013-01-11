package org.misty.rc.Endymion;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
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
public class EventListener implements GestureDetector.OnGestureListener {

    private Context _context;
    private ViewFlipper _flipper;
    private Resources _res;
    private SharedPreferences _pref;

    private Animation inFromLeft;
    private Animation inFromRight;
    private Animation outToLeft;
    private Animation outToRight;

    public EventListener(Context context, ViewFlipper flipper) {
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
        boolean ejection = _pref.getBoolean(_res.getString(R.string.pref_page_ejection), true);

        if(dx > dy) {
            if(velocityX > 0) {
                // →
                _flipper.setInAnimation(inFromLeft);
                _flipper.setOutAnimation(outToRight);
                if(ejection) {
                    _flipper.showNext();
                } else {
                    _flipper.showPrevious();
                }
            } else {
                // ←
                _flipper.setInAnimation(inFromRight);
                _flipper.setOutAnimation(outToLeft);
                if(ejection) {
                    _flipper.showPrevious();
                } else {
                    _flipper.showNext();
                }
            }
            return true;
        }
        return false;
    }
}
