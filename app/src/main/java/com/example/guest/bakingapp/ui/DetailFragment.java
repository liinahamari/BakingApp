package com.example.guest.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import com.example.guest.bakingapp.mvp.model.Reciep;

import static com.example.guest.bakingapp.ui.DetailActivity.ID;

/**
 * Created by l1maginaire on 4/27/18.
 */

public class DetailFragment extends Fragment {
    private Callbacks callbacks;
    private Reciep reciep;

    public static DetailFragment newInstance(Reciep reciep) {
        Bundle args = new Bundle();
        args.putParcelable(ID, reciep);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callbacks = (DetailFragment.Callbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement onLikeClicked()");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reciep = getArguments().getParcelable(ID);
    }

    public interface Callbacks {
        void onLikeClicked(Reciep reciep, FloatingActionButton floatingButton);
    }
}
