package com.example.javahealthify.ui.screens.add_personal_ingredient;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.IngredientInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddPersonalIngredientVM extends ViewModel {
    private MutableLiveData<IngredientInfo> newIngredient = new MutableLiveData<>();

    public MutableLiveData<IngredientInfo> getNewIngredient() {
        return newIngredient;
    }

    public AddPersonalIngredientVM(){}

    public void setNewIngredient(MutableLiveData<IngredientInfo> newIngredient) {
        this.newIngredient = newIngredient;
    }


    public AddPersonalIngredientVM(MutableLiveData<IngredientInfo> newIngredient) {
        this.newIngredient = newIngredient;
    }



    public void addPersonalIngredient() {
        DocumentReference userDocumentRef = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        CollectionReference personalIngredientRef = userDocumentRef.collection("personal_ingredient");
        personalIngredientRef.add(this.newIngredient.getValue()).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("ADDED NEW INGREDIENT", "DocumentSnapshot added with ID: " + documentReference.getId());

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ADDED NEW INGREDIENT failure", "Error adding document", e);
                    }
                });
    }
}
