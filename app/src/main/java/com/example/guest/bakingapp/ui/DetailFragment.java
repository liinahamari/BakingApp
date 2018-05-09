package com.example.guest.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.adapters.StepsAdapter;
import com.example.guest.bakingapp.data.local.pojo.IngredientLocal;
import com.example.guest.bakingapp.data.local.pojo.StepLocal;
import com.example.guest.bakingapp.data.remote.pojo.IngredientRemote;
import com.example.guest.bakingapp.data.remote.pojo.RecipeRemote;
import com.example.guest.bakingapp.data.remote.pojo.StepRemote;
import com.example.guest.bakingapp.utils.LikeButtonColorChanger;
import com.example.guest.bakingapp.utils.MakeIngredietsString;
import com.example.guest.bakingapp.utils.RxThreadManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Single;

import static com.example.guest.bakingapp.data.local.Provider.URI_INGREDIENTS;
import static com.example.guest.bakingapp.data.local.Provider.URI_STEP;
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
    private RecipeRemote recipeRemote;
    private StepsAdapter adapter;
    Unbinder unbinder;

    public static DetailFragment newInstance(RecipeRemote recipeRemote) {
        Bundle args = new Bundle();
        args.putParcelable(ID, recipeRemote);
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
                new String[]{String.valueOf(recipeRemote.getId())}, null))
                .compose(RxThreadManager.manageSingle())
                .doOnError(throwable -> Log.e(TAG, "Something is wrong with App-class"))
                .subscribe(cursor -> {
                    List<IngredientRemote> ingredientRemoteList = new ArrayList<>();
                    if (cursor.getCount() > 0) {
                        cursor.moveToPosition(-1);
                        while (cursor.moveToNext()) {
                            IngredientRemote ingredientRemote = new IngredientRemote();
                            ingredientRemote.setIngredient(cursor.getString(cursor.getColumnIndexOrThrow(IngredientLocal.COLUMN_QUANTITITY)));
                            ingredientRemote.setMeasure(cursor.getString(cursor.getColumnIndexOrThrow(IngredientLocal.COLUMN_MEASURE)));
                            ingredientRemote.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(IngredientLocal.COLUMN_QUANTITITY)));
                            ingredientRemoteList.add(ingredientRemote);
                        }
                    }
                });
        Single.fromCallable(() -> getActivity().getContentResolver().query(URI_STEP, null, null,
                new String[]{String.valueOf(recipeRemote.getId())}, null))
                .compose(RxThreadManager.manageSingle())
                .doOnError(throwable -> Log.e(TAG, "Something is wrong with App-class"))
                .subscribe(cursor -> {
                    List<StepRemote> ingredientList = new ArrayList<>();
                    if (cursor.getCount() > 0) {
                        cursor.moveToPosition(-1);
                        while (cursor.moveToNext()) {
                            StepRemote ingredient = new StepRemote();
                            ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(StepLocal.COLUMN_ID)));
                            ingredient.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(StepLocal.COLUMN_DESCRIPTION)));
                            ingredient.setShortDescription(cursor.getString(cursor.getColumnIndexOrThrow(StepLocal.COLUMN_S_DESCRIPTION)));
                            ingredient.setThumbnailURL(cursor.getString(cursor.getColumnIndexOrThrow(StepLocal.COLUMN_VIDEO_URL)));
                            ingredient.setVideoURL(cursor.getString(cursor.getColumnIndexOrThrow(StepLocal.COLUMN_THUMB_URL)));
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
        adapter = new StepsAdapter(recipeRemote, getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void setView() {
        fab.setOnClickListener(v -> callbacks.onLikeClicked(recipeRemote, fab));
        String s = MakeIngredietsString.make(recipeRemote.getIngredientRemotes());
        ingredientsTv.setText(s);
        LikeButtonColorChanger.change(fab, getActivity(), recipeRemote.isFavorite());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeRemote = getArguments().getParcelable(ID);
    }

    public interface Callbacks {
        void onLikeClicked(RecipeRemote recipeRemote, FloatingActionButton fab);
    }
}
