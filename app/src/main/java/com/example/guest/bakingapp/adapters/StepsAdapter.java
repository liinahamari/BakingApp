package com.example.guest.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.data.remote.pojo.RecipeRemote;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by l1maginaire on 4/29/18.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    private RecipeRemote recipe;
    private Context context;
    private Callbacks callbacks;

    public StepsAdapter(RecipeRemote recipe, Context context) {
        this.recipe = recipe;
        this.context = context;
        try {
            callbacks = (Callbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onLikeClicked()");
        }
    }

    @NonNull
    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_step, parent, false);
        return new StepsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.ViewHolder holder, int position) {
        String shortDescription = " " + recipe.getStepRemotes().get(position).getShortDescription();
        holder.label.setText(shortDescription);
        holder.view.setOnClickListener(v -> callbacks.onStepClicked(position));
        if (recipe.getStepRemotes().get(position).getVideoURL().equals("")
                || recipe.getStepRemotes().get(position).getVideoURL().isEmpty())
            holder.playButton.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return (recipe.getStepRemotes() == null) ? 0 : recipe.getStepRemotes().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_label)
        protected TextView label;
        @BindView(R.id.play_button)
        protected ImageView playButton;
        private final View view;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public interface Callbacks {
        void onStepClicked(int position);
    }
}

