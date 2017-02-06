package com.iamwee.placesfinder.view.walk_through;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iamwee.placesfinder.R;


public class WalkThroughPagerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup layout;
        switch (position) {
            case 0:
                layout = (ViewGroup) LayoutInflater
                        .from(container.getContext()).inflate(R.layout.item_walk_through_one, container, false);
                break;
            case 1:
                layout = (ViewGroup) LayoutInflater
                        .from(container.getContext()).inflate(R.layout.item_walk_through_two, container, false);
                break;
            case 2:
                layout = (ViewGroup) LayoutInflater
                        .from(container.getContext()).inflate(R.layout.item_walk_through_three, container, false);
                break;
            default:
                throw new NullPointerException("Unexpected position: " + position);
        }
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
