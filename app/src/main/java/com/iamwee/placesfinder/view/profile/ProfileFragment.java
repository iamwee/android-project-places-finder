package com.iamwee.placesfinder.view.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.common.event.OpenActivity;
import com.iamwee.placesfinder.utilities.DialogHelper;

import org.greenrobot.eventbus.EventBus;


public class ProfileFragment extends PlacesFinderFragment<ProfileContractor.Presenter>
        implements ProfileContractor.View, DialogHelper.Callback, View.OnClickListener {

    private EditText edtCodeName;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProfilePresenter.newInstance(this);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @Override
    public void onNetworkConnectionFailure() {

    }

    @Override
    public void onShowToastMessage(String message) {

    }

    @Override
    protected void initView(View rootView) {
        edtCodeName = (EditText) rootView.findViewById(R.id.edt_code_name);
    }

    @Override
    protected void setupView(View rootView) {
        rootView.findViewById(R.id.btn_change_password).setOnClickListener(this);
    }

    @Override
    public void onProfileSaved(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new OpenActivity(OpenActivity.FINISH));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save_profile) {
            getPresenter().saveProfile(getCodeNameText());
        }
        return super.onOptionsItemSelected(item);
    }

    private String getCodeNameText() {
        return edtCodeName.getText().toString();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_profile_menu, menu);
    }

    @Override
    public void onExecuting() {
        DialogHelper.show(getActivity(), this);
    }

    @Override
    public void onPostExecute() {
        DialogHelper.dismiss();
    }

    @Override
    public void onProgressDialogCancelled() {
        getPresenter().cancelCall();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_change_password) {
            EventBus.getDefault().post(new OpenActivity(OpenActivity.CHANGE_PASSWORD));
        }
    }
}
