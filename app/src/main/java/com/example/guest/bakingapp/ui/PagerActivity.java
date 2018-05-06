package com.example.guest.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.base.BaseActivity;
import com.example.guest.bakingapp.data.remote.StepRemote;

import java.util.ArrayList;

public class PagerActivity extends BaseActivity {
    public static final String ID = "steps_id";
    public static final String POSITION = "steps_pos";

    @Override
    protected int getContentView() {
        return R.layout.activity_fragment;
    }

    @Override
    protected Fragment getFragment() {
        return PagerFragment.newInstance(getIntent().getParcelableArrayListExtra(ID), getIntent().getIntExtra(POSITION, 0));
    }

    public static Intent newIntent(Context context, ArrayList<StepRemote> stepRemotes, int position) {
        Intent intent = new Intent(context, PagerActivity.class);
        intent.putParcelableArrayListExtra(ID, stepRemotes);
        intent.putExtra(POSITION, position);
        return intent;
    }
}
