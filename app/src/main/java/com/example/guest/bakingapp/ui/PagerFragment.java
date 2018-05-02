package com.example.guest.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.mvp.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.guest.bakingapp.ui.PagerActivity.ID;
import static com.example.guest.bakingapp.ui.PagerActivity.POSITION;

/**
 * Created by l1maginaire on 4/29/18.
 */

public class PagerFragment extends Fragment {
    private List<Step> stepList;
    @BindView(R.id.my_pager)
    protected ViewPager viewPager;
    private int position;

    public static Fragment newInstance(ArrayList<Step> steps, int position) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ID, steps);
        args.putInt(POSITION, position);
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepList = getArguments().getParcelableArrayList(ID);
        position = getArguments().getInt(POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pager, container, false);
        ButterKnife.bind(this, v);
        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                String video = stepList.get(i).getVideoURL();
                return StepFragment.newInstance(video, stepList.get(i).getDescription());
            }

            @Override
            public int getCount() {
                return (stepList == null || stepList.size() < 1) ? 0 : stepList.size();
            }
        });
        return v;
    }
}
