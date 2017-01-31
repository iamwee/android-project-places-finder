package com.iamwee.placesfinder.view.main.pager.recent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.util.PlaceUtil;

import java.util.ArrayList;


public class PlaceRecentFragment extends PlacesFinderFragment<PlaceRecentContractor.Presenter>
        implements PlaceRecentContractor.View, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rvRecentPlace;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PlaceRecentAdapter placeRecentAdapter;

    public PlaceRecentFragment() {

    }

    public static PlaceRecentFragment newInstance() {
        PlaceRecentFragment fragment = new PlaceRecentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PlaceRecentPresenter.newInstance(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_place_recent, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    public void onNetworkConnectionFailure() {

    }

    @Override
    public void onShowToastMessage(String message) {

    }

    @Override
    protected void initView(View rootView) {
        rvRecentPlace = (RecyclerView) rootView.findViewById(R.id.rv_recent_place);
        rvRecentPlace.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout = (SwipeRefreshLayout)
                rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
    }

    @Override
    protected void setupView(View rootView) {
        if (PlaceUtil.load().size() > 0) {
            placeRecentAdapter = new PlaceRecentAdapter(PlaceUtil.load());
            rvRecentPlace.setAdapter(placeRecentAdapter);
            swipeRefreshLayout.setOnRefreshListener(this);
        } else {
            ViewStub viewStub = (ViewStub) rootView.findViewById(R.id.view_stub);
            viewStub.inflate();
        }
    }

    @Override
    public void onRefresh() {
        getPresenter().getPlacesData();
        placeRecentAdapter.setPlacesData(new ArrayList<Place>());
    }

    @Override
    public void onRefreshed() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onUpdatePlacesData(ArrayList<Place> places) {
        placeRecentAdapter.setPlacesData(places);
        onRefreshed();
    }
}
