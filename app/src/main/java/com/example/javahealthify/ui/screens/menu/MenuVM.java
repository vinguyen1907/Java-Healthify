package com.example.javahealthify.ui.screens.menu;

import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.custom_livedata.FirestoreDishes;

public class MenuVM extends ViewModel {
    private FirestoreDishes firestoreDishes;
    public FirestoreDishes getFirestoreDishes() {
        return firestoreDishes;
    }


    public void setFirestoreDishes(FirestoreDishes firestoreDishes) {
        this.firestoreDishes = firestoreDishes;
    }
    public MenuVM() {
        firestoreDishes = new FirestoreDishes();
    }
}
