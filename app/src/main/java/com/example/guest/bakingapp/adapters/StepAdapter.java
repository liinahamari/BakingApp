/*
package com.example.guest.bakingapp.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.guest.bakingapp.mvp.model.Step;

import java.util.List;

*/
/**
 * Created by l1maginaire on 4/29/18.
 *//*


public class StepAdapter extends FragmentStatePagerAdapter {
    private List<Step> stepList;

    public StepsAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<Step> stepList) {
        this.stepList = stepList;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putString(REVIEW_DATA, stepList.get(position).getContent());
        args.putString(REVIEW_AUTHOR, stepList.get(position).getAuthor());
        ReviewFragment fragment = new ReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return (stepList == null || stepList.size() < 1) ? 0 : stepList.size();
    }
}*/
