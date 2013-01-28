package org.misty.rc.Endymion.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.misty.rc.Endymion.R;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/28
 * Time: 10:09
 * To change this template use File | Settings | File Templates.
 */
public class FolderViewFragment extends Fragment {
    private String[] TestArray = new String[]{"hoge","foo","bar","test01","test02","test03","test04"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.folder_view_frag, container, false);
        //GridView _grid = (GridView)v.findViewById(R.id.folder_grid);
        //_grid.setAdapter(new TestAdapter(this.getActivity()));
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridView _gridView = (GridView) this.getActivity().findViewById(R.id.folder_grid);
        _gridView.setAdapter(new TestAdapter(this.getActivity()));
    }

    public class TestAdapter extends BaseAdapter {
        private Context _context;

        public TestAdapter(Context context) {
            _context = context;
        }

        @Override
        public int getCount() {
            return TestArray.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView _textView;
            if(convertView == null) {
                _textView = new TextView(_context);
                _textView.setLayoutParams(new GridView.LayoutParams(85, 85));
                _textView.setPadding(8,8,8,8);

            } else {
                _textView = (TextView) convertView;
            }
            _textView.setText(String.valueOf(position));
            return _textView;
        }
    }
}
