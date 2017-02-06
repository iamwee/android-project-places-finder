package com.iamwee.placesfinder.view.walk_through;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.event.OpenActivity;
import com.rd.PageIndicatorView;

import org.greenrobot.eventbus.EventBus;


public class WalkThroughFragment extends PlacesFinderFragment
        implements View.OnClickListener {

    private PageIndicatorView pageIndicatorView;
    private ViewPager pager;

    public WalkThroughFragment() {

    }

    public static WalkThroughFragment newInstance() {
        Bundle args = new Bundle();
        WalkThroughFragment fragment = new WalkThroughFragment();
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
    protected void initView(View rootView) {
        pager = (ViewPager) rootView.findViewById(R.id.view_pager);
        pageIndicatorView = (PageIndicatorView) rootView.findViewById(R.id.page_indicator_view);
    }

    @Override
    protected void setupView(View rootView) {
        pager.setAdapter(new WalkThroughPagerAdapter());
        pageIndicatorView.setViewPager(pager);

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
