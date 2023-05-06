package com.example.javahealthify.ui.screens.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.Dish;

import java.util.ArrayList;

public class DishRecycleViewAdapter extends RecyclerView.Adapter<DishRecycleViewAdapter.MealViewHolder> {
    Context context;

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
        notifyDataSetChanged();
    }

    ArrayList<Dish> dishes = new ArrayList<Dish>();

    private MealOptionsClickListener mealOptionsClickListener;
    private AddIngredientClickListener addIngredientClickListener;

    public DishRecycleViewAdapter(Context context, ArrayList<Dish> dishArrayList) {
        this.context = context;
        this.dishes = dishArrayList;
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
        holder.tvDishName.setText(dishes.get(position).getName());
        holder.tvMealCalories.setText(String.valueOf(dishes.get(position).getCalories()));

        IngredientRowRecyclerViewAdapter ingredientRowRecyclerViewAdapter = new IngredientRowRecyclerViewAdapter(context, dishes.get(position).getIngredients());
        holder.rvIngredients.setLayoutManager(new LinearLayoutManager(context));
        holder.rvIngredients.setAdapter(ingredientRowRecyclerViewAdapter);
        holder.btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mealOptionsClickListener != null) {
                    mealOptionsClickListener.onMealOptionClick(position);
                }
            }
        });
        holder.btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addIngredientClickListener != null) {
                    addIngredientClickListener.onAddIngredientClick(position);
                }
            }
        });

        Drawable startDrawable;
        switch (dishes.get(position).getSession()) {

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
        return dishes == null? 0 : dishes.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView tvDishName, tvMealCalories;
        RecyclerView rvIngredients;
        AppCompatButton btnMealOption, btnAddIngredient;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDishName = itemView.findViewById(R.id.menu_dish_name);
            tvMealCalories = itemView.findViewById(R.id.meal_calories);
            rvIngredients = itemView.findViewById(R.id.meal_ingredients_list);
            btnMealOption = itemView.findViewById(R.id.meal_options);
            btnAddIngredient = itemView.findViewById(R.id.add_ingredient);
        }
    }

    public interface MealOptionsClickListener {
        void onMealOptionClick(int position);
    }

    public interface AddIngredientClickListener {
        void onAddIngredientClick(int position);
    }

}
