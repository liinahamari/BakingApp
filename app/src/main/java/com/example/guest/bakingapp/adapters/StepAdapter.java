package com.example.guest.bakingapp.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.example.guest.bakingapp.data.remote.pojo.StepRemote;
import com.example.guest.bakingapp.ui.StepFragment;

import java.util.List;
import java.util.Locale;

/**
Created by l1maginaire on 4/29/18.
*/

public class StepAdapter extends FragmentPagerAdapter {
    private List<StepRemote> stepList;
    private String title;

    public StepAdapter(FragmentManager fm, List<StepRemote> stepList) {
        super(fm);
        this.stepList = stepList;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return StepFragment.newInstance(stepList.get(position).getVideoURL(),
                stepList.get(position).getDescription());
    }

    @Override
    public int getCount() {
        return (stepList == null || stepList.size() < 1) ? 0 : stepList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.format(Locale.US, title, position);
    }
}
