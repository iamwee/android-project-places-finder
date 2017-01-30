package com.iamwee.placesfinder.view.changepassword;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.util.ProgressDialogHelper;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zeon on 1/17/17.
 */

public class ChangePasswordFragment extends PlacesFinderFragment<ChangePasswordContractor.Presenter>
        implements ChangePasswordContractor.View, View.OnClickListener, ProgressDialogHelper.Callback {

    private TextInputEditText edtNewPassword;
    private TextInputEditText edtConfirmPassword;

    public ChangePasswordFragment() {

    }

    public static ChangePasswordFragment newInstance() {
        Bundle args = new Bundle();
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangePasswordPresenter.newInstance(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_change_password, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    protected void initView(View rootView) {
        edtNewPassword = (TextInputEditText) rootView.findViewById(R.id.edt_new_password);
        edtConfirmPassword = (TextInputEditText) rootView.findViewById(R.id.edt_confirm_password);
    }

    @Override
    protected void setupView(View rootView) {
        rootView.findViewById(R.id.btn_change_password).setOnClickListener(this);
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
    public void onNetworkConnectionFailure() {
        Toast.makeText(
                getActivity(),
                R.string.error_check_internet_connection,
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onShowToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_change_password) {
            getPresenter().changePassword(
                    edtNewPassword.getText().toString(),
                    edtConfirmPassword.getText().toString()
            );
        }
    }

    @Override
    public void onChangePasswordSuccess(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new OpenActivity(OpenActivity.FINISH));
    }

    @Override
    public void onChangePasswordFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressDialogCancelled() {
        getPresenter().cancelCall();
    }
}
