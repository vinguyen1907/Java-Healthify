package com.example.javahealthify.data.custom_livedata;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.javahealthify.data.models.Dish;
import com.example.javahealthify.data.models.Ingredient;
import com.example.javahealthify.utils.GlobalMethods;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreDishes extends LiveData<ArrayList<Dish>> {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference userRef = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
    private CollectionReference dailyActivitiesRef = userRef.collection("daily_activities");
    private DocumentReference dailyActivityRef;
    private ListenerRegistration listenerRegistration;
    private void queryDailyActivities() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date start = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date end = calendar.getTime();

        Timestamp startTimestamp = new Timestamp(start);
        Timestamp endTimestamp = new Timestamp(end);

        dailyActivitiesRef
                .whereGreaterThanOrEqualTo("date", startTimestamp)
                .whereLessThan("date", endTimestamp)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()) {
                            Map<String, Object> newDailyActivity = new HashMap<>();
                            newDailyActivity.put("date", startTimestamp);
                            newDailyActivity.put("dishes", new ArrayList<Map<String, Object>>());

                            dailyActivitiesRef.add(newDailyActivity)
                                    .addOnSuccessListener(documentReference -> {
                                        dailyActivityRef = documentReference;
                                    }).addOnFailureListener( e-> {
                                        Log.e("SOMEERRORS", "queryDailyActivities: error in query",  e);
                                    });
                            Log.d("FIRESTOREDISHES", "queryDailyActivities: I Created a new dailyactivities");
                        } else {
                            dailyActivityRef = task.getResult().getDocuments().get(0).getReference();
                        }
                    } else {
                        // Handle error
                        Log.d("FIRESTOREDISHES", "queryDailyActivities: something went wrong");
                    }
                });
    }

    @Override
    protected void onActive() {
        super.onActive();
        queryDailyActivities();
        if (dailyActivityRef != null) {
            listenerRegistration = dailyActivityRef.addSnapshotListener((documentSnapshot, e) -> {
                if (e != null) {
                    Log.e("FIRESTOREDISHES", "Error in SnapshotListener", e);
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    List<Map<String, Object>> dishesMapList = (List<Map<String, Object>>) documentSnapshot.get("dishes");
                    ArrayList<Dish> dishes = new ArrayList<>();

                    for (Map<String, Object> dishMap : dishesMapList) {
                        Dish dish = new Dish();
                        dish.setName((String) dishMap.get("name"));
                        dish.setSession((String) dishMap.get("session"));
                        Log.d("DISHNAME", "onActive: " + dish.getName());

                        List<Map<String, Object>> ingredientsMapList = (List<Map<String, Object>>) dishMap.get("ingredients");
                        if (ingredientsMapList == null || ingredientsMapList.isEmpty()) {
                            continue;
                        }
                        List<Ingredient> ingredients = new ArrayList<>();
                        double dishTotalCalories = 0;
                        double dishTotalCarb = 0;
                        double dishTotalLipid = 0;
                        double dishTotalProtein = 0;
                        for (Map<String, Object> ingredientMap : ingredientsMapList) {
                            Ingredient ingredient = new Ingredient();
                            ingredient.setName((String) ingredientMap.get("name"));
                            ingredient.setCalories(((Number) ingredientMap.get("calories")).doubleValue());
                            ingredient.setCarb(((Number) ingredientMap.get("carb")).doubleValue());
                            ingredient.setLipid(((Number) ingredientMap.get("lipid")).doubleValue());
                            ingredient.setProtein(((Number) ingredientMap.get("protein")).doubleValue());
                            ingredient.setWeight(((Number) ingredientMap.get("weight")).doubleValue());
                            dishTotalCalories += ingredient.getCalories();
                            dishTotalCarb += ingredient.getCarb();
                            dishTotalLipid += ingredient.getLipid();
                            dishTotalProtein += ingredient.getProtein();
                            ingredients.add(ingredient);
                        }
                        dish.setIngredients(ingredients);
                        dish.setCalories(dishTotalCalories);
                        dish.setCarb(dishTotalCarb);
                        dish.setLipid(dishTotalLipid);
                        dish.setProtein(dishTotalProtein);

                        dishes.add(dish);
                    }

                    setValue(dishes);
                }
            });
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (listenerRegistration != null) {
            listenerRegistration.remove();
            listenerRegistration = null;
        }
    }

    public void addDish(Dish newDish) {
        if (dailyActivityRef != null) {
            dailyActivityRef.update("dishes", FieldValue.arrayUnion(GlobalMethods.toKeyValuePairs(newDish)))
                    .addOnSuccessListener(aVoid -> Log.d("FIRESTOREDISHES", "addDish: Dish added successfully"))
                    .addOnFailureListener(e -> Log.e("FIRESTOREDISHES", "addDish: Error adding dish", e));
        }
    }

    public void deleteDish(Dish dishToDelete) {
        Log.d("DELETE DISH NAME", "deleteDish: " + dishToDelete.getIngredients());
        if (dailyActivityRef != null) {
            dailyActivityRef.update("dishes", FieldValue.arrayRemove(GlobalMethods.toKeyValuePairs(dishToDelete)))
                    .addOnSuccessListener(aVoid -> Log.d("FIRESTOREDISHES", "deleteDish: Dish deleted successfully"))
                    .addOnFailureListener(e -> Log.e("FIRESTOREDISHES", "deleteDish: Error deleting dish", e));
        }
    }

    public void updateDishes(List<Dish> newDishes) {
        if (dailyActivityRef != null) {
            List<Map<String, Object>> newDishesKeyValuePairs = new ArrayList<>();
            for (Dish dish : newDishes) {
                newDishesKeyValuePairs.add(GlobalMethods.toKeyValuePairs(dish));
            }
            dailyActivityRef.update("dishes", newDishesKeyValuePairs)
                    .addOnSuccessListener(aVoid -> Log.d("FIRESTOREDISHES", "updateDishes: Dishes updated successfully"))
                    .addOnFailureListener(e -> Log.e("FIRESTOREDISHES", "updateDishes: Error updating dishes", e));
        }
    }
}
