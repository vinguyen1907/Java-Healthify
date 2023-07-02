package com.example.javahealthify.ui.screens;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.NormalUser;
import com.example.javahealthify.data.models.User;
import com.example.javahealthify.utils.FirebaseConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    public void updateUserProfileImage(Uri uri) {
        FirebaseConstants.usersRef.document(firebaseAuth.getCurrentUser().getUid()).update("imageUrl", uri)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.setImageUrl(uri.toString());
                        }
                    }
                });
    }

    public User getUser() {
        return user;
    }
}