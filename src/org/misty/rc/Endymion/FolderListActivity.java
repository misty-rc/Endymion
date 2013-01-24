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

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/16
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
public class FolderListActivity extends Activity implements FolderSelectorFragment.FolderSelectorListener {

    private FolderListAdapter _adapter;
    private Context _context;
    private SharedPreferences _pref;
    private List<String> _listFolderPath;

    private String _pref_folder_path_key;
    private String _add_path;
    private Set<String> _paths = new TreeSet<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folderlist);

        _context = getApplicationContext();
        _pref = PreferenceManager.getDefaultSharedPreferences(_context);

        //get stringset
        _pref_folder_path_key = getString(R.string.pref_folder_path);
        _add_path = getString(R.string.add_path);

        _paths.addAll(_pref.getStringSet(_pref_folder_path_key, null));
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
                FolderSelectorFragment _selector = new FolderSelectorFragment();
                _selector.show(getFragmentManager(), "dialog");
                return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void addPathToPref(String value) {
        if(_listFolderPath.contains(value)) {
            //nothing
        } else {
            _listFolderPath.add(value);
            Collections.sort(_listFolderPath);

            _paths.add(value);
            SharedPreferences.Editor editor = _pref.edit();
            editor.putStringSet(_pref_folder_path_key, _paths);
            editor.commit();
            _adapter.notifyDataSetChanged();
        }
    }

    private void deletePathFromPref(String value) {

    }

    @Override
    public void onDialogPositiveClick(FolderSelectorFragment dialog, Bundle args) {
        Log.d("Endymion", "positive click");
        Log.d("Endymion", "add path: " + args.getString(_add_path));
        addPathToPref(args.getString(_add_path));
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Log.d("Endymion", "negative click");
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
            ((TextView) convertView.findViewById(R.id.folderpath)).setText(path);

            return convertView;
        }
    }
}
