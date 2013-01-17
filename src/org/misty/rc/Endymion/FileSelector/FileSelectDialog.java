package org.misty.rc.Endymion.FileSelector;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/17
 * Time: 15:47
 * To change this template use File | Settings | File Templates.
 */
public class FileSelectDialog implements AdapterView.OnItemClickListener {
    private Context _context;
    private OnFileSelectListener _listener;
    private AlertDialog _dialog;

    public FileSelectDialog(Context context) {
        this._context = context;
    }

    public void setOnFileSelectListener(OnFileSelectListener listener) {
        this._listener = listener;
    }

    public void show(File fileDir) {
        String _title = fileDir.getAbsolutePath();

        // ビュー
        ListView _listview = new ListView(_context);
        _listview.setScrollingCacheEnabled(false);
        _listview.setOnItemClickListener(this);

        // リスト
        ExtendedFilter _filter = new ExtendedFilter();
        File[] _files = fileDir.listFiles(_filter);
        List<FileInfo> _fileInfoList = new ArrayList<FileInfo>();
        if(null != _files) {
            for(File ftemp : _files) {
                _fileInfoList.add(new FileInfo(ftemp.getName(), ftemp));
            }
            Collections.sort(_fileInfoList);
        }

        if(null != fileDir.getParent()) {
            _fileInfoList.add(0, new FileInfo("..", new File(fileDir.getParent())));
        }

        FileInfoAdapter _adapter = new FileInfoAdapter(_context, 0, _fileInfoList);
        _listview.setAdapter(_adapter);

        AlertDialog.Builder _builder = new AlertDialog.Builder(_context);
        _builder.setTitle(_title);
        _builder.setView(_listview);
        _dialog = _builder.show();
    }

    private class ExtendedFilter implements FileFilter {
        private boolean isDirOnly = true;
        private boolean isEnableFilter = true;

        public void setDirOnly(boolean state) {
            isDirOnly = state;
        }

        public void setEnableFilter(boolean state) {
            isEnableFilter = state;
        }

        @Override
        public boolean accept(File pathname) {
            if(isEnableFilter) {
                if(pathname.isDirectory()) {
                    return true;
                } else {
                    if(isDirOnly) {
                        return false;
                    } else {
                        //ファイル一覧
                        //pathname.getName().toLowerCase().endsWith("png");
                    }
                }
            }
            return false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public interface OnFileSelectListener {
        public void onFileSelect(File file);
    }
}
