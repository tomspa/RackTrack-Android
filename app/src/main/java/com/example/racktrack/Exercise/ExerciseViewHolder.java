package com.example.racktrack.Exercise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racktrack.R;

public class ExerciseViewHolder extends RecyclerView.ViewHolder {

    private TextView titleView;
    private TextView descView;

    public ExerciseViewHolder(@NonNull View itemView) {
        super(itemView);
        this.titleView = itemView.findViewById(R.id.exercise_title);
        this.descView = itemView.findViewById(R.id.exercise_desc);
    }

    public void bind(String name, String desc) {
        this.titleView.setText(name);
        this.descView.setText(desc);
    };

    static ExerciseViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_list_item, parent, false);
        return new ExerciseViewHolder(view);

    }
}
