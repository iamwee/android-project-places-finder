package com.iamwee.placesfinder.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.util.ProgressDialogHelper;
import com.iamwee.placesfinder.util.SessionUtil;

import org.greenrobot.eventbus.EventBus;


public class LoginFragment extends PlacesFinderFragment<LoginContractor.Presenter>
        implements LoginContractor.View, View.OnClickListener,
        ProgressDialogHelper.Callback, TextView.OnEditorActionListener {

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
        Toast.makeText(
                getActivity(),
                getString(R.string.error_check_connection),
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void onShowToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initView(View rootView) {
        edtEmail = (TextInputEditText) rootView.findViewById(R.id.edt_email);
        edtPassword = (TextInputEditText) rootView.findViewById(R.id.edt_password);
    }

    @Override
    protected void setupView(View rootView) {
        edtEmail.setText(SessionUtil.getEmailThatLoggedIn());
        edtPassword.setOnEditorActionListener(this);
        rootView.findViewById(R.id.btn_login).setOnClickListener(this);
        rootView.findViewById(R.id.btn_login_as_guest).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_login) {
            getPresenter().login(edtEmail.getText().toString(),
                    edtPassword.getText().toString());
        } else if (id == R.id.btn_login_as_guest) {
            onLoginSuccess();
        }
    }

    @Override
    public void onServiceExecuting() {
        ProgressDialogHelper.show(getActivity(), this);
    }

    @Override
    public void onServicePostExecute() {
        ProgressDialogHelper.dismiss();
    }

    @Override
    public void onLoginSuccess() {
        SessionUtil.saveEmailThatLoggedIn(edtEmail.getText().toString());
        EventBus.getDefault().post(new OpenActivity(OpenActivity.MAIN_ACTIVITY, true, false));
    }

    @Override
    public void onLoginAsGuest() {
        EventBus.getDefault().post(new OpenActivity(OpenActivity.MAIN_ACTIVITY, true, true));
    }

    @Override
    public void onLoginFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressDialogCancelled() {
        getPresenter().cancelCallLogin();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            getPresenter().login(edtEmail.getText().toString(),
                    edtPassword.getText().toString());
        }
        return false;
    }
}
