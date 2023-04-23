package com.example.javahealthify.ui.screens.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.Dish;

import java.util.ArrayList;

public class DishRecycleViewAdapter extends RecyclerView.Adapter<DishRecycleViewAdapter.MealViewHolder> {
    Context context;
    MutableLiveData<ArrayList<Dish>> dishes = new MutableLiveData<ArrayList<Dish>>();

    public DishRecycleViewAdapter(Context context, ArrayList<Dish> dishArrayList) {
        this.context = context;
        this.dishes.setValue( dishArrayList);
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_layout, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        holder.tvDishName.setText(dishes.getValue().get(position).getDishName());
        holder.tvMealCalories.setText(String.valueOf(dishes.getValue().get(position).getTotalCalories()));

        Log.d("KHOASIEUDZ", "onBindViewHolder: " + String.valueOf(dishes.getValue().get(position).getIngredientData().size()));
        IngredientRowRecyclerViewAdapter ingredientRowRecyclerViewAdapter = new IngredientRowRecyclerViewAdapter(context, dishes.getValue().get(position).getIngredientData());
        holder.rvIngredients.setLayoutManager(new LinearLayoutManager(context));
        holder.rvIngredients.setAdapter(ingredientRowRecyclerViewAdapter);

        Drawable startDrawable;
        switch (dishes.getValue().get(position).getSession()) {

            case "Breakfast":
                startDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_breakfast);
                break;
            case "Lunch":
                startDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_lunch);
                break;
            case "Dinner":
                startDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_dinner);
                break;
            default:
                startDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_breakfast);

        }
        holder.tvDishName.setCompoundDrawablesRelativeWithIntrinsicBounds(startDrawable, null, null, null);

    }

    @Override
    public int getItemCount() {
        return dishes.getValue().size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView tvDishName, tvMealCalories;
        RecyclerView rvIngredients;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDishName = itemView.findViewById(R.id.menu_dish_name);
            tvMealCalories = itemView.findViewById(R.id.meal_calories);
            rvIngredients = itemView.findViewById(R.id.meal_ingredients_list);
        }
    }

}
