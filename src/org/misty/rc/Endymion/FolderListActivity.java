package org.misty.rc.Endymion;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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



        //_adapter = new ArrayAdapter<String>(this, 0, NAMES);
        List<String> _list = new ArrayList<String>();
        _list.add("hoghoge");
        _list.add("foobar");
        _list.add("/var/dev/random");

        _adapter = new FolderListAdapter(this, 0, _list);
        ListView _view = (ListView) findViewById(R.id.listview01);
        _view.setAdapter(_adapter);
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
