package com.example.racktrack.Exercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.racktrack.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExerciseListActivity extends AppCompatActivity implements ExerciseListener {

    MutableLiveData<List<Exercise>> exercises = new MutableLiveData<>();
    ExerciseListAdapter exerciseListAdapter;
    ExerciseRepository exerciseRepository;

    public ExerciseListActivity() {
        super();
        exercises.setValue(new ArrayList<>(Arrays.asList(
                new Exercise("test", "test"),
                new Exercise("test", "test"),
                new Exercise("test", "test"),
                new Exercise("test", "test"),
                new Exercise("test", "test"),
                new Exercise("test", "test"),
                new Exercise("test", "test"),
                new Exercise("test", "test"),
                new Exercise("test", "test")
        )));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        this.exerciseRepository = new ExerciseRepository(this);
        exerciseRepository.getExercises(this);

        RecyclerView recyclerView = findViewById(R.id.exercise_recycler_view);
        this.exerciseListAdapter = new ExerciseListAdapter(new ExerciseListAdapter.ExerciseDiff());
        recyclerView.setAdapter(exerciseListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.exercises.observe(this, exerciseListAdapter::submitList);
    }

    @Override
    public void success(ArrayList<Exercise> exercises) {
        System.out.println("success");
        this.exercises.setValue(exercises);
    }
}