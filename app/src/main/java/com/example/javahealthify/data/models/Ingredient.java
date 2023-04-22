package com.example.javahealthify.data.models;

public class Ingredient {
    private String id;
    private String shortDescription;
    private double calories;
    private double carbs;
    private double lipid;
    private double protein;

    public Ingredient(String id, String shortDescription, double calories, double carbs, double lipid, double protein) {
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

    public double getCalories() {
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
