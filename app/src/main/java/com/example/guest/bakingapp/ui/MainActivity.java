package com.example.guest.bakingapp.ui;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_fragment;
    }

    @Override
    protected Fragment getFragment() {
        return new MainFragment();
    }
}
