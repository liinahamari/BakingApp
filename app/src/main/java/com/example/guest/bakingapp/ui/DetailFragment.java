package com.example.guest.bakingapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.adapters.StepsAdapter;
import com.example.guest.bakingapp.data.Repository;
import com.example.guest.bakingapp.data.local.LocalDataSource;
import com.example.guest.bakingapp.data.remote.pojo.RecipeRemote;
import com.example.guest.bakingapp.utils.LikeButtonColorChanger;
import com.example.guest.bakingapp.utils.MakeIngredietsString;
import com.example.guest.bakingapp.utils.RxThreadManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Single;

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
    Unbinder unbinder;

    public static DetailFragment newInstance(int recipeId) {
        Bundle args = new Bundle();
        args.putInt(ID, recipeId);
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
        /*Single.fromCallable(() -> getActivity().getContentResolver().query(URI_INGREDIENTS, null, null,
                new String[]{String.valueOf(recipeId.getId())}, null))
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
                new String[]{String.valueOf(recipeId.getId())}, null))
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

        Single.fromCallable(() -> getActivity().getContentResolver().query(URI_RECIPE, null, null,
                new String[]{String.valueOf(recipeId.getId())}, null))
                .compose(RxThreadManager.manageSingle())
                .doOnError(throwable -> Log.e(TAG, "Something is wrong with App-class"))
                .subscribe(cursor -> {
                    List<RecipeRemote> ingredientList = new ArrayList<>();
                    cursor.moveToPosition(-1);
                    while (cursor.moveToNext()) {
                        RecipeRemote ingredient = new RecipeRemote();
                        ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(RecipeLocal.COLUMN_RECIPE_ID)));
                        ingredient.setImage(cursor.getString(cursor.getColumnIndexOrThrow(RecipeLocal.COLUMN_IMAGE)));
                        ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow(RecipeLocal.COLUMN_NAME)));
                        ingredient.setServings(cursor.getInt(cursor.getColumnIndexOrThrow(RecipeLocal.COLUMN_SERVINGS)));
                        ingredientList.add(ingredient);
                    }
                });*/
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
        StepsAdapter adapter = new StepsAdapter(recipeRemote.getStepRemotes(), getActivity());
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("CheckResult")
    private void setView() {
        fab.setOnClickListener(v -> callbacks.onLikeClicked(fab, recipeRemote));
        ingredientsTv.setText(MakeIngredietsString.make(recipeRemote.getIngredientRemotes()));
        Single.fromCallable(() -> LocalDataSource.isFavorite(getActivity(), recipeRemote.getId()))
                .compose(RxThreadManager.manageSingle())
                .subscribe(isFavorite -> LikeButtonColorChanger.change(fab, getActivity(), isFavorite));
        if (getActivity() != null && getActivity().getLocalClassName().equals("com.example.guest.bakingapp.ui.MainActivity")) //twopane mode detector
            ((MainActivity) getActivity()).setFab(fab);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeRemote = Repository.get().getRecipe(getArguments().getInt(ID));
    }

    public interface Callbacks {
        void onLikeClicked(FloatingActionButton fab, RecipeRemote  recipe);
    }
}
