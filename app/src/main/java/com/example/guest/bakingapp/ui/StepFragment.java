package com.example.guest.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.mvp.model.Step;

/**
 * Created by l1maginaire on 4/29/18.
 */

public class StepFragment extends Fragment {
    public static final String ID = "single_step_id";
    private Step step;

    public static Fragment newInstance(Step step) {
        Bundle args = new Bundle();
        args.putParcelable(ID, step);
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        step = getArguments().getParcelable(ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pager, container, false);
        return v;
    }
}
