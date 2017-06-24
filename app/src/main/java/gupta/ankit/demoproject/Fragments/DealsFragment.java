package gupta.ankit.demoproject.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gupta.ankit.demoproject.Adapters.ViewPagerAdapter;
import gupta.ankit.demoproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DealsFragment extends Fragment {


    View view;
    public static final String Fragment_TAG = DealsFragment.class.getSimpleName();
    ViewPager viewPager;
    TabLayout tabLayout;
    TabLayout.TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener;
    TopDealsFragment topDealsFragment;
    PopularDealsFragment popularDealsFragment;
    public DealsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.deals_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);

        tabLayoutOnPageChangeListener = new TabLayout.TabLayoutOnPageChangeListener(tabLayout);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                getFragmentByPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
    }
    public void init(){
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
    }
    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        topDealsFragment=new TopDealsFragment();
        popularDealsFragment=new PopularDealsFragment();
        adapter.addFragment(topDealsFragment,"TOP");
        adapter.addFragment(popularDealsFragment,"POPULAR");
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private android.support.v4.app.Fragment getFragmentByPosition(int position) {
        android.support.v4.app.Fragment fragment = null;

        switch (position) {
            case 0:
                Log.e("Fragment Position",position+"");
                break;

            case 1:
                Log.e("Fragment Position",position+"");
                break;

            case 2:
                Log.e("Fragment Position",position+"");
                break;
        }

        return fragment;
    }
}
