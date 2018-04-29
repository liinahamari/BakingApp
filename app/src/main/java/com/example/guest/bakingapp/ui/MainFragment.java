package com.example.guest.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.adapters.MainListAdapter;
import com.example.guest.bakingapp.base.BaseFragment;
import com.example.guest.bakingapp.di.components.DaggerBakingComponent;
import com.example.guest.bakingapp.di.modules.BakingModule;
import com.example.guest.bakingapp.mvp.model.Reciep;
import com.example.guest.bakingapp.mvp.presenters.MainPresenter;
import com.example.guest.bakingapp.mvp.view.MainView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.guest.bakingapp.utils.NetworkChecker.isNetAvailable;

/**
 * Created by l1maginaire on 4/26/18.
 */

public class MainFragment extends BaseFragment implements MainView {
    @Inject
    protected MainPresenter presenter;
    @BindView(R.id.main_recycler)
    protected RecyclerView recyclerView;

    private MainListAdapter adapter;
    private Callbacks callbacks;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callbacks = (Callbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement onItemClicked()");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);
        setupAdapter();
        loadNew();
        return v;
    }

    private void setupAdapter() {
        /*recyclerView.setHasFixedSize(true);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                || getResources().getBoolean(R.bool.isTab)) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }*/
//        cursorAdapter = new FavoritesAdapter(getActivity(), emptyFavoritesFrame, getLayoutInflater(), callbacks);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        adapter = new MainListAdapter(getActivity(), callbacks);
        recyclerView.setAdapter(adapter);
    }

    private void loadNew() {
        if (isNetAvailable(getActivity())) {
            presenter.getRecieps();
//            errorLayout.setVisibility(View.INVISIBLE);
        } else {
//            errorLayout.setVisibility(VISIBLE);
//            repeatButton.setOnClickListener(v -> loadNew());
        }
    }

    @Override
    public void onReciepsLoaded(List<Reciep> recieps) {
        onClearItems();
        adapter.addRecieps(recieps);
    }

    @Override
    public void onClearItems() {
        adapter.clearItems();
    }

    protected void resolveDaggerDependencies() {
        DaggerBakingComponent.builder()
                .applicationComponent(getApplicationComponent(getActivity()))
                .bakingModule(new BakingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        presenter.unsibscibe();
        super.onDestroyView();
    }

    public interface Callbacks {
        void onItemClicked(Reciep reciep, int position);
    }
}
