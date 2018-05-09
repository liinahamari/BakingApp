package com.example.guest.bakingapp.adapters;

/**
 * Created by l1maginaire on 4/26/18.
 */

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
    private List<RecipeRemote> recipeRemotes;
    private Context context;
    private FloatingActionButton fab;
    private MainFragment.Callbacks callbacks;
    private int position = -1;

    public MainListAdapter(Context context, MainFragment.Callbacks callbacks) {
        this.callbacks = callbacks;
        this.context = context;
        recipeRemotes = new ArrayList<>(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    public void addRecieps(List<RecipeRemote> recipeRemotes) {
        this.recipeRemotes.addAll(recipeRemotes);
        notifyDataSetChanged();
    }

    public void setFab(FloatingActionButton fab, int position) {
        this.fab = fab;
        this.position = position;
    }

    public void clearItems() {
        recipeRemotes.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeRemote recipeRemote = recipeRemotes.get(position);
        holder.title.setText(recipeRemote.getName());
        String steps = "Steps: " + String.valueOf(recipeRemote.getStepRemotes().size());
        holder.steps.setText(steps);
        String servings = "Servings: " + String.valueOf(recipeRemote.getServings());
        holder.servings.setText(servings);
        holder.title.setOnClickListener(v -> callbacks.onItemClicked(recipeRemote, position));
        holder.favIcon.setOnClickListener(v ->
        {
            holder.favIcon.setClickable(false);
            if (recipeRemote.isFavorite() == 0) {
                Single.fromCallable(() -> LocalDataSource.insert(recipeRemotes.get(position), context))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(uri -> bookmarkCallback(recipeRemote, 1, holder, position));
            } else {
                Single.fromCallable(() -> LocalDataSource.delete(recipeRemotes.get(position).getId(), context))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(rowsDeleted -> bookmarkCallback(recipeRemote, 0, holder, position));
            }
        });

        Picasso.with(context)
                .load(recipeRemote.isFavorite() != 0 ? R.drawable.t_star : R.drawable.f_star).into(holder.favIcon);
    }

    @Override
    public int getItemCount() {
        return (recipeRemotes == null) ? 0 : recipeRemotes.size();
    }

    private void bookmarkCallback(RecipeRemote recipeRemote, int setFavorite, ViewHolder holder, int position) {
        recipeRemote.setFavorite(setFavorite);
        Picasso.with(context)
                .load(setFavorite != 0 ? R.drawable.t_star : R.drawable.f_star)
                .into(holder.favIcon);
        if (fab != null && this.position == position) {
//            LikeButtonColorChanger.change(fab, context, setFavorite);
        }
        holder.favIcon.setClickable(true);
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
        private final View view;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
