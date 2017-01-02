package com.iamwee.placesfinder.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.common.event.OpenActivity;
import com.iamwee.placesfinder.utilities.DialogHelper;

import org.greenrobot.eventbus.EventBus;


public class LoginFragment extends PlacesFinderFragment<LoginContractor.Presenter>
        implements LoginContractor.View, View.OnClickListener, DialogHelper.Callback {

    private TextInputEditText edtEmail;
    private TextInputEditText edtPassword;

    public LoginFragment() {

    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginPresenter.newInstance(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    public void onNetworkConnectionFailure() {
        Toast.makeText(getActivity(), "Check network connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initView(View rootView) {
        edtEmail = (TextInputEditText) rootView.findViewById(R.id.edt_email);
        edtPassword = (TextInputEditText) rootView.findViewById(R.id.edt_password);
    }

    @Override
    protected void setupView(View rootView) {
        rootView.findViewById(R.id.btn_login).setOnClickListener(this);
        rootView.findViewById(R.id.btn_login_as_guest).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_login) {
            presenter().login(edtEmail.getText().toString(),
                    edtPassword.getText().toString());
        } else if (id == R.id.btn_login_as_guest) {
            onLoginSuccess();
        }
    }

    @Override
    public void onShowProgressDialog() {
        DialogHelper.show(getActivity(), this);
    }

    @Override
    public void onDismissProgressDialog() {
        DialogHelper.dismiss();
    }

    @Override
    public void onLoginSuccess() {
        EventBus.getDefault().post(new OpenActivity(OpenActivity.MAIN_ACTIVITY));
    }

    @Override
    public void onLoginFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressDialogCancelled() {
        presenter().cancelCallLogin();
    }
}
