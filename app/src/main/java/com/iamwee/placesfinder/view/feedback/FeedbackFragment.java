package com.iamwee.placesfinder.view.feedback;

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


public class FeedbackFragment extends PlacesFinderFragment<FeedbackContractor.Presenter>
        implements FeedbackContractor.View, ProgressDialogHelper.Callback {

    private TextInputEditText edtEmail;
    private TextInputEditText edtTitle;
    private TextInputEditText edtFeedback;

    public FeedbackFragment() {
    }

    public static FeedbackFragment newInstance() {
        Bundle args = new Bundle();
        FeedbackFragment fragment = new FeedbackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FeedbackPresenter.newInstance(this);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    protected void initView(View rootView) {
        edtEmail = (TextInputEditText) rootView.findViewById(R.id.edt_email);
        edtTitle = (TextInputEditText) rootView.findViewById(R.id.edt_title);
        edtFeedback = (TextInputEditText) rootView.findViewById(R.id.edt_feedback);
    }

    @Override
    protected void setupView(View rootView) {
        if (SessionUtil.hasLoggedIn()) {
            edtEmail.setText(SessionUtil.getUserProfile().getEmail());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onStop();
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
        onShowToastMessage(getString(R.string.error_check_internet_connection));
    }

    @Override
    public void onShowToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_send) {
            getPresenter().sendFeedback(
                    edtEmail.getText().toString(),
                    edtTitle.getText().toString(),
                    edtFeedback.getText().toString()
            );
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.send_action_menu, menu);
    }

    @Override
    public void onFeedbackSent(String message) {
        onShowToastMessage(message);
        EventBus.getDefault().post(new OpenActivity(OpenActivity.FINISH));
    }

    @Override
    public void onProgressDialogCancelled() {
        getPresenter().cancelCall();
    }
}
