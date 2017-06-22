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
public class PopularDealsFragment extends Fragment {


    public static PopularDealsFragment newInstance(){
        PopularDealsFragment popularDealsFragment = new PopularDealsFragment();
        return popularDealsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

}
