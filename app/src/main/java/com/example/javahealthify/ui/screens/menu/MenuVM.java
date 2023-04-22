package com.example.javahealthify.ui.screens.menu;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.Ingredient;

public class MenuVM extends ViewModel {
    private MutableLiveData<String> dishName = new MutableLiveData<>();
    private MutableLiveData<Ingredient> s = new MutableLiveData<>();

    public MutableLiveData<String> getDishName() {
        return dishName;
    }

    public void setDishName(MutableLiveData<String> dishName) {
        this.dishName = dishName;
    }

    public MutableLiveData<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(MutableLiveData<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
