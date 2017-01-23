package com.iamwee.placesfinder.view.main.pager.recent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.util.PlaceUtil;


public class PlaceRecentFragment extends PlacesFinderFragment<RecentContractor.Presenter>
        implements RecentContractor.View {

    private RecyclerView rvRecentPlace;

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
        RecentPresenter.newInstance(this);
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
    }

    @Override
    protected void setupView(View rootView) {
        if (PlaceUtil.loadData().size() > 0) {
            rvRecentPlace.setAdapter(new PlaceRecentAdapter());
        } else {
            ViewStub viewStub = (ViewStub) rootView.findViewById(R.id.view_stub);
            viewStub.inflate();
        }
    }
}
