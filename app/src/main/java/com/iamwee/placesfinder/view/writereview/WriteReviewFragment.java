package com.iamwee.placesfinder.view.writereview;

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
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.util.ProgressDialogHelper;
import com.iamwee.placesfinder.util.SessionUtil;

import org.greenrobot.eventbus.EventBus;


public class WriteReviewFragment extends PlacesFinderFragment<WriteReviewContractor.Presenter>
        implements WriteReviewContractor.View, ProgressDialogHelper.Callback {

    private TextInputEditText edtCodeName, edtReview;
    private Place place;

    public WriteReviewFragment() {

    }

    public static WriteReviewFragment newInstance(Place place) {
        Bundle args = new Bundle();
        args.putParcelable("place", place);
        WriteReviewFragment fragment = new WriteReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WriteReviewPresenter.newInstance(this);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write_review, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    protected void initView(View rootView) {
        edtCodeName = (TextInputEditText) rootView.findViewById(R.id.edt_code_name);
        edtReview = (TextInputEditText) rootView.findViewById(R.id.edt_review);
    }

    @Override
    protected void setupView(View rootView) {
        edtCodeName.setText(SessionUtil.getUserProfile().getCodeName());
        place = getArguments().getParcelable("place");
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
            getPresenter().submitReview(place.getId(), edtReview.getText().toString());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.send_action_menu, menu);
    }

    @Override
    public void onReviewSubmitted(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new OpenActivity(OpenActivity.FINISH));
    }

    @Override
    public void onProgressDialogCancelled() {
        getPresenter().cancelCall();
    }
}
