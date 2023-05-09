package com.example.javahealthify.data.models;

import com.google.type.DateTime;

import java.util.List;

public class Workout {
    private int calories;
    private List<Exercise> exercises;
    private DateTime date;

    public Workout() {
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
}
