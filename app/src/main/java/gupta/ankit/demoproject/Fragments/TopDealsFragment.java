package gupta.ankit.demoproject.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gupta.ankit.demoproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopDealsFragment extends Fragment {


    public static TopDealsFragment newInstance(){
        TopDealsFragment topDealsFragment = new TopDealsFragment();
        return topDealsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

}
