package com.iamwee.placesfinder.view.main.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.util.Contextor;
import com.iamwee.placesfinder.view.main.pager.nearby.PlaceNearbyFragment;
import com.iamwee.placesfinder.view.main.pager.recent.PlaceRecentFragment;

import java.util.ArrayList;
import java.util.List;


public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private List<PlacesFinderFragment> fragmentList;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<>();
        fragmentList.add(PlaceRecentFragment.newInstance());
        fragmentList.add(PlaceNearbyFragment.newInstance());
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
        Context context = Contextor.getInstance().getContext();
        if (position == 0) {
            return context.getString(R.string.recent);
        } else if (position == 1) {
            return context.getString(R.string.nearby);
        }
        return super.getPageTitle(position);
    }
}
