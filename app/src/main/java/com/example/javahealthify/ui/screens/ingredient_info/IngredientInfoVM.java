package com.example.javahealthify.ui.screens.ingredient_info;

import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.IngredientInfo;

public class IngredientInfoVM extends ViewModel {

    public IngredientInfo getIngredientInfo() {
        return ingredientInfo;
    }

    public void setIngredientInfo(IngredientInfo ingredientInfo) {
        this.ingredientInfo = ingredientInfo;
    }

    IngredientInfo ingredientInfo = new IngredientInfo();




}
