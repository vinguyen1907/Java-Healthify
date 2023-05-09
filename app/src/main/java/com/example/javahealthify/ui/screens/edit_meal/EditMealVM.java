package com.example.javahealthify.ui.screens.edit_meal;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.Ingredient;

import java.util.ArrayList;

public class EditMealVM extends ViewModel {

    MutableLiveData<ArrayList<Ingredient>> ingredients = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Ingredient>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(MutableLiveData<ArrayList<Ingredient>> ingredients) {
        this.ingredients = ingredients;
    }
}
