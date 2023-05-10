package com.example.javahealthify.ui.screens.workout;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.Exercise;
import com.example.javahealthify.data.models.Workout;
import com.example.javahealthify.utils.GlobalMethods;

import java.util.ArrayList;
import java.util.List;

public class WorkoutVM extends ViewModel {
    private MutableLiveData<List<Exercise>> selectedExercises = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Integer> selectedTotalCalories = new MutableLiveData<Integer>(0);
//    private MutableLiveData<Workout> selectedExercises = new MutableLiveData<>();


    public MutableLiveData<Integer> getSelectedTotalCalories() {
        return selectedTotalCalories;
    }

    public void onAddSelectedExercise(Exercise exercise) {
        List<Exercise> exercises = selectedExercises.getValue(); // get exercises in workout
        exercises.add(exercise); // add exercises to workout
        selectedExercises.setValue(exercises);
        selectedTotalCalories.setValue(GlobalMethods.calculateTotalCalories(selectedExercises.getValue()));
    }

    public String getSelectedTotalCaloriesString() {
        return String.valueOf(selectedTotalCalories);
    }
    public void loadSelectedExercises() {
        // TODO: load Selected Exercises
//        selectedExercises.setValue();
        selectedTotalCalories.setValue(GlobalMethods.calculateTotalCalories(selectedExercises.getValue()));
    }

    public MutableLiveData<List<Exercise>> getSelectedExercises() {
        return selectedExercises;
    }
}
