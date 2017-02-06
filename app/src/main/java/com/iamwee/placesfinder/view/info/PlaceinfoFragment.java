package com.iamwee.placesfinder.view.info;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.iamwee.placesfinder.event.SwipeRefresh;
import com.iamwee.placesfinder.util.ProgressDialogHelper;
import com.iamwee.placesfinder.view.info.adapter.PlaceInfoAdapter;
import com.iamwee.placesfinder.view.info.adapter.model.BasePlaceInfoItem;
import com.iamwee.placesfinder.view.suggest.choosephoto.ChoosePhotoActivity;
import com.iamwee.placesfinder.widget.PlaceView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class PlaceInfoFragment extends PlacesFinderFragment<PlaceInfoContractor.Presenter>
        implements PlaceInfoContractor.View, SwipeRefreshLayout.OnRefreshListener, ProgressDialogHelper.Callback {

    private static final int CHOOSE_PHOTO = 1;
    private RecyclerView rvPlaceInfo;
    private PlaceInfoAdapter placeInfoAdapter;

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
    public void onRefresh(SwipeRefresh refresh) {
        if (refresh.getStatus() == SwipeRefresh.REFRESH) {
            Place place = getArguments().getParcelable("place");
            getPresenter().getPlaceById(place.getId());
        }
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
        } else if (event.getStatus() == OpenActivity.CHOOSE_PHOTO) {
            startActivityForResult(new Intent(getActivity(), ChoosePhotoActivity.class), CHOOSE_PHOTO);
        }
    }

    @Override
    protected void initView(View rootView) {
        rvPlaceInfo = (RecyclerView) rootView.findViewById(R.id.rv_place_info);
        rvPlaceInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void setupView(View rootView) {
        placeInfoAdapter = new PlaceInfoAdapter(new ArrayList<BasePlaceInfoItem>());
        rvPlaceInfo.setAdapter(placeInfoAdapter);
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
        placeInfoAdapter.update(basePlaceInfoItems);
    }

    @Override
    public void onRefreshed() {
        EventBus.getDefault().post(new SwipeRefresh(SwipeRefresh.DISMISS));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_report) {
            Place place = getArguments().getParcelable("place");
            EventBus.getDefault().post(place);
        } else if (id == R.id.action_share) {
            //A simple share text data to other app, not use facebook sdk
            Place place = getArguments().getParcelable("place");
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    place.toString());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_info_place_menu, menu);
    }

    @Override
    public void onRefresh() {
        Place place = getArguments().getParcelable("place");
        getPresenter().getPlaceById(place.getId());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PHOTO && resultCode == Activity.RESULT_OK) {
            PlaceView placeView = new PlaceView(getActivity());
            placeView.setImageUrl(data.getStringExtra("image_path"));
            placeView.setName("");
            placeView.setAddress("");
            new AlertDialog.Builder(getActivity())
                    .setView(placeView)
                    .setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Place place = getArguments().getParcelable("place");
                            getPresenter().uploadImage(
                                    data.getStringExtra("image_path"),
                                    place
                            );
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
        }
    }

    @Override
    public void onExecuting() {
        ProgressDialogHelper.show(getActivity(), this);
    }

    @Override
    public void onPostExecute() {
        ProgressDialogHelper.dismiss();
    }

    @Override
    public void onProgressDialogCancelled() {
        getPresenter().cancelCall();
    }
}
