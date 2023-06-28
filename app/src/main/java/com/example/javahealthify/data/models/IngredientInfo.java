package com.example.javahealthify.data.models;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class IngredientInfo {

    private String id;
    private String Short_Description;
    private double Calories = 0;
    private double Carbs = 0;
    private double Lipid = 0;

    private double Protein = 0;


    public IngredientInfo(String Short_Description, double calories, double carbs, double lipid, double protein) {
        this.Short_Description = Short_Description;
        this.Calories = calories;
        this.Carbs = carbs;
        this.Lipid = lipid;
        this.Protein = protein;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public <T extends IngredientInfo> T withId(@NonNull final String id) {
        this.id = id;
        return (T) this;
    }

    public IngredientInfo() {

    }

    public String getShort_Description() {
        return Short_Description;
    }

    public void setShort_Description(String short_Description) {
        this.Short_Description = short_Description;
    }

    public double getCalories() {
        return Calories;
    }

    public void setCalories(double calories) {
        this.Calories = calories;
    }

    public double getCarbs() {
        return Carbs;
    }

    public void setCarbs(double carbs) {
        this.Carbs = carbs;
    }

    public double getLipid() {
        return Lipid;
    }

    public void setLipid(double lipid) {
        this.Lipid = lipid;
    }

    public double getProtein() {
        return Protein;
    }

    public void setProtein(double protein) {
        this.Protein = protein;
    }
}
