package com.example.javahealthify.ui.screens.workout_exercise_details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.Exercise;

public class WorkoutExerciseDetailsVM extends ViewModel {
    private MutableLiveData<Exercise> exercise = new MutableLiveData<>();

    public MutableLiveData<Exercise> getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise.setValue(exercise);
    }

}
