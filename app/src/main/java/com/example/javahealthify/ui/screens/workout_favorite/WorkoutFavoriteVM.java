package com.example.javahealthify.ui.screens.workout_favorite;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.Exercise;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WorkoutFavoriteVM extends ViewModel {
    private MutableLiveData<List<Exercise>> favoriteList = new MutableLiveData<>();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public WorkoutFavoriteVM() {
        loadFavoriteList();
    }

    public MutableLiveData<List<Exercise>> getFavoriteList() {
        return favoriteList;
    }

    private void loadFavoriteList() {
        firestore.collection("users").document(auth.getCurrentUser().getUid())
                .collection("favorite_exercises").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Exercise> newList = new ArrayList<>();
                            for (DocumentSnapshot doc : task.getResult()) {
                                newList.add(doc.toObject(Exercise.class));
                            }
                            favoriteList.setValue(newList);
                        }
                    }
                });
    }
}
