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
import com.example.guest.bakingapp.data.remote.StepRemote;

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
    private List<StepRemote> stepRemoteList;
    @BindView(R.id.my_pager)
    protected ViewPager viewPager;
    private int position;
    Unbinder unbinder;

    public static Fragment newInstance(ArrayList<StepRemote> stepRemotes, int position) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ID, stepRemotes);
        args.putInt(POSITION, position);
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepRemoteList = getArguments().getParcelableArrayList(ID);
        position = getArguments().getInt(POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pager, container, false);
        unbinder = ButterKnife.bind(this, v);
        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                String video = stepRemoteList.get(i).getVideoURL();
                return StepFragment.newInstance(video, stepRemoteList.get(i).getDescription());
            }

            @Override
            public int getCount() {
                return (stepRemoteList == null || stepRemoteList.size() < 1) ? 0 : stepRemoteList.size();
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
