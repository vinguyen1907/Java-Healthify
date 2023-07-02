package com.example.javahealthify.ui.screens.profile;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.NormalUser;
import com.example.javahealthify.data.models.User;
import com.example.javahealthify.ui.screens.profile_personal_info.ProfilePersonalInfoFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileVM extends ViewModel {
    private MutableLiveData<Boolean> isLoadingData = new MutableLiveData<>( null);
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private NormalUser user = new NormalUser();

    public NormalUser getUser() {
        return user;
    }

    public MutableLiveData<Boolean> getIsLoadingData() {
        return isLoadingData;
    }
    public void getUserLiveData() {
        isLoadingData.setValue(true);
        firestore.collection("users").whereEqualTo("email", firebaseAuth.getCurrentUser().getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            user = task.getResult().getDocuments().get(0).toObject(NormalUser.class);
                            isLoadingData.setValue(false);

                        } else {
                            Log.d("Get user data error", "Error getting user documents: ", task.getException());
                            isLoadingData.setValue(false);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        isLoadingData.setValue(false);
                        Log.i("Error", e.getMessage());
                    }
                });
    }
}
