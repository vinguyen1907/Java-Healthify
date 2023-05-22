package com.example.javahealthify.ui.screens.workout;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.Exercise;
import com.example.javahealthify.utils.GlobalMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutVM extends ViewModel {
    private MutableLiveData<List<Exercise>> selectedExercises = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Integer> selectedTotalCalories = new MutableLiveData<Integer>(0);
    //    private MutableLiveData<Workout> selectedExercises = new MutableLiveData<>();
    private MutableLiveData<String> addSelectedExercisesToDbMessage = new MutableLiveData<>(null);
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public WorkoutVM() {
        loadSelectedExercises();
    }

    // Methods handle data
    public void loadSelectedExercises() {
        firestore.collection("users").document(auth.getCurrentUser().getUid())
                .collection("daily_activities").document(GlobalMethods.convertDateToHyphenSplittingFormat(new Date()))
                .collection("today_selected_exercises").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Exercise> newList = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            newList.add(doc.toObject(Exercise.class));
                        }
                        selectedExercises.setValue(newList);
                        recalculateSelectedExercisesCalories();
                    }
                });
    }

    public void addExercisesToDb(List<Exercise> exercise) {
        WriteBatch batch = firestore.batch();

        for (Exercise newExercise : exercise) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", newExercise.getId());
            data.put("name", newExercise.getName());
            data.put("imageUrl", newExercise.getImageUrl());
            data.put("startingPosition", newExercise.getStartingPosition());
            data.put("execution", newExercise.getExecution());
            data.put("unit", newExercise.getUnit());
            data.put("count", newExercise.getCount());
            data.put("caloriesPerUnit", newExercise.getCaloriesPerUnit());
            data.put("categoryId", newExercise.getCategoryId());

           DocumentReference ref = firestore.collection("users").document(auth.getCurrentUser().getUid())
                    .collection("daily_activities").document(GlobalMethods.convertDateToHyphenSplittingFormat(new Date()))
                    .collection("today_selected_exercises").document();

            batch.set(ref, data);
        }

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    addSelectedExercisesToDbMessage.setValue("Add your selected exercises successfully");
                    loadSelectedExercises();
                } else {
                    Log.e("Add exercise", "Error writing batch", task.getException());
                    addSelectedExercisesToDbMessage.setValue("Some errors occurred");
                }
            }
        });
    }

    private void recalculateSelectedExercisesCalories() {
        selectedTotalCalories.setValue(GlobalMethods.calculateTotalCalories(selectedExercises.getValue()));
    }

    public void moveTempListToSelectedList(List<Exercise> tempList) {
        addExercisesToDb(tempList);
    }

    // Getters and Setters
    public MutableLiveData<Integer> getSelectedTotalCalories() {
        return selectedTotalCalories;
    }

    public MutableLiveData<List<Exercise>> getSelectedExercises() {
        return selectedExercises;
    }


    public MutableLiveData<String> getAddSelectedExercisesToDbMessage() {
        return addSelectedExercisesToDbMessage;
    }

    public void setAddSelectedExercisesToDbMessage(String newMessage) {
        addSelectedExercisesToDbMessage.setValue(newMessage);
    }

}
