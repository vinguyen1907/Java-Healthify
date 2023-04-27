package com.example.javahealthify.data.models;

import java.util.ArrayList;

public class Dish {
    private String id;
    private String dishName;
    private ArrayList<Ingredient> ingredientData;
    private int totalCalories;
    private String Session;
    public Dish(String id, String dishName, ArrayList<Ingredient> ingredientData, int totalCalories, String Session) {
    this.id = id;
    this.dishName = dishName;
    this.ingredientData = ingredientData;
    this.totalCalories = totalCalories;
    this.Session = Session;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public ArrayList<Ingredient> getIngredientData() {
        return ingredientData;
    }

    public void setIngredientData(ArrayList<Ingredient> ingredientData) {
        this.ingredientData = ingredientData;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public String getSession() {
        return Session;
    }

    public void setSession(String session) {
        Session = session;
    }
}
