package org.misty.rc.Endymion;

import android.app.Activity;
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
import android.widget.ImageView;
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
public class FolderListActivity extends Activity implements FolderSelectorFragment.FolderSelectorListener, View.OnClickListener {

    private FolderListAdapter _adapter;
    private Context _context;
    private SharedPreferences _pref;
    private List<String> _listFolderPath;

    private String _pref_folder_path_key;
    private String _add_path;
    private String _tag_dialog_add_path;
    private String _tag_dialog_delete_path;
    private Set<String> _paths = new TreeSet<String>();

    private static final String KEY_POSITION = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folderlist);

        _context = getApplicationContext();
        _pref = PreferenceManager.getDefaultSharedPreferences(_context);

        //get stringset
        _pref_folder_path_key = getString(R.string.pref_folder_path);
        _add_path = getString(R.string.add_path);
        _tag_dialog_add_path = getString(R.string.dialog_add_path);
        _tag_dialog_delete_path = getString(R.string.dialog_delete_path);

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
                _selector.show(getFragmentManager(), _tag_dialog_add_path);
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
            if(updatePreference(_pref_folder_path_key, _paths)) {
                _adapter.notifyDataSetChanged();
            }
        }
    }

    private void deletePathFromPref(String value) {
        _listFolderPath.remove(value);
        Collections.sort(_listFolderPath);

        _paths.remove(value);
        if(updatePreference(_pref_folder_path_key, _paths)) {
            _adapter.notifyDataSetChanged();
        }
    }

    private boolean updatePreference(String key, Set<String> values) {
        try {
            SharedPreferences.Editor editor = _pref.edit();
            editor.putStringSet(key, values);
            editor.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onDialogPositiveClick(FolderSelectorFragment dialog, Bundle args) {
        if(_tag_dialog_add_path.equals(args.get(_tag_dialog_add_path))) {
            addPathToPref(args.getString(_add_path));
        } else if(_tag_dialog_delete_path.equals(args.getString(_tag_dialog_delete_path))) {
            int _pos = args.getInt(KEY_POSITION);
            deletePathFromPref(_adapter.getItem(_pos));
        }
    }

    @Override
    public void onDialogNegativeClick(FolderSelectorFragment dialog) {
        Log.d("Endymion", "negative click");
    }

    @Override
    public void onClick(View v) {
        int _click_pos = Integer.parseInt(v.getTag().toString());

        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, _click_pos);

        FolderSelectorFragment _dialog = new FolderSelectorFragment();
        _dialog.setArguments(args);
        _dialog.show(getFragmentManager(), _tag_dialog_delete_path);
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
            convertView.findViewById(R.id.trushmark).setOnClickListener(FolderListActivity.this);
            convertView.findViewById(R.id.trushmark).setTag(position);

            return convertView;
        }
    }
}
