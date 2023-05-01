package com.example.javahealthify.ui.screens.find_ingredient;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.IngredientInfo;
import com.example.javahealthify.ui.screens.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FindIngredientVM extends ViewModel {
    public MutableLiveData<ArrayList<IngredientInfo>> getIngredientInfoArrayList() {
        return ingredientInfoArrayList;
    }

    MutableLiveData<ArrayList<IngredientInfo>> ingredientInfoArrayList = new MutableLiveData<>();

    public void search(String query) {
        query = query.toUpperCase();
        CollectionReference ingredientInfoRef = MainActivity.getDb().collection("ingredient-data");
        Log.d("SEARCH", "search: " + ingredientInfoRef);
        Query searchQuery = ingredientInfoRef.whereGreaterThanOrEqualTo("Short_Description", query).whereLessThanOrEqualTo("Short_Description", query + "\uf8ff").limit(10);
        searchQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d("FOOOOD", "onComplete: hello");

                if (task.isSuccessful()) {
                    Log.d("SUCCESS", "Task is successful");
                    ArrayList<IngredientInfo> tempList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("DOCUMENT", "Processing document: " + document.getId());
                        Integer calories = document.get("Calories", int.class);
                        Double carbs = document.get("Carbs", double.class);
                        Double lipid = document.get("Lipid", double.class);
                        Double protein = document.get("Protein", double.class);
                        IngredientInfo temp = new IngredientInfo(document.getId(), document.get("Short_Description", String.class), calories, carbs, lipid, protein);
                        tempList.add(temp);
                    }
                    ingredientInfoArrayList.setValue(tempList);
                } else {
                    Log.d("FAILURE", "onComplete: " + task.getException());
                }
            }
        });
    }

}
