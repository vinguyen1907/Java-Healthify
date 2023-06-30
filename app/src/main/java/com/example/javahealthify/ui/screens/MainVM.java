package com.example.javahealthify.ui.screens;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.NormalUser;
import com.example.javahealthify.data.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainVM extends ViewModel {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private User user;

    public interface UserLoadCallback {
        void onUserLoaded(User user);
    }

    public void loadUser(UserLoadCallback callback) {
        firestore.collection("users")
                .whereEqualTo("email", firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    user = task.getResult().getDocuments().get(0).toObject(NormalUser.class);
                    if (callback != null) {
                        callback.onUserLoaded(user);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.i("Error", e.getMessage());
                });
    }

    public User getUser() {
        return user;
    }
}