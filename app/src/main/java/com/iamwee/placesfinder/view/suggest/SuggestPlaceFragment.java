package com.iamwee.placesfinder.view.suggest;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.dao.PlaceType;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.util.LocationUtil;
import com.iamwee.placesfinder.util.ProgressDialogHelper;
import com.iamwee.placesfinder.view.suggest.chooselocation.ChooseLocationActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SuggestPlaceFragment extends PlacesFinderFragment<SuggestPlaceContractor.Presenter>
        implements SuggestPlaceContractor.View, View.OnClickListener,
        View.OnFocusChangeListener, ProgressDialogHelper.Callback {

    private LatLng latLng;
    private String placeType = "";

    private TextView tvLocation;
    private TextInputEditText edtName;
    private TextInputEditText edtType;
    private TextInputEditText edtAddress;
    private TextInputEditText edtDetail;

    public SuggestPlaceFragment() {
    }

    public static SuggestPlaceFragment newInstance() {
        Bundle args = new Bundle();
        SuggestPlaceFragment fragment = new SuggestPlaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SuggestPlacePresenter.newInstance(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_suggest_place, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    protected void initView(View rootView) {
        tvLocation = (TextView) rootView.findViewById(R.id.tv_location);
        edtName = (TextInputEditText) rootView.findViewById(R.id.edt_place_name);
        edtAddress = (TextInputEditText) rootView.findViewById(R.id.edt_place_address);
        edtType = (TextInputEditText) rootView.findViewById(R.id.edt_place_type);
        edtDetail = (TextInputEditText) rootView.findViewById(R.id.edt_place_detail);
    }

    @Override
    protected void setupView(View rootView) {
        latLng = LocationUtil.getLastLocation();
        tvLocation.setText("Location: " + latLng.latitude + "," + latLng.longitude);
        edtAddress.setText(LocationUtil.getAddress());

        rootView.findViewById(R.id.btn_get_location).setOnClickListener(this);
        rootView.findViewById(R.id.btn_place_submit).setOnClickListener(this);
        edtType.setOnClickListener(this);
        edtType.setOnFocusChangeListener(this);
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
        outState.putBundle("presenter", getPresenter().onSaveInstanceState());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
            getPresenter().onRestoreInstanceState(savedInstanceState.getBundle("presenter"));
    }

    @Override
    public void onNetworkConnectionFailure() {
        Toast.makeText(getActivity(), R.string.error_check_internet_connection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            edtAddress.setText(data.getStringExtra("address"));
            String location = "Location: "
                    + data.getDoubleExtra("lat", 0.0)
                    + ","
                    + data.getDoubleExtra("lng", 0.0);
            latLng = new LatLng(data.getDoubleExtra("lat", 0.0), data.getDoubleExtra("lng", 0.0));
            tvLocation.setText(location);
        }
    }

    private String getPlaceAddressText() {
        return edtAddress.getText().toString();
    }

    private String getPlaceNameText() {
        return edtName.getText().toString();
    }

    private String getPlaceDetailText() {
        return edtDetail.getText().toString();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.edt_place_type) {
            getPresenter().getTypeOfPlace();
        } else if (id == R.id.btn_place_submit) {
            getPresenter().submitPlace(
                    getPlaceNameText(),
                    latLng,
                    placeType,
                    getPlaceAddressText(),
                    getPlaceDetailText()
            );
        } else if (id == R.id.btn_get_location) {
            startActivityForResult(new Intent(getActivity(), ChooseLocationActivity.class), 1);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.edt_place_type && hasFocus) {
            getPresenter().getTypeOfPlace();
        }
    }

    @Override
    public void onPostGetPlaceType(final List<PlaceType> placeTypes) {
        ArrayList<CharSequence> types = new ArrayList<>();
        for (PlaceType placetype : placeTypes) {
            types.add(placetype.getTypeName());
        }

        new AlertDialog.Builder(getActivity())
                .setItems(types.toArray(
                        new CharSequence[types.size()]),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                placeType = placeTypes.get(which).getId();
                                edtType.setText(placeTypes.get(which).getTypeName());
                            }
                        }).show();
    }

    @Override
    public void onPlaceSubmitted() {
        Toast.makeText(getActivity(), "Place has submitted.", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new OpenActivity(OpenActivity.FINISH));
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
