package com.example.racktrack.Exercise;

import java.util.ArrayList;

public interface ExerciseListener {
    void success(ArrayList<Exercise> exercises);
    void failed(String error);
}
