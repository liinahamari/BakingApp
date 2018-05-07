package com.example.guest.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.data.remote.pojo.StepRemote;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by l1maginaire on 4/29/18.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    private List<StepRemote> stepRemotes;
    private Context context;
    private Callbacks callbacks;

    public StepsAdapter(List<StepRemote> stepRemotes, Context context) {
        this.stepRemotes = stepRemotes;
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
        String shortDescription = " " + stepRemotes.get(position).getShortDescription();
        holder.label.setText(shortDescription);
        holder.description.setText(stepRemotes.get(position).getDescription());
        holder.view.setOnClickListener(v -> callbacks.onStepClicked(position));
    }

    @Override
    public int getItemCount() {
        return (stepRemotes == null) ? 0 : stepRemotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_label)
        protected TextView label;
        @BindView(R.id.step_description)
        protected TextView description;
        private final View view;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public interface Callbacks{
        void onStepClicked(int position);
    }
}

