package com.iamwee.placesfinder.view.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.util.NetworkUtil;
import com.iamwee.placesfinder.util.SessionUtil;
import com.iamwee.placesfinder.view.info.PlaceInfoActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends PlacesFinderActivity
        implements TextWatcher, Callback<List<Place>> {

    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtSearch = (EditText) findViewById(R.id.edt_search);
        edtSearch.addTextChangedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, SearchFragment.newInstance(), "fragment_search")
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onOpenInfoPlaceActivity(Place place) {
        Intent intent = new Intent(this, PlaceInfoActivity.class);
        intent.putExtra("place", place);
        openActivity(intent, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(edtSearch.getText().toString().isEmpty()) return;
        if (NetworkUtil.isNetworkAvailable(this)) {
            RequestBody body = new FormBody.Builder()
                    .add("secret", SessionUtil.getSecretCode())
                    .add("token", SessionUtil.getToken())
                    .add("keyword", edtSearch.getText().toString())
                    .build();
            HttpManager.getServices().searchPlace(body).enqueue(this);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
        if (response.isSuccessful()) {
            SearchFragment searchFragment = (SearchFragment)
                    getSupportFragmentManager().findFragmentByTag("fragment_search");
            if (searchFragment != null) searchFragment.updatePlaceData(response.body());
        }
    }

    @Override
    public void onFailure(Call<List<Place>> call, Throwable t) {
        t.printStackTrace();
    }
}
