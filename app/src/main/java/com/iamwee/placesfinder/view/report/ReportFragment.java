package com.iamwee.placesfinder.view.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;


public class ReportFragment extends PlacesFinderFragment<ReportContractor.Presenter>
        implements ReportContractor.View {

    public ReportFragment() {
    }

    public static ReportFragment newInstance() {
        Bundle args = new Bundle();
        ReportFragment fragment = new ReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReportPresenter.newInstance(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    public void onExecuting() {

    }

    @Override
    public void onPostExecute() {

    }

    @Override
    public void onNetworkConnectionFailure() {

    }

    @Override
    public void onShowToastMessage(String message) {

    }
}
