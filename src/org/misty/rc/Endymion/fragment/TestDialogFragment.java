package org.misty.rc.Endymion.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import org.misty.rc.Endymion.FolderListActivity;
import org.misty.rc.Endymion.R;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/18
 * Time: 14:14
 * To change this template use File | Settings | File Templates.
 */
public class TestDialogFragment extends DialogFragment {
    int mNum;

    public static TestDialogFragment newInstance(int val) {
        TestDialogFragment ins = new TestDialogFragment();

        Bundle args = new Bundle();
        args.putInt("num", val);
        ins.setArguments(args);

        return ins;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");

        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch ((mNum-1)%6) {
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
            case 4: style = DialogFragment.STYLE_NORMAL; break;
            case 5: style = DialogFragment.STYLE_NORMAL; break;
            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
            case 8: style = DialogFragment.STYLE_NORMAL; break;
        }
        switch ((mNum-1)%6) {
            case 4: theme = android.R.style.Theme_Holo; break;
            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
            case 6: theme = android.R.style.Theme_Holo_Light; break;
            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
            case 8: theme = android.R.style.Theme_Holo_Light; break;
        }
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test_dialog, container, false);
        View tv = v.findViewById(R.id.textview01);
        ((TextView)tv).setText("Dialog #" + mNum + ": using style ");

        // Watch for button clicks.
        Button button = (Button)v.findViewById(R.id.button01);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // When button is clicked, call up to owning activity.
                //((FolderListActivity)getActivity()).showDialog();
            }
        });

        return v;
    }
}
