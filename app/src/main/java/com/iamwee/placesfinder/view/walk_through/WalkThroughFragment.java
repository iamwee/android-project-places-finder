package com.iamwee.placesfinder.view.walk_through;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.common.event.OpenActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Zeon on 2/1/2560.
 */

public class WalkThroughFragment
        extends PlacesFinderFragment<WalkThroughContractor.Presenter>
        implements WalkThroughContractor.View, View.OnClickListener {

    public WalkThroughFragment() {

    }

    public static WalkThroughFragment newInstance() {
        WalkThroughFragment fragment = new WalkThroughFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_walk_through, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    public void onNetworkConnectionFailure() {

    }

    @Override
    public void onShowToast(String message) {

    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void setupView(View rootView) {
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.view_pager);
        pager.setAdapter(new WalkThroughPagerAdapter());

        rootView.findViewById(R.id.btn_create_account).setOnClickListener(this);
        rootView.findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_create_account) {
            openActivity(OpenActivity.REGISTER_ACTIVITY);
        } else if (id == R.id.btn_login) {
            openActivity(OpenActivity.LOGIN_ACTIVITY);
        }
    }

    private void openActivity(int status) {
        EventBus.getDefault().post(new OpenActivity(status));
    }
}
