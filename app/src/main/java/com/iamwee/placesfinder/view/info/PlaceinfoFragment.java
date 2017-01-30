package com.iamwee.placesfinder.view.info;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.view.info.adapter.PlaceInfoAdapter;
import com.iamwee.placesfinder.view.info.adapter.model.BasePlaceInfoItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.List;

public class PlaceInfoFragment extends PlacesFinderFragment<PlaceInfoContractor.Presenter>
        implements PlaceInfoContractor.View {

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
        setHasOptionsMenu(true);
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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onSubmitPlace(OpenActivity event) {
        if (event.getStatus() == OpenActivity.SUBMIT_PLACE) {
            new AlertDialog.Builder(getActivity())
                    .setMessage("")
                    .setPositiveButton(R.string.action_submit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Place place = getArguments().getParcelable("place");
                            getPresenter().submitPlace(
                                    place.getId()
                            );
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
        }
    }

    @Override
    protected void initView(View rootView) {
        rvPlaceInfo = (RecyclerView) rootView.findViewById(R.id.rv_place_info);
        rvPlaceInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void setupView(View rootView) {
        Place place = getArguments().getParcelable("place");
        getPresenter().convertToAdapterModel(place);
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
    public void onSetAdapter(List<BasePlaceInfoItem> basePlaceInfoItems) {
        PlaceInfoAdapter placeInfoAdapter = new PlaceInfoAdapter(basePlaceInfoItems);
        rvPlaceInfo.setAdapter(placeInfoAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_info_place_menu, menu);
    }
}
