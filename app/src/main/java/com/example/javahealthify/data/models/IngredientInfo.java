package com.example.javahealthify.data.models;

public class IngredientInfo {
    private String id;
    private String shortDescription;
    private int calories;
    private double carbs;
    private double lipid;
    private double protein;

    public IngredientInfo(String id, String shortDescription, int calories, double carbs, double lipid, double protein) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.calories = calories;
        this.carbs = carbs;
        this.lipid = lipid;
        this.protein = protein;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public int getCalories() {
        return calories;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getLipid() {
        return lipid;
    }

    public double getProtein() {
        return protein;
    }
}
