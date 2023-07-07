package com.example.javahealthify.ui.screens;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.NormalUser;
import com.example.javahealthify.data.models.User;
import com.example.javahealthify.utils.FirebaseConstants;
import com.example.javahealthify.utils.GlobalMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainVM extends ViewModel {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private MutableLiveData<User> user = new MutableLiveData<>();

    public interface UserLoadCallback {
        void onUserLoaded(User user);
    }

    public void loadUser(UserLoadCallback callback) {
        Log.i("Loading user", "");
        FirebaseConstants.usersRef.document(firebaseAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                user.setValue(document.toObject(NormalUser.class));
                                if (callback != null) {
                                    callback.onUserLoaded(user. getValue());
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
                            User copyUser = user.getValue();
                            copyUser.setImageUrl(uri.toString());
                            user.setValue(copyUser);
                        }
                    }
                });
    }

    public void updateKeyword(String newName) {
        FirebaseConstants.usersRef.document(firebaseAuth.getCurrentUser().getUid()).update("keyword", GlobalMethods.generateKeyword(newName));
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public String getUserImageUrl() {
        if (user.getValue() == null) {
            return null;
        } else if (user.getValue().getImageUrl().isEmpty()) {
            return null;
        } else {
            return user.getValue().getImageUrl();
        }
    }
}