package com.iamwee.placesfinder.view.photo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.dao.Place;

public class PhotoListFragment extends PlacesFinderFragment {

    private RecyclerView rvPhotoList;

    public PhotoListFragment() {

    }

    public static PhotoListFragment newInstance(Place place) {
        Bundle args = new Bundle();
        args.putParcelable("place", place);
         PhotoListFragment fragment = new PhotoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo_list, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    protected void initView(View rootView) {
        rvPhotoList = (RecyclerView) rootView.findViewById(R.id.rv_photos);
        rvPhotoList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

    @Override
    protected void setupView(View rootView) {
        Place place = getArguments().getParcelable("place");
        rvPhotoList.setAdapter(new PhotoListAdapter(place.getImages()));
    }
}
