package org.misty.rc.Endymion;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.*;
import android.widget.*;
import org.misty.rc.Endymion.fragment.TestDialogFragment;

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
                DirectorySelectorFragment _frag = DirectorySelectorFragment.newInstance(99);
                _frag.show(getFragmentManager(), "dialog");
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

    int mstacklebel = 0;

    //TestDialogFragment
    public void showDialog() {
        mstacklebel++;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if(null != prev) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment diag = TestDialogFragment.newInstance(mstacklebel);
        diag.show(ft, "dialog");
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

    public void doPositiveClick() {
        Log.d("Endymion", "Positive Click");
    }

    public void doNegativeClick() {
        Log.d("Endymion", "Negative Click");
    }

    public static class DirectorySelectorFragment extends DialogFragment {

        public static DirectorySelectorFragment newInstance(int val) {
            DirectorySelectorFragment ins = new DirectorySelectorFragment();
            Bundle args = new Bundle();
            args.putInt("test", val);
            ins.setArguments(args);
            return ins;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            Log.d("Endymion", "onCreate");
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Log.d("Endymion", "onCreateView");
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Log.d("Endymion", "onCreateDialog");

            int _val = getArguments().getInt("test");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("テストタイトル" + _val);
            //builder.setMessage("テストメッセージ");
            //array item test
            // memo: シングルチョイスのこれはシンプル過ぎて使えない
/*            builder.setItems(R.array.test_path_array_value, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity(), "Choice: " + which, Toast.LENGTH_SHORT).show();
                }
            });*/
/*            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((FolderListActivity) getActivity()).doPositiveClick();
                        }
                    }
            );

            builder.setNegativeButton("CANCEL",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((FolderListActivity) getActivity()).doNegativeClick();
                        }
                    }
            );*/
            return builder.create();
        }
    }
}
