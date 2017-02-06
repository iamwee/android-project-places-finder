package com.iamwee.placesfinder.view.logout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.event.OpenActivity;

import org.greenrobot.eventbus.EventBus;


public class LogoutFragment extends PlacesFinderFragment<LogoutContractor.Presenter>
        implements LogoutContractor.View {

    public LogoutFragment() {

    }

    public static LogoutFragment newInstance() {
        LogoutFragment fragment = new LogoutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogoutPresenter.newInstance(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_logout, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    public void onNetworkConnectionFailure() {
        onShowToastMessage(getString(R.string.error_check_internet_connection));
    }

    @Override
    public void onShowToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onStart();
        getPresenter().logout();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPostLogout() {
        EventBus.getDefault().post(new OpenActivity(OpenActivity.SPLASH_SCREEN_ACTIVITY));
    }
}
