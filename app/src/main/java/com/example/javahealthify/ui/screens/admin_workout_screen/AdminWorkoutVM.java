package com.example.javahealthify.ui.screens.admin_workout_screen;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.Workout;
import com.example.javahealthify.data.models.WorkoutCategory;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminWorkoutVM extends ViewModel {
    MutableLiveData<ArrayList<WorkoutCategory>> workoutCategories;
    MutableLiveData<ArrayList<Workout>> workouts;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AdminWorkoutVM() {
        workouts = new MutableLiveData<>();
        workoutCategories = new MutableLiveData<>();
        fetchWorkoutCategories();
    }

    private void fetchWorkoutCategories() {
        db.collection("workout_categories").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<WorkoutCategory> temp = new ArrayList<>();
                for (DocumentSnapshot document: queryDocumentSnapshots) {
                    WorkoutCategory temp1 = document.toObject(WorkoutCategory.class);
                    temp.add(document.toObject(WorkoutCategory.class));
                    Log.d("Categories Id", "onSuccess: " + temp1.getId());
                }
                workoutCategories.postValue(temp);
            }
        });
    }
}
