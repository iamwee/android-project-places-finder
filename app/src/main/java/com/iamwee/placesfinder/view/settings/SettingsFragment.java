package com.iamwee.placesfinder.view.settings;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;

import com.iamwee.placesfinder.R;

public class SettingsFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener {

    public SettingsFragment() {

    }

    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        Preference preference = findPreference("app_version");
        try {
            String appVersion = getActivity().getPackageManager().getPackageInfo(
                    getActivity().getPackageName(),
                    0
            ).versionName;

            preference.setSummary("v " + appVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            preference.setSummary("Version not found.");
        }
        preference.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        new AlertDialog.Builder(getActivity())
                .setTitle("About Places Finder")
                .setMessage("Developed by: iamwee\n\nhttps://github.com/iamwee")
                .setPositiveButton(android.R.string.ok, null)
                .show();
        return true;
    }
}
