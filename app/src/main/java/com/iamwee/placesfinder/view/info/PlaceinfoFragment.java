package com.iamwee.placesfinder.view.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.view.info.adapter.PlaceInfoAdapter;
import com.iamwee.placesfinder.view.info.adapter.model.BasePlaceInfoItem;
import com.iamwee.placesfinder.view.main.pager.recent.PlaceRecentAdapter;

import java.util.List;

public class PlaceInfoFragment extends PlacesFinderFragment<PlaceInfoContractor.Presenter>
        implements PlaceInfoContractor.View {

    private PlaceInfoAdapter placeInfoAdapter;
    private RecyclerView rvPlaceInfo;

    public PlaceInfoFragment() {

    }

    public static PlaceInfoFragment newInstance(Place place) {
        Bundle args = new Bundle();
        args.putParcelable("place", place);
        PlaceInfoFragment fragment = new PlaceInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PlaceInfoPresenter.newInstance(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_place_info, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    protected void initView(View rootView) {
        rvPlaceInfo = (RecyclerView) rootView.findViewById(R.id.rv_place_info);
        rvPlaceInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void setupView(View rootView) {
        //getPresenter().convertToAdapterModel((Place) getArguments().getParcelable("place"));
        rvPlaceInfo.setAdapter(new PlaceRecentAdapter());

    }

    @Override
    public void onNetworkConnectionFailure() {

    }

    @Override
    public void onShowToastMessage(String message) {

    }

    @Override
    public void onSetAdapter(List<BasePlaceInfoItem> basePlaceInfoItems) {
        placeInfoAdapter = new PlaceInfoAdapter(basePlaceInfoItems);
    }
}
