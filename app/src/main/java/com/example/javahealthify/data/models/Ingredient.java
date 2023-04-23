package com.example.javahealthify.data.models;

public class Ingredient {
    private IngredientInfo ingredientInfo;
    private int weight;
    private int calories;

    public Ingredient(IngredientInfo ingredientInfo, int weight) {
        this.ingredientInfo = ingredientInfo;
        this.weight = weight;
        this.calories = ingredientInfo.getCalories()*weight;
    }

    public IngredientInfo getIngredientInfo() {
        return ingredientInfo;
    }

    public void setIngredientInfo(IngredientInfo ingredientInfo) {
        this.ingredientInfo = ingredientInfo;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
