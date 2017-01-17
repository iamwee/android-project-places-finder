package com.iamwee.placesfinder.view.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.view.main.pager.MainPagerAdapter;

public class MainFragment extends PlacesFinderFragment<MainContractor.Presenter>
        implements MainContractor.View {

    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainPresenter.newInstance(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void setupView(View rootView) {
        ViewPager vpMain = (ViewPager) rootView.findViewById(R.id.vp_main);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        vpMain.setAdapter(new MainPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(vpMain);
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onStart();
        getPresenter().getCurrentProfile();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("getPresenter", getPresenter().onSaveInstanceState());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
            getPresenter().onRestoreInstanceState(savedInstanceState.getBundle("getPresenter"));
    }

    @Override
    public void onNetworkConnectionFailure() {

    }

    @Override
    public void onShowToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onExecuting() {

    }

    @Override
    public void onPostExecute() {

    }
}
