package com.example.guest.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.base.BaseActivity;
import com.example.guest.bakingapp.mvp.model.Reciep;

/**
 * Created by l1maginaire on 4/27/18.
 */

public class DetailActivity extends BaseActivity/* implements DetailFragment.Callbacks*/{
    public static final String ID = "id";

    public static Intent newIntent(Context context, Reciep reciep) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(ID, reciep);
        return intent;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_fragment;
    }

    @Override
    protected Fragment getFragment() {
        return DetailFragment.newInstance(getIntent().getParcelableExtra(ID));
    }
}
