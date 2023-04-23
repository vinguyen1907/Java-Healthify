package com.example.javahealthify.ui.screens.menu;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.Dish;
import com.example.javahealthify.data.models.Ingredient;
import com.example.javahealthify.data.models.IngredientInfo;

import java.util.ArrayList;
import java.util.Objects;

public class MenuVM extends ViewModel {
    private MutableLiveData<ArrayList<Dish>> dishes = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Dish>> getDishes() {
        return dishes;
    }

    public void setDishes(MutableLiveData<ArrayList<Dish>> dishes) {
        this.dishes = dishes;
    }

    public void setupForTesting() {
        if (dishes.getValue() == null) {
            dishes.setValue(new ArrayList<>());
        }
        for (int j = 0; j < 50; j++) {
            int totalCalories = 0;
            ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

            for (int i = 0; i < 3; i++) {
                IngredientInfo tempInfo = new IngredientInfo("p1", "Chicken breast, no skin " + String.valueOf(i), 100, 10, 10, 10);
                Ingredient tempIngredient = new Ingredient(tempInfo, 100);
                ingredients.add(tempIngredient);
                totalCalories += tempIngredient.getWeight() * tempIngredient.getIngredientInfo().getCalories();
            }

            Dish temp = new Dish("dd", "Chicken Breast" + String.valueOf(j), ingredients, totalCalories, "Breakfast");
            Objects.requireNonNull(dishes.getValue()).add(temp);
        }
        Log.d("KHOADZ", "setupForTesting: " + dishes);

    }

    public void optionMenuClick() {

    }

    public void addIngredientClick() {
    }
}
