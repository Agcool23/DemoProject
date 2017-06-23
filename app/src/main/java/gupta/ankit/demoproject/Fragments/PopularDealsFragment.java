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


    View view;
    public static final String Fragment_TAG = PopularDealsFragment.class.getSimpleName();
    public static PopularDealsFragment newInstance(){
        PopularDealsFragment popularDealsFragment = new PopularDealsFragment();
        return popularDealsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.popular_deals_fragment, container, false);
        return view;
    }

}
