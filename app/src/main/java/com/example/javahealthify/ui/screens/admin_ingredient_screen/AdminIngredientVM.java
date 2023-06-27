package com.example.javahealthify.ui.screens.admin_ingredient_screen;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.IngredientInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminIngredientVM extends ViewModel {
    MutableLiveData<ArrayList<IngredientInfo>> databaseIngredientList;
    MutableLiveData<ArrayList<IngredientInfo>> pendingIngredientList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Query query = db.collection("ingredient-data").limit(10);
    ListenerRegistration registration;

    private int pageSize = 10;
    private DocumentSnapshot lastVisibleDocument;

    public AdminIngredientVM() {
        databaseIngredientList = new MutableLiveData<>(new ArrayList<>());
        pendingIngredientList = new MutableLiveData<>(new ArrayList<>());
        loadMore();
    }
    public void loadMore() {
        Query newQuery;
        if (lastVisibleDocument != null) {
            newQuery = query.startAfter(lastVisibleDocument);
        } else {
            newQuery = query;
        }

        newQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<IngredientInfo> tempList = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        IngredientInfo ingredientInfo = doc.toObject(IngredientInfo.class);
                        tempList.add(ingredientInfo);
                    }
                    if (!tempList.isEmpty()) {
                        lastVisibleDocument = task.getResult().getDocuments().get(tempList.size() - 1);
                        ArrayList<IngredientInfo> currentList = databaseIngredientList.getValue();
                        currentList.addAll(tempList);
                        databaseIngredientList.postValue(currentList);
                    }
                }
            }
        });
    }
    public MutableLiveData<ArrayList<IngredientInfo>> getDatabaseIngredientList() {
        return databaseIngredientList;
    }

    public void setDatabaseIngredientList(MutableLiveData<ArrayList<IngredientInfo>> databaseIngredientList) {
        this.databaseIngredientList = databaseIngredientList;
    }

    public MutableLiveData<ArrayList<IngredientInfo>> getPendingIngredientList() {
        return pendingIngredientList;
    }

    public void setPendingIngredientList(MutableLiveData<ArrayList<IngredientInfo>> pendingIngredientList) {
        this.pendingIngredientList = pendingIngredientList;
    }
}
