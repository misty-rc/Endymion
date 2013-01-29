package org.misty.rc.Endymion.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.misty.rc.Endymion.R;
import org.misty.rc.Endymion.model.FolderInfo;

import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/28
 * Time: 10:09
 * To change this template use File | Settings | File Templates.
 */
public class FolderViewFragment extends Fragment {
    private final String TAG = "Endymion";
    private final String LOG_TAG = "FolderViewFragment";
    private List<FolderInfo> _FolderInfoList;

    public static final String[] TestArray = {"hoge","foo","bar","test01","test02","test03","test04"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, LOG_TAG + " onCreate");
        super.onCreate(savedInstanceState);

        //pref
        SharedPreferences _pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> _paths = _pref.getStringSet(getString(R.string.pref_folder_path), null);

        _FolderInfoList = getFolderInfoList(_paths);

        //FolderViewAdapter _adapter = new FolderViewAdapter(getActivity(), 0, _FolderInfoList);
    }

    private List<FolderInfo> getFolderInfoList(Set<String> paths) {
        List<FolderInfo> _list = new ArrayList<FolderInfo>();
        Iterator<String> it = paths.iterator();
        while(it.hasNext()) {
            FolderInfo inf = new FolderInfo(new File(it.next()), FolderInfo.FLAG_STATIC_FOLDER);
            Log.d(TAG, LOG_TAG + " getFolderInfoList, " + inf.getName());
            _list.add(inf);
        }
        return _list;
    }

    public class FolderViewAdapter extends ArrayAdapter<FolderInfo> {
        private Context _context;
        private List<FolderInfo> _list;
        private LayoutInflater _inflater;

        public FolderViewAdapter(Context context, int textViewResourceId, List<FolderInfo> objects) {
            super(context, textViewResourceId, objects);
            _context = context;
            _inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _list = objects;
        }

        @Override
        public int getCount() {
            return _list.size();
        }

        @Override
        public FolderInfo getItem(int position) {
            return _list.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FolderInfo _item = getItem(position);
            Log.d(TAG, LOG_TAG + " getView: " + _item.getName());
            if(convertView == null) {
                convertView = _inflater.inflate(R.layout.folder_view_frag_item1, null);
            }
            //((ImageView)convertView.findViewById(R.id.folder_view_frag_item1_thumb)).setImageBitmap();
            ((TextView)convertView.findViewById(R.id.folder_view_frag_item1_footer_name)).setText(_item.getName());
            ((TextView)convertView.findViewById(R.id.folder_view_frag_item1_footer_count)).setText(String.valueOf(_item.getCount()));

            return convertView;
        }
    }

    private FolderViewAdapter _adapter;

    private final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, LOG_TAG + " onCreateView");

        // inflate view
        View v = inflater.inflate(R.layout.folder_view_frag, container, false);

        if(_FolderInfoList.isEmpty()) {
            // パスが無い（からっぽ）の場合
            //_adapter.clear();
            TextView _tv = new TextView(getActivity());
            _tv.setText("フォルダが設定されていません");
            _tv.setGravity(Gravity.CENTER);
            ((LinearLayout) v).addView(_tv, new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        } else {
            // パスが設定されている場合
            _adapter = new FolderViewAdapter(getActivity(), 0, _FolderInfoList);
            GridView _gv = new GridView(getActivity());
            _gv.setNumColumns(4);
            _gv.setColumnWidth(40);
            _gv.setVerticalSpacing(10);
            _gv.setHorizontalSpacing(10);
            _gv.setGravity(Gravity.CENTER);
            _gv.setAdapter(_adapter);
            ((LinearLayout) v).addView(_gv, new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        }
        //
        //GridView _grid = (GridView)v.findViewById(R.id.folder_grid);
        //_grid.setAdapter(new TestAdapter(this.getActivity()));
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), R.layout.folder_view_frag_item, TestArray);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, TestArray);
        //TestAdapter adapter = new TestAdapter(getActivity());
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
