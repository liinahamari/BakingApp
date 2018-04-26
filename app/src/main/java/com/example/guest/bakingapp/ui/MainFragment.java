package com.example.guest.bakingapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guest.bakingapp.App;
import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.di.components.ApplicationComponent;
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

public class MainFragment extends Fragment implements MainView{
    @Inject
    protected MainPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.activity_main, container, false);
        presenter.getRecieps();
        return v;
    }

    private void loadNew() {
        if (isNetAvailable(getActivity())) {
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

    private ApplicationComponent getApplicationComponent(Activity activity) {
        return ((App) activity.getApplication()).getApplicationComponent();
    }
}
