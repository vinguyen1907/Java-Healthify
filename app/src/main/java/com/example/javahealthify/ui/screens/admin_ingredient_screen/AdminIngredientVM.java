package com.example.javahealthify.ui.screens.admin_ingredient_screen;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.IngredientInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;

public class AdminIngredientVM extends ViewModel {
    MutableLiveData<ArrayList<IngredientInfo>> databaseIngredientList;
    MutableLiveData<ArrayList<IngredientInfo>> pendingIngredientList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Query query;
    ListenerRegistration registration;

    private int pageSize = 20;
    private DocumentSnapshot lastVisibleDocument;

    public AdminIngredientVM() {
        databaseIngredientList = new MutableLiveData<>(new ArrayList<>());
        pendingIngredientList = new MutableLiveData<>(new ArrayList<>());
        query = db.collection("ingredient-data").limit(pageSize);
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
                        IngredientInfo ingredientInfo = doc.toObject(IngredientInfo.class).withId(doc.getId());
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

    public void addIngredient(IngredientInfo ingredientInfo) {
        db.collection("ingredient-data").add(ingredientInfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                ArrayList<IngredientInfo> currentList = databaseIngredientList.getValue();
                ingredientInfo.withId(documentReference.getId()); // Assuming IngredientInfo has a `withId` method to set the document ID
                currentList.add(ingredientInfo);
                databaseIngredientList.postValue(currentList);
            }
        });
    }

    public void updateIngredient(String id, IngredientInfo updatedIngredient) {
        db.collection("ingredient-data").document(id).set(updatedIngredient).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                ArrayList<IngredientInfo> currentList = databaseIngredientList.getValue();
                for (int i = 0; i < currentList.size(); i++) {
                    IngredientInfo ingredient = currentList.get(i);
                    if (ingredient.getId().equals(id)) {
                        currentList.set(i, updatedIngredient);
                    }
                }
                databaseIngredientList.postValue(currentList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ArrayList<IngredientInfo> currentList = databaseIngredientList.getValue();
                for (Iterator<IngredientInfo> it = currentList.iterator(); it.hasNext();) {
                    IngredientInfo ingredient = it.next();
                    if (ingredient.getId().equals(id)) {
                        it.remove();
                    }
                }
                databaseIngredientList.postValue(currentList);
            }
        });
    }

    public void deleteIngredient(String id) {
        db.collection("ingredient-data").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                ArrayList<IngredientInfo> currentList = databaseIngredientList.getValue();
                for (Iterator<IngredientInfo> it = currentList.iterator(); it.hasNext();) {
                    IngredientInfo ingredient = it.next();
                    if (ingredient.getId().equals(id)) {
                        it.remove();
                    }
                }
                databaseIngredientList.postValue(currentList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

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
