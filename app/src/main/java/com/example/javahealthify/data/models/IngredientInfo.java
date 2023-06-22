package com.example.javahealthify.data.models;

public class IngredientInfo {
    private String shortDescription;
    private double calories = 0;
    private double carbs = 0;
    private double lipid = 0;

    private double protein = 0;


    public IngredientInfo(String shortDescription, double calories, double carbs, double lipid, double protein) {
        this.shortDescription = shortDescription;
        this.calories = calories;
        this.carbs = carbs;
        this.lipid = lipid;
        this.protein = protein;
    }

    public IngredientInfo() {

    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getLipid() {
        return lipid;
    }

    public void setLipid(double lipid) {
        this.lipid = lipid;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }
}
