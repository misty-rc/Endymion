package org.misty.rc.Endymion;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.misty.rc.Endymion.fragment.FolderSelectorFragment;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/16
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
public class FolderListActivity extends Activity implements FolderSelectorFragment.FolderSelectorListener, SharedPreferences.OnSharedPreferenceChangeListener {

    //private ArrayAdapter<String> _adapter;
    private FolderListAdapter _adapter;
    //static final String[] NAMES = new String[]{"Foobar", "hogehoge", "test01"};
    private Context _context;
    private SharedPreferences _pref;
    private List<String> _listFolderPath;

    private String _pref_folder_path_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folderlist);

        _context = getApplicationContext();
        _pref = PreferenceManager.getDefaultSharedPreferences(_context);

        //get stringset
        _pref_folder_path_key = getString(R.string.pref_folder_path);
        Set<String> _paths = _pref.getStringSet(_pref_folder_path_key, null);
        _listFolderPath = EndymionUtil.toStringList(_paths);

        _adapter = new FolderListAdapter(this, 0, _listFolderPath);
        ListView _view = (ListView) findViewById(R.id.listview01);
        _view.setAdapter(_adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.folder_list_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.folder_add:
                // folder picker
                FolderSelectorFragment _selector = new FolderSelectorFragment();
                _selector.show(getFragmentManager(), "dialog");
                return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        //_pref.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
        //_pref.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void addPathToPref(String value) {
        //String key = getString(R.string.pref_folder_path);
        _listFolderPath.add(value);

        Set<String> _paths = _pref.getStringSet(_pref_folder_path_key, null);
        _paths.add(value);

        SharedPreferences.Editor editor = _pref.edit();
        editor.putStringSet(_pref_folder_path_key, _paths);
        editor.commit();
    }

    private void deletePathFromPref(String value) {

    }

    @Override
    public void onDialogPositiveClick(FolderSelectorFragment dialog, Bundle args) {
        Log.d("Endymion", "positive click");
        Log.d("Endymion", "add path: " + args.getString(getString(R.string.add_path)));
        addPathToPref(args.getString(getString(R.string.add_path)));
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Log.d("Endymion", "negative click");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d("Endymion", "on shared preference changed");
        _adapter.notifyDataSetChanged();
    }

    public class FolderListAdapter extends ArrayAdapter<String> {
        private LayoutInflater _inflater;
        private List<String> _list;

        public FolderListAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            _inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _list = objects;
        }

        @Override
        public String getItem(int position) {
            return _list.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String path = getItem(position);
            if(null == convertView) {
                convertView = _inflater.inflate(R.layout.folder_list_item, null);
            }
            TextView tv;
            tv = (TextView) convertView.findViewById(R.id.folderpath);
            tv.setText(path);

            return convertView;
        }
    }
}
