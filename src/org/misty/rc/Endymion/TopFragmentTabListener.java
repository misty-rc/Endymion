package org.misty.rc.Endymion;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/28
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
public class TopFragmentTabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment _fragment;
    private final Activity _activity;
    private final String _tag;
    private final Class<T> _class;

    public TopFragmentTabListener(Activity activity, String tag, Class<T> clz) {
        _activity = activity;
        _tag = tag;
        _class = clz;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if(_fragment == null) {
            _fragment = Fragment.instantiate(_activity, _class.getName());
            ft.add(android.R.id.content, _fragment, _tag);
        } else {
            ft.attach(_fragment);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if(_fragment != null) {
            ft.detach(_fragment);
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
