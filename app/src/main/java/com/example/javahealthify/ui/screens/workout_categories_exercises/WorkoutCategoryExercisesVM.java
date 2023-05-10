package com.example.javahealthify.ui.screens.workout_categories_exercises;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.Exercise;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WorkoutCategoryExercisesVM extends ViewModel {
    private String categoryId;
    private MutableLiveData<List<Exercise>> exercises = new MutableLiveData<>(new ArrayList<>());
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();


    public void loadData() {
        exercises.setValue(new ArrayList<>());

        List<Exercise> newList = new ArrayList<>();
        firestore.collection("workout_categories").document(categoryId).collection("exercises").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                Exercise newExercise = doc.toObject(Exercise.class);
                                newList.add(newExercise);
                            }
                            exercises.setValue(newList);
                        } else {
                            Log.i("Load exercise error","Error getting documents: ", task.getException());
                        }
                    }
                });
    }



    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public MutableLiveData<List<Exercise>> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises.setValue(exercises);
    }


}
