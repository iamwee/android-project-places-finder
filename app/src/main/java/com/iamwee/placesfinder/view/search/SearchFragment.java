package com.iamwee.placesfinder.view.search;

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

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends PlacesFinderFragment {

    private RecyclerView rvSearchPlace;
    private PlaceSearchAdapter placeSearchAdapter;

    public SearchFragment() {

    }

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
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
    protected void initView(View rootView) {
        rvSearchPlace = (RecyclerView) rootView.findViewById(R.id.rv_place_search);
        rvSearchPlace.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void setupView(View rootView) {
        placeSearchAdapter = new PlaceSearchAdapter(new ArrayList<Place>());
        rvSearchPlace.setAdapter(placeSearchAdapter);
    }

    public void updatePlaceData(List<Place> places) {
        placeSearchAdapter.update(places);
    }
}
