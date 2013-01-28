package org.misty.rc.Endymion.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.misty.rc.Endymion.R;
import org.misty.rc.Endymion.model.PathInfo;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/18
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class FolderSelectorFragment extends DialogFragment implements AdapterView.OnItemClickListener{
    FolderSelectorListener _listener;
    FolderSelectorAdapter _adapter;

    private List<PathInfo> _listPathInfo;
    private File _currentDir;
    private TextView _textView;

    private String _tag_current;
    private String _tag_dialog_add_path;
    private String _tag_dialog_delete_path;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //interface実装の保証
            _listener = (FolderSelectorListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement FolderSelectorListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _tag_current = getTag();
        _tag_dialog_add_path = getString(R.string.dialog_add_path);
        _tag_dialog_delete_path = getString(R.string.dialog_delete_path);
    }

    private FileFilter getDirectoryFileFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        };
    }

    private void createDirectoryList(File currentDir) {
        if(null == _listPathInfo) {
            _listPathInfo = new ArrayList<PathInfo>();
        }
        _listPathInfo.clear();
        File[] _listfiles = currentDir.listFiles(getDirectoryFileFilter());
        if(null != _listfiles) {
            for(File path : _listfiles) {
                _listPathInfo.add(new PathInfo(path.getName(), path));
            }
            Collections.sort(_listPathInfo);
        }
        if(null != currentDir.getParent()) {
            _listPathInfo.add(0, new PathInfo("..", new File(currentDir.getParent())));
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater _inflater = getActivity().getLayoutInflater();

        Log.d("Endymion", "TAG: " + _tag_current);

        if(_tag_current.equals(_tag_dialog_add_path)) {
            View _view = _inflater.inflate(R.layout.folderlist_choice, null, false);

            //初期ディレクトリはユーザーディレクトリ直下
            _currentDir = Environment.getExternalStorageDirectory();
            createDirectoryList(_currentDir);

            _adapter = new FolderSelectorAdapter(getActivity(), 0, _listPathInfo);
            ListView _listView = (ListView)_view.findViewById(R.id.folderlist_choice_view);
            _listView.setAdapter(_adapter);
            _listView.setOnItemClickListener(this);
            _textView = (TextView)_view.findViewById(R.id.folderlist_choice_current_dir);
            _textView.setText(_currentDir.getAbsolutePath());

            //builder setting
            builder.setTitle("フォルダを選択");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Bundle args = new Bundle();
                    //選択したパスをセット
                    args.putString(getString(R.string.add_path), _currentDir.getAbsolutePath());
                    //ダイアログタグをセット
                    args.putString(_tag_current, _tag_dialog_add_path);
                    _listener.onDialogPositiveClick(FolderSelectorFragment.this, args );
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    _listener.onDialogNegativeClick(FolderSelectorFragment.this);
                }
            });
            builder.setView(_view);
        } else if (_tag_current.equals(_tag_dialog_delete_path)) {
            builder.setTitle("パス削除");
            builder.setMessage("削除してもよろしいですか？");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Bundle args = getArguments();
                    args.putString(_tag_current, _tag_dialog_delete_path);
                    _listener.onDialogPositiveClick(FolderSelectorFragment.this, args);
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    _listener.onDialogNegativeClick(FolderSelectorFragment.this);
                }
            });
        } else {
            //nothing
        }
        return builder.create();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //選択項目を取得
        PathInfo _pathInfo = _adapter.getItem(position);
        Log.d("Endymion", "touch path: " + _pathInfo.getName() + " / " + _pathInfo.getFile().getAbsolutePath());

        //currentDirを変更
        _currentDir = _pathInfo.getFile();

        //要素を削除
        _adapter.clear();
        _textView.setText(_currentDir.getAbsolutePath());
        createDirectoryList(_currentDir);
    }

    public class FolderSelectorAdapter extends ArrayAdapter<PathInfo> {
        private LayoutInflater _inflater;
        private List<PathInfo> _list;

        public FolderSelectorAdapter(Context context, int textViewResourceId, List<PathInfo> objects) {
            super(context, textViewResourceId, objects);
            _inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _list = objects;
        }

        @Override
        public PathInfo getItem(int position) {
            return _list.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PathInfo _file = getItem(position);
            if(null == convertView) {
                convertView = _inflater.inflate(android.R.layout.simple_list_item_1, null);
            }
            ((TextView)convertView.findViewById(android.R.id.text1)).setText(_file.getName());
            return convertView;
        }
    }

    public interface FolderSelectorListener {
        public void onDialogPositiveClick(FolderSelectorFragment dialog, Bundle args);
        public void onDialogNegativeClick(FolderSelectorFragment dialog);
    }
}
