package org.misty.rc.Endymion.FileSelector;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/17
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public class FileInfoAdapter extends ArrayAdapter<FileInfo> {
    private Context _context;
    private List<FileInfo> _fileInfoList;

    public FileInfoAdapter(Context context, int textViewResourceId, List<FileInfo> objects) {
        super(context, textViewResourceId, objects);
        _fileInfoList = objects;
    }

    @Override
    public FileInfo getItem(int position) {
        return _fileInfoList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        if(null == convertView) {

        }

        FileInfo _fileinfo = _fileInfoList.get(position);
        if(_fileinfo.isDirectory()) {
            //ディレクトリの場合
        } else {
            //ファイルの場合
        }

        return convertView;
    }

}
