package org.misty.rc.Endymion;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/16
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
public class FolderListActivity extends Activity {

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
        //get preference values
        _pref = PreferenceManager.getDefaultSharedPreferences(_context);

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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public class FolderListAdapter extends ArrayAdapter<String> {
        private LayoutInflater _inflater;

        public FolderListAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            _inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //return super.getView(position, convertView, parent);
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
