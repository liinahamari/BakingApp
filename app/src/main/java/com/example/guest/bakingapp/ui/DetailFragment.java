package com.example.guest.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.adapters.MainListAdapter;
import com.example.guest.bakingapp.adapters.StepsAdapter;
import com.example.guest.bakingapp.mvp.model.Reciep;
import com.example.guest.bakingapp.utils.LikeButtonColorChanger;
import com.example.guest.bakingapp.utils.MakeIngredietsString;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.guest.bakingapp.ui.DetailActivity.ID;

/**
 * Created by l1maginaire on 4/27/18.
 */

public class DetailFragment extends Fragment {
    @BindView(R.id.ingredients_tv)
    protected TextView ingredientsTv;
    @BindView(R.id.detail_recycler)
    protected RecyclerView recyclerView;
    @BindView(R.id.fab)
    protected FloatingActionButton fab;

    private Callbacks callbacks;
    private Reciep reciep;
    private StepsAdapter adapter;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, v);
        setView();
        setupAdapter();
        return v;
    }

    private void setupAdapter() {
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        recyclerView.setLayoutParams(params);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new StepsAdapter(reciep.getSteps(), getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void setView() {
        fab.setOnClickListener(v -> callbacks.onLikeClicked(reciep, fab));
        String s = MakeIngredietsString.make(reciep.getIngredients());
        ingredientsTv.setText(s);
        LikeButtonColorChanger.change(fab, getActivity(), reciep.isFavorite());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reciep = getArguments().getParcelable(ID);
    }

    public interface Callbacks {
        void onLikeClicked(Reciep reciep, FloatingActionButton fab);
    }
}
