package com.example.racktrack.Exercise;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.racktrack.RoomDatabase.Quote;

public class ExerciseListAdapter extends ListAdapter<Exercise, ExerciseViewHolder> {

    protected ExerciseListAdapter(@NonNull DiffUtil.ItemCallback<Exercise> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ExerciseViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = this.getItem(position);
        holder.bind(exercise.getName(), exercise.getDescription());
    }

    static class ExerciseDiff extends DiffUtil.ItemCallback<Exercise> {
        @Override
        public boolean areItemsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return false;
        }
    }
}
