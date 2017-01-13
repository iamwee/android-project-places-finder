package com.iamwee.placesfinder.view.main.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.view.main.pager.placesmap.PlacesMapFragment;
import com.iamwee.placesfinder.view.main.pager.profile.ProfileFragment;
import com.iamwee.placesfinder.view.main.pager.recent.RecentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zeon on 4/1/2560.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private List<PlacesFinderFragment> fragmentList;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<>();
        fragmentList.add(RecentFragment.newInstance());
        fragmentList.add(PlacesMapFragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Recently";
        } else if (position == 1) {
            return "Nearby";
        }
        return super.getPageTitle(position);
    }
}
