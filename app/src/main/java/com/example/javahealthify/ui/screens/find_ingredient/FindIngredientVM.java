package com.example.javahealthify.ui.screens.find_ingredient;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.IngredientInfo;
import com.example.javahealthify.ui.screens.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FindIngredientVM extends ViewModel {

    MutableLiveData<ArrayList<IngredientInfo>> ingredientInfoArrayList = new MutableLiveData<>();


    MutableLiveData<ArrayList<IngredientInfo>> personalIngredientInfoArrayList = new MutableLiveData<>();

    public MutableLiveData<ArrayList<IngredientInfo>> getIngredientInfoArrayList() {
        return ingredientInfoArrayList;
    }

    public MutableLiveData<ArrayList<IngredientInfo>> getPersonalIngredientInfoArrayList() {
        return personalIngredientInfoArrayList;
    }

    public void search(String query) {
        query = query.toUpperCase();
        CollectionReference ingredientInfoRef = MainActivity.getDb().collection("ingredient-data");
        Query searchQuery = ingredientInfoRef.whereGreaterThanOrEqualTo("Short_Description", query).whereLessThanOrEqualTo("Short_Description", query + "\uf8ff").limit(10);
        searchQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d("FOOOOD", "onComplete: hello");

                if (task.isSuccessful()) {
                    Log.d("SUCCESS", "Task is successful");
                    ArrayList<IngredientInfo> tempList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Integer calories = document.get("Calories", int.class);
                        Double carbs = document.get("Carbs", double.class);
                        Double lipid = document.get("Lipid", double.class);
                        Double protein = document.get("Protein", double.class);
                        IngredientInfo temp = new IngredientInfo(document.get("Short_Description", String.class), calories, carbs, lipid, protein);
                        tempList.add(temp);
                    }
                    ingredientInfoArrayList.setValue(tempList);
                } else {
                    Log.d("FAILURE", "onComplete: " + task.getException());
                }
            }
        });

        //personal ingredients
        DocumentReference userDocument = MainActivity.getDb().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        CollectionReference personalIngredientsReference = userDocument.collection("personal_ingredient");
        Query personalIngredientQuery = personalIngredientsReference.whereGreaterThanOrEqualTo("shortDescription", query).whereLessThanOrEqualTo("shortDescription", query + "\uf8ff").limit(10);
        personalIngredientQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("personal ingredient found", "onComplete: personalIngredient found");
                    ArrayList<IngredientInfo> tempList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Integer calories = document.get("calories", int.class);
                        Double carbs = document.get("carbs", double.class);
                        Double lipid = document.get("lipid", double.class);
                        Double protein = document.get("protein", double.class);
                        IngredientInfo temp = new IngredientInfo(document.get("shortDescription", String.class), calories, carbs, lipid, protein);
                        tempList.add(temp);
                    }
                    personalIngredientInfoArrayList.setValue(tempList);
                } else {
                    Log.d("FAILURE", "onComplete: " + task.getException());
                }
            }
        });
    }
}
