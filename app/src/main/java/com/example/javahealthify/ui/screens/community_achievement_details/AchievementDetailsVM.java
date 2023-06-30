package com.example.javahealthify.ui.screens.community_achievement_details;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.Achievement;
import com.example.javahealthify.data.models.Exercise;
import com.example.javahealthify.utils.FirebaseConstants;
import com.example.javahealthify.utils.GlobalMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AchievementDetailsVM extends ViewModel {
    private MutableLiveData<Achievement> achievement = new MutableLiveData<>();
    private MutableLiveData<List<Exercise>> exercises = new MutableLiveData<>();
//    private MutableLiveData<List<>>
    private MutableLiveData<Boolean> isLoadingFoods = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isLoadingExercises = new MutableLiveData<>(false);

    private void loadFoods() {
//        FirebaseConstants.dailyActivitiesRef.document(GlobalMethods.convertDateToHyphenSplittingFormat(achievement.getValue().getCreatedTime()))
//                .collection()
    }

    private void loadExercises() {
        isLoadingExercises.setValue(true);

        FirebaseConstants.dailyActivitiesRef.document(GlobalMethods.convertDateToHyphenSplittingFormat(achievement.getValue().getCreatedTime()))
                .collection("workouts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Exercise> newExercises = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                newExercises.add(doc.toObject(Exercise.class));
                            }
                            exercises.setValue(newExercises);

                            isLoadingExercises.setValue(false);
                        }
                    }
                });
    }

    // GETTER AND SETTERS
    public MutableLiveData<Achievement> getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement.setValue(achievement);
        loadFoods();
        loadExercises();
    }

    public MutableLiveData<List<Exercise>> getExercises() {
        return exercises;
    }

    public void setExercises(MutableLiveData<List<Exercise>> exercises) {
        this.exercises = exercises;
    }

    public MutableLiveData<Boolean> getIsLoadingFoods() {
        return isLoadingFoods;
    }

    public MutableLiveData<Boolean> getIsLoadingExercises() {
        return isLoadingExercises;
    }
}
