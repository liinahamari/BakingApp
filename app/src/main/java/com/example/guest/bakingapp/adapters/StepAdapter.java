package com.example.guest.bakingapp.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.data.remote.pojo.StepRemote;
import com.example.guest.bakingapp.ui.StepFragment;

import java.util.List;
import java.util.Locale;

/**
 * Created by l1maginaire on 4/29/18.
 */

public class StepAdapter extends FragmentPagerAdapter {
    private List<StepRemote> stepList;
    private String title;

    public StepAdapter(FragmentManager fm, List<StepRemote> stepList, Context context) {
        super(fm);
        this.stepList = stepList;
        title = context.getResources().getString(R.string.step_tab_id);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return StepFragment.newInstance(stepList.get(position).getVideoURL(),
                stepList.get(position).getDescription(), stepList.get(position).getThumbnailURL());
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
