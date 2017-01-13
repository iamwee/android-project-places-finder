package com.iamwee.placesfinder.view.main.pager.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;


public class ProfileFragment extends PlacesFinderFragment<ProfileContractor.Presenter>
        implements ProfileContractor.View{

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance(){
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProfilePresenter.newInstance(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
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

    }
}
