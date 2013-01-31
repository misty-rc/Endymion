package org.misty.rc.Endymion.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.UserDictionary;
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

    private FolderViewAdapter _adapter;


    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, LOG_TAG + " onAttach");
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onPause() {
        Log.d(TAG, LOG_TAG + " onPause");
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onResume() {
        Log.d(TAG, LOG_TAG + " onResume");
        _FolderInfoList = getFolderInfoList(getPathSet());
        _adapter.clear();
        _adapter.addAll(_FolderInfoList);
        _adapter.notifyDataSetChanged();
        _gridView.invalidateViews();
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, LOG_TAG + " onCreate");
        super.onCreate(savedInstanceState);
        _FolderInfoList = getFolderInfoList(getPathSet());

        thumbTest(_FolderInfoList.get(0).getAbsPath());
    }

    private void thumbTest(String path) {
        String _path = path;
        Uri uri3 = Uri.fromFile(new File(_path));

        ContentResolver cr = getActivity().getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri.Builder builder = new Uri.Builder();

        builder.path(_path);
        Uri uri2 = builder.build();
        Cursor c = cr.query(
                uri,
                new String[] {
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.MIME_TYPE,
                        MediaStore.Images.Media.TITLE
                },
                null,
                null,
                null
        );

        Log.d(TAG, LOG_TAG + " Cursor Count: " + c.getCount());

        c.moveToFirst();
        for (int k = 0; k < c.getCount(); k++ ) {
            Log.d(TAG, "ID= " + c.getString(c.getColumnIndexOrThrow("_id")));
            for(String column : c.getColumnNames()) {
                Log.d(TAG, column + " = " + c.getString(c.getColumnIndexOrThrow(column)));
            }
            c.moveToNext();
        }
    }

    private Set<String> getPathSet() {
        SharedPreferences _pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return _pref.getStringSet(getString(R.string.pref_folder_path), new TreeSet<String>());
    }

    private List<FolderInfo> getFolderInfoList(Set<String> paths) {
        List<FolderInfo> _list = new ArrayList<FolderInfo>();
        for (String path : paths) {
            FolderInfo inf = new FolderInfo(new File(path), FolderInfo.FLAG_STATIC_FOLDER);
            Log.d(TAG, LOG_TAG + " getFolderInfoList, " + inf.getName());
            _list.add(inf);
        }
        return _list;
    }

    private TextView _textView;
    private GridView _gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, LOG_TAG + " onCreateView");
        _gridView = new GridView(getActivity());
        //_gridView.setTag(R.string.id_folder_view_gridview);
        _textView = new TextView(getActivity());
        int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
        int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

        // inflate view
        View v = inflater.inflate(R.layout.folder_view_frag, container, false);
        if(_FolderInfoList.isEmpty()) {
            // パスが無い（からっぽ）の場合
            _textView.setText("フォルダが設定されていません");
            _textView.setGravity(Gravity.CENTER);
            ((LinearLayout) v).addView(_textView, new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        } else {
            // パスが設定されている場合
            _gridView.setNumColumns(4);
            _gridView.setColumnWidth(40);
            _gridView.setVerticalSpacing(10);
            _gridView.setHorizontalSpacing(10);
            _gridView.setGravity(Gravity.CENTER);
            ((LinearLayout) v).addView(_gridView, new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        }
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _adapter = new FolderViewAdapter(getActivity(), 0, _FolderInfoList);
        _gridView.setAdapter(_adapter);
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
}
