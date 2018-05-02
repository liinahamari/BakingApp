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
import com.example.guest.bakingapp.mvp.model.Reciep;
import com.example.guest.bakingapp.ui.MainFragment;
import com.example.guest.bakingapp.utils.DbOperations;
import com.example.guest.bakingapp.utils.FavoritesChecker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
    private List<Reciep> recieps;
    private Context context;
    private FloatingActionButton fab;
    private MainFragment.Callbacks callbacks;
    private int position = -1;

    public MainListAdapter(Context context, MainFragment.Callbacks callbacks) {
        this.callbacks = callbacks;
        this.context = context;
        recieps = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    public void addRecieps(List<Reciep> recieps) {
        this.recieps.addAll(recieps);
        notifyDataSetChanged();
    }

    public void setFab(FloatingActionButton fab, int position) {
        this.fab = fab;
        this.position = position;
    }

    public void clearItems() {
        recieps.clear();
        notifyDataSetChanged();
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reciep reciep = recieps.get(position);
        holder.title.setText(reciep.getName());
        holder.title.setOnClickListener(v -> callbacks.onItemClicked(reciep, position));
        holder.favIcon.setOnClickListener(v ->
        {
            holder.favIcon.setClickable(false);
            if (reciep.isFavorite() == 0) {
                Single.fromCallable(() -> DbOperations.insert(recieps.get(position), context))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(uri -> bookmarkCallback(reciep, 1, holder, position));
            } else {
                Single.fromCallable(() -> DbOperations.delete(recieps.get(position).getId(), context))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(rowsDeleted -> bookmarkCallback(reciep, 0, holder, position));
            }
        });

        Single.fromCallable(() -> {
            holder.favIcon.setClickable(false);
            return FavoritesChecker.isFavorite(context, reciep);
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(isFavorite -> {
                    holder.favIcon.setClickable(true);
                    Picasso.with(context)
                            .load(isFavorite != 0 ? R.drawable.t_star: R.drawable.f_star)
                            .into(holder.favIcon);
                    reciep.setFavorite(isFavorite);
                });
    }

    @Override
    public int getItemCount() {
        return (recieps == null) ? 0 : recieps.size();
    }

    private void bookmarkCallback(Reciep reciep, int setFavorite, ViewHolder holder, int position) {
        reciep.setFavorite(setFavorite);
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
