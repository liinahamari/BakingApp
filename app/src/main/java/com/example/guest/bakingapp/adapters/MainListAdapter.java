package com.example.guest.bakingapp.adapters;

/**
 * Created by l1maginaire on 4/26/18.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.data.local.LocalDataSource;
import com.example.guest.bakingapp.data.remote.pojo.RecipeRemote;
import com.example.guest.bakingapp.ui.MainFragment;
import com.example.guest.bakingapp.utils.LikeButtonColorChanger;
import com.example.guest.bakingapp.utils.RxThreadManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
    private List<RecipeRemote> recipes;
    private Context context;
    private FloatingActionButton fab;
    private MainFragment.Callbacks callbacks;
    private int bindedDetailContainerPosition = -1;
    private CompositeDisposable compositeDisposable;

    public MainListAdapter(Context context, MainFragment.Callbacks callbacks) {
        compositeDisposable = new CompositeDisposable();
        recipes = new ArrayList<>(0);
        this.callbacks = callbacks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_lists_single_item, parent, false);
        return new ViewHolder(v);
    }

    public void addRecieps(List<RecipeRemote> recipes) {
        this.recipes.addAll(recipes);
        notifyDataSetChanged();
    }

    public void setFab(FloatingActionButton fab, int position) {
        this.bindedDetailContainerPosition = position;
        this.fab = fab;
    }

    public void clearItems() {
        recipes.clear();
        notifyDataSetChanged();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeRemote recipe = recipes.get(position);
        holder.title.setText(recipe.getName());
        String steps = "Steps: " + String.valueOf(recipe.getStepRemotes().size());
        holder.steps.setText(steps);
        String servings = "Servings: " + String.valueOf(recipe.getServings());
        holder.servings.setText(servings);
        if(recipe.getImage()!=null && !recipe.getImage().isEmpty()){
            holder.recipeImage.setVisibility(View.VISIBLE);
            Picasso.with(context).load(recipe.getImage()).into(holder.recipeImage);
        }
        holder.title.setOnClickListener(v -> callbacks.onItemClicked(recipe.getId(), position));
        holder.favIcon.setOnClickListener(v ->
        {
            holder.favIcon.setClickable(false);
            compositeDisposable.add(Single.fromCallable(() -> LocalDataSource.isFavorite(context, recipe.getId()))
                    .observeOn(Schedulers.io())
                    .flatMap(isFavorite -> {
                        if (isFavorite) {
                            return Single.fromCallable(() -> LocalDataSource.delete(recipe.getId(), context));
                        } else {
                            return Single.fromCallable(() -> LocalDataSource.insert(recipe, context));
                        }
                    })
                    .compose(RxThreadManager.manageSingle())
                    .subscribe(isFavorite -> {
                        if (fab != null && this.bindedDetailContainerPosition == position) {
                            LikeButtonColorChanger.change(fab, context, isFavorite);
                        }
                        Picasso.with(context)
                                .load(isFavorite ? R.drawable.t_star : R.drawable.f_star)
                                .into(holder.favIcon);
                        holder.favIcon.setClickable(true);
                    }));
        });

        compositeDisposable.add(Single.fromCallable(() -> {
            holder.favIcon.setClickable(false);
            return LocalDataSource.isFavorite(context, recipe.getId());
        })
                .compose(RxThreadManager.manageSingle())
                .subscribe(isFavorite -> {
                    Picasso.with(context)
                            .load(isFavorite ? R.drawable.t_star : R.drawable.f_star)
                            .into(holder.favIcon);
                    holder.favIcon.setClickable(true);
                }));
    }

    @Override
    public int getItemCount() {
        return (recipes == null) ? 0 : recipes.size();
    }

    public void unsubscibe() {
        compositeDisposable.dispose();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        protected TextView title;
        @BindView(R.id.steps_label_in_list)
        protected TextView steps;
        @BindView(R.id.servings_label_in_list)
        protected TextView servings;
        @BindView(R.id.fav)
        protected ImageView favIcon;
        @BindView(R.id.recipe_image)
        protected ImageView recipeImage;
        private final View view;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
