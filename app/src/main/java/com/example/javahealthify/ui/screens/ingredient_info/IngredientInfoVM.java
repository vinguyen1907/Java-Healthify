package com.example.javahealthify.ui.screens.ingredient_info;

import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.IngredientInfo;
import com.example.javahealthify.utils.GlobalMethods;

public class IngredientInfoVM extends ViewModel {
    private GlobalMethods globalMethods;

    public GlobalMethods getGlobalMethods() {
        return globalMethods;
    }


    public IngredientInfo getIngredientInfo() {
        return ingredientInfo;
    }

    public void setIngredientInfo(IngredientInfo ingredientInfo) {
        this.ingredientInfo = ingredientInfo;
    }

    IngredientInfo ingredientInfo = new IngredientInfo();




}
