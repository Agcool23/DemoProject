package gupta.ankit.demoproject.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import gupta.ankit.demoproject.Fragments.PopularDealsFragment;
import gupta.ankit.demoproject.Fragments.TopDealsFragment;

public class ViewPagerAdapter  extends FragmentPagerAdapter{

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;


        if (position == 0) {
            fragment = TopDealsFragment.newInstance();


        }
        else if (position == 1) {

            fragment = PopularDealsFragment.newInstance();

        }
        return fragment;
    }
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
