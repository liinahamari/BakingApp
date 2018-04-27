package com.example.guest.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.base.BaseFragment;
import com.example.guest.bakingapp.di.components.DaggerBakingComponent;
import com.example.guest.bakingapp.di.modules.BakingModule;
import com.example.guest.bakingapp.mvp.model.Reciep;
import com.example.guest.bakingapp.mvp.presenters.MainPresenter;
import com.example.guest.bakingapp.mvp.view.MainView;

import java.util.List;

import javax.inject.Inject;

import static com.example.guest.bakingapp.utils.NetworkChecker.isNetAvailable;

/**
 * Created by l1maginaire on 4/26/18.
 */

public class MainFragment extends BaseFragment implements MainView {
    @Inject
    protected MainPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.activity_main, container, false);
        loadNew();
        return v;
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

    }

    @Override
    public void onClearItems() {

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
}
