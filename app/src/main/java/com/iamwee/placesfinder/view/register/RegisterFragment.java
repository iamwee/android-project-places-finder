package com.iamwee.placesfinder.view.register;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.utilities.DialogHelper;

import org.greenrobot.eventbus.EventBus;


public class RegisterFragment extends PlacesFinderFragment<RegisterContractor.Presenter>
        implements RegisterContractor.View,
        View.OnClickListener,
        DialogHelper.Callback {

    private TextInputEditText edtEmail;
    private TextInputEditText edtPassword;
    private TextInputEditText edtConfirmPassword;
    private TextInputEditText edtCodeName;

    public RegisterFragment() {

    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RegisterPresenter.newInstance(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    public void onNetworkConnectionFailure() {

    }

    @Override
    public void onShowToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServiceExecuting() {
        DialogHelper.show(getActivity(), this);
    }

    @Override
    public void onServicePostExecute() {
        DialogHelper.dismiss();
    }

    @Override
    protected void initView(View rootView) {
        edtEmail = (TextInputEditText) rootView.findViewById(R.id.edt_email);
        edtPassword = (TextInputEditText) rootView.findViewById(R.id.edt_password);
        edtConfirmPassword = (TextInputEditText) rootView.findViewById(R.id.edt_confirm_password);
        edtCodeName = (TextInputEditText) rootView.findViewById(R.id.edt_code_name);
    }

    @Override
    protected void setupView(View rootView) {
        rootView.findViewById(R.id.btn_back_to_login).setOnClickListener(this);
        rootView.findViewById(R.id.btn_create_account).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back_to_login) {
            EventBus.getDefault().post(new OpenActivity(OpenActivity.LOGIN_ACTIVITY));
        } else if (id == R.id.btn_create_account) {
            getPresenter().createAccount(getEmailText(),
                    getPasswordText(),
                    getConfirmPasswordText(),
                    getCodeNameText());
        }
    }

    public String getEmailText() {
        return edtEmail.getText().toString();
    }

    public String getPasswordText() {
        return edtPassword.getText().toString();
    }

    public String getConfirmPasswordText() {
        return edtConfirmPassword.getText().toString();
    }

    public String getCodeNameText() {
        return edtCodeName.getText().toString();
    }

    @Override
    public void onProgressDialogCancelled() {
        getPresenter().cancelCall();
    }

    @Override
    public void onCreateAccountSuccess() {
        new AlertDialog.Builder(getActivity())
                .setMessage("Account has created")
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EventBus.getDefault().post(new OpenActivity(OpenActivity.LOGIN_ACTIVITY));
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        EventBus.getDefault().post(new OpenActivity(OpenActivity.LOGIN_ACTIVITY));
                    }
                })
                .show();
    }
}
