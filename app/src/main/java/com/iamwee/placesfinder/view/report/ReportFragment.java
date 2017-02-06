package com.iamwee.placesfinder.view.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.util.ProgressDialogHelper;
import com.iamwee.placesfinder.util.SessionUtil;

import org.greenrobot.eventbus.EventBus;


public class ReportFragment extends PlacesFinderFragment<ReportContractor.Presenter>
        implements ReportContractor.View, ProgressDialogHelper.Callback {

    private TextInputEditText edtEmail;
    private TextInputEditText edtTitle;
    private TextInputEditText edtDescription;

    public ReportFragment() {
    }

    public static ReportFragment newInstance() {
        Bundle args = new Bundle();
        ReportFragment fragment = new ReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReportPresenter.newInstance(this);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    protected void initView(View rootView) {
        edtEmail = (TextInputEditText) rootView.findViewById(R.id.edt_email);
        edtTitle = (TextInputEditText) rootView.findViewById(R.id.edt_title);
        edtDescription = (TextInputEditText) rootView.findViewById(R.id.edt_description);
    }

    @Override
    protected void setupView(View rootView) {
        edtEmail.setText(SessionUtil.getUserProfile().getEmail());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_send) {
            getPresenter().sendReport(
                    edtEmail.getText().toString(),
                    edtTitle.getText().toString(),
                    edtDescription.getText().toString()
            );
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.send_action_menu, menu);
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
    public void onProgressDialogCancelled() {
        getPresenter().cancelCall();
    }

    @Override
    public void onReportSent() {
        EventBus.getDefault().post(new OpenActivity(OpenActivity.FINISH));
    }
}
