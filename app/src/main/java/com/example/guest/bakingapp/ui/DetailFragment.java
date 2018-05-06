package com.example.guest.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guest.bakingapp.App;
import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.adapters.StepsAdapter;
import com.example.guest.bakingapp.mvp.model.Ingredient;
import com.example.guest.bakingapp.mvp.model.Recipe;
import com.example.guest.bakingapp.mvp.model.Step;
import com.example.guest.bakingapp.utils.LikeButtonColorChanger;
import com.example.guest.bakingapp.utils.MakeIngredietsString;
import com.example.guest.bakingapp.utils.RxThreadManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Single;

import static com.example.guest.bakingapp.db.Provider.URI_INGREDIENTS;
import static com.example.guest.bakingapp.db.Provider.URI_RECIPE;
import static com.example.guest.bakingapp.db.Provider.URI_STEP;
import static com.example.guest.bakingapp.ui.DetailActivity.ID;

/**
 * Created by l1maginaire on 4/27/18.
 */

public class DetailFragment extends Fragment {
    private static final String TAG = DetailFragment.class.getSimpleName();

    @BindView(R.id.ingredients_tv)
    protected TextView ingredientsTv;
    @BindView(R.id.detail_recycler)
    protected RecyclerView recyclerView;
    @BindView(R.id.fab)
    protected FloatingActionButton fab;

    private Callbacks callbacks;
    private Recipe recipe;
    private StepsAdapter adapter;
    Unbinder unbinder;

    public static DetailFragment newInstance(Recipe recipe) {
        Bundle args = new Bundle();
        args.putParcelable(ID, recipe);
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
        unbinder = ButterKnife.bind(this, v);
        setView();
        setupAdapter();
        Single.fromCallable(() -> getActivity().getContentResolver().query(URI_INGREDIENTS, null, null,
                new String[]{String.valueOf(recipe.getId())}, null))
                .compose(RxThreadManager.manageSingle())
                .doOnError(throwable -> Log.e(TAG, "Something is wrong with App-class"))
                .subscribe(cursor -> {
                    List<Ingredient> ingredientList = new ArrayList<>();
                    if (cursor.getCount() > 0) {
                        cursor.moveToPosition(-1);
                        while (cursor.moveToNext()) {
                            Ingredient ingredient = new Ingredient();
                            ingredient.setIngredient(cursor.getString(cursor.getColumnIndexOrThrow(com.example.guest.bakingapp.db.model.Ingredient.COLUMN_QUANTITITY)));
                            ingredient.setMeasure(cursor.getString(cursor.getColumnIndexOrThrow(com.example.guest.bakingapp.db.model.Ingredient.COLUMN_MEASURE)));
                            ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(com.example.guest.bakingapp.db.model.Ingredient.COLUMN_QUANTITITY)));
                            ingredientList.add(ingredient);
                        }
                    }
                });
        Single.fromCallable(() -> getActivity().getContentResolver().query(URI_STEP, null, null,
                new String[]{String.valueOf(recipe.getId())}, null))
                .compose(RxThreadManager.manageSingle())
                .doOnError(throwable -> Log.e(TAG, "Something is wrong with App-class"))
                .subscribe(cursor -> {
                    List<Step> ingredientList = new ArrayList<>();
                    if (cursor.getCount() > 0) {
                        cursor.moveToPosition(-1);
                        while (cursor.moveToNext()) {
                            Step ingredient = new Step();
                            ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(com.example.guest.bakingapp.db.model.Step.COLUMN_ID)));
                            ingredient.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(com.example.guest.bakingapp.db.model.Step.COLUMN_DESCRIPTION)));
                            ingredient.setShortDescription(cursor.getString(cursor.getColumnIndexOrThrow(com.example.guest.bakingapp.db.model.Step.COLUMN_S_DESCRIPTION)));
                            ingredient.setThumbnailURL(cursor.getString(cursor.getColumnIndexOrThrow(com.example.guest.bakingapp.db.model.Step.COLUMN_VIDEO_URL)));
                            ingredient.setVideoURL(cursor.getString(cursor.getColumnIndexOrThrow(com.example.guest.bakingapp.db.model.Step.COLUMN_THUMB_URL)));
                            ingredientList.add(ingredient);
                        }
                    }
                });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setupAdapter() {
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        recyclerView.setLayoutParams(params);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new StepsAdapter(recipe.getSteps(), getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void setView() {
        fab.setOnClickListener(v -> callbacks.onLikeClicked(recipe, fab));
        String s = MakeIngredietsString.make(recipe.getIngredients());
        ingredientsTv.setText(s);
        LikeButtonColorChanger.change(fab, getActivity(), recipe.isFavorite());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipe = getArguments().getParcelable(ID);
    }

    public interface Callbacks {
        void onLikeClicked(Recipe recipe, FloatingActionButton fab);
    }
}
