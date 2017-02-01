package com.iamwee.placesfinder.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.util.SessionUtil;
import com.iamwee.placesfinder.view.SettingsActivity;
import com.iamwee.placesfinder.view.feedback.FeedbackActivity;
import com.iamwee.placesfinder.view.info.PlaceInfoActivity;
import com.iamwee.placesfinder.view.login.LoginActivity;
import com.iamwee.placesfinder.view.logout.LogoutActivity;
import com.iamwee.placesfinder.view.profile.ProfileActivity;
import com.iamwee.placesfinder.view.search.SearchActivity;
import com.iamwee.placesfinder.view.suggest.SuggestPlaceActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class MainActivity extends PlacesFinderActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, MainFragment.newInstance())
                    .commit();
        }
    }

    @Override
    protected void setupView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_logged_in);
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            openActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_search) {
            openActivity(new Intent(this, SearchActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            if (SessionUtil.hasLoggedIn()) {
                openActivity(new Intent(this, ProfileActivity.class));
            } else {
                openActivity(new Intent(this, LoginActivity.class), true);
            }
        } else if (id == R.id.nav_suggest_place) {
            if (SessionUtil.hasLoggedIn()) {
                openActivity(new Intent(this, SuggestPlaceActivity.class));
            } else {
                openActivity(new Intent(this, LoginActivity.class), true);
            }
        } else if (id == R.id.nav_settings) {
            openActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_logout) {
            openActivity(new Intent(this, LogoutActivity.class), true);
        } else if (id == R.id.nav_send_feed_back){
            openActivity(new Intent(this, FeedbackActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Subscribe
    public void onOpenPlaceInfo(Place place) {
        Intent intent = new Intent(this, PlaceInfoActivity.class);
        intent.putExtra("place", place);
        openActivity(intent);
    }

}
