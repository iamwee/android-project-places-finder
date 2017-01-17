package com.iamwee.placesfinder.view.changepassword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iamwee.placesfinder.R;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ChangePasswordFragment.newInstance())
                    .commit();
        }
    }
}
