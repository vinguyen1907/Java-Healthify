package com.example.javahealthify.ui.screens.add_personal_ingredient;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddPersonalIngredientVM extends ViewModel {
    MutableLiveData<String> ingredientName;

    public MutableLiveData<String> getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(MutableLiveData<String> ingredientName) {
        this.ingredientName = ingredientName;
    }

    public MutableLiveData<Double> getServingSize() {
        return servingSize;
    }

    public void setServingSize(MutableLiveData<Double> servingSize) {
        this.servingSize = servingSize;
    }

    public MutableLiveData<Double> getCalories() {
        return calories;
    }

    public void setCalories(MutableLiveData<Double> calories) {
        this.calories = calories;
    }

    public MutableLiveData<Double> getProtein() {
        return protein;
    }

    public void setProtein(MutableLiveData<Double> protein) {
        this.protein = protein;
    }

    public MutableLiveData<Double> getLipid() {
        return lipid;
    }

    public void setLipid(MutableLiveData<Double> lipid) {
        this.lipid = lipid;
    }

    MutableLiveData<Double> servingSize;
    MutableLiveData<Double> calories;
    MutableLiveData<Double> protein;
    MutableLiveData<Double> lipid;
}
