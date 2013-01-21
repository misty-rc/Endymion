package org.misty.rc.Endymion;

import android.app.*;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.*;
import android.widget.*;
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
public class FolderListActivity extends Activity implements FolderSelectorFragment.FolderSelectorListener {

    //private ArrayAdapter<String> _adapter;
    private FolderListAdapter _adapter;
    //static final String[] NAMES = new String[]{"Foobar", "hogehoge", "test01"};
    private Context _context;
    private SharedPreferences _pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folderlist);

        _context = getApplicationContext();
        _pref = PreferenceManager.getDefaultSharedPreferences(_context);

        Environment.getExternalStorageDirectory();

        //get stringset
        String _paths_key = getString(R.string.pref_folder_path);
        Set<String> _paths = _pref.getStringSet(_paths_key, null);

        _adapter = new FolderListAdapter(this, 0, EndymionUtil.toStringList(_paths));
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
                //DirectorySelectorFragment _frag = DirectorySelectorFragment.newInstance(99);
                FolderSelectorFragment _selector = new FolderSelectorFragment();
                _selector.show(getFragmentManager(), "dialog");
                //_frag.show(getFragmentManager(), "dialog");
                //showDialog();
                return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        //save folder paths
        ListView _view = (ListView) findViewById(R.id.listview01);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDialogPositiveClick(FolderSelectorFragment dialog, Bundle args) {
        Log.d("Endymion", "positive click");
        Log.d("Endymion", "add path: " + args.getString("add_path"));
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Log.d("Endymion", "negative click");
    }

    public class FolderListAdapter extends ArrayAdapter<String> {
        private LayoutInflater _inflater;

        public FolderListAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            _inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
