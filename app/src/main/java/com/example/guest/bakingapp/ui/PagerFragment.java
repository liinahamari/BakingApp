package com.example.guest.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.adapters.StepAdapter;
import com.example.guest.bakingapp.data.remote.pojo.StepRemote;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.guest.bakingapp.ui.PagerActivity.ID;
import static com.example.guest.bakingapp.ui.PagerActivity.POSITION;

/**
 * Created by l1maginaire on 4/29/18.
 */

public class PagerFragment extends Fragment {
    @BindView(R.id.recipe_step_viewpager)
    protected ViewPager viewPager;
    @BindView(R.id.recipe_step_tablayout)
    TabLayout tabLayout;

    private int position;//todo what for?
    private List<StepRemote> stepRemoteList;
    Unbinder unbinder;
    private StepAdapter stepAdapter;

    public static Fragment newInstance(int recipeId, int position) {
        Bundle args = new Bundle();
        args.putInt(ID, recipeId);
        args.putInt(POSITION, position);
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepRemoteList = Repository.get().getSteps(getArguments().getInt(ID));
        position = getArguments().getInt(POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pager, container, false);
        unbinder = ButterKnife.bind(this, v);
        stepAdapter = new StepAdapter(getFragmentManager(), stepRemoteList, getActivity());
        viewPager.setAdapter(stepAdapter);
        setUpViewPagerListener();
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(position);
        return v;
    }

    private void setUpViewPagerListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int i) {
                position = i;
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
