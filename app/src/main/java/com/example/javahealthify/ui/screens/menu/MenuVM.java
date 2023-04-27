package com.example.javahealthify.ui.screens.menu;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.Dish;

import java.util.ArrayList;

public class MenuVM extends ViewModel {
    public void setTodayCalories(String todayCalories) {
        this.todayCalories.setValue(todayCalories);
    }

    private MutableLiveData<ArrayList<Dish>> dishes = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Dish>> getDishes() {
        return dishes;
    }

    public void setDishes(MutableLiveData<ArrayList<Dish>> dishes) {
        this.dishes = dishes;
    }

    private MutableLiveData<String> todayCalories = new MutableLiveData<>();

    public MutableLiveData<String> getTodayCalories() {
        return todayCalories;
    }
}
