package com.example.javahealthify.ui.screens.profile_edit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditProfileVM extends ViewModel {
    private MutableLiveData<User> userLiveData;

    public LiveData<User> getUserLiveData() {
        if (userLiveData == null) {
            userLiveData = new MutableLiveData<>();
            loadUser();
        }
        return userLiveData;
    }

    private void loadUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            User user = new User();
            user.setName(firebaseUser.getDisplayName());
            user.setEmail(firebaseUser.getEmail());
            userLiveData.setValue(user);
        }
    }
}
