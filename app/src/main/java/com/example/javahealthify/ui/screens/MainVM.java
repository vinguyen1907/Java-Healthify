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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainVM extends ViewModel {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private User user;

    public interface UserLoadCallback {
        void onUserLoaded(User user);
    }

    public void loadUser(UserLoadCallback callback) {
        FirebaseConstants.usersRef.document(firebaseAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                user = document.toObject(NormalUser.class);
                                if (callback != null) {
                                    callback.onUserLoaded(user);
                                }
                            }
                        } else {
                            Log.e("Load user failed", "", task.getException());
                        }
                    }
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