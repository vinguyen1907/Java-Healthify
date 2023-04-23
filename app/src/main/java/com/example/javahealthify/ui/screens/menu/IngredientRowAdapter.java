package com.example.javahealthify.ui.screens.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.Ingredient;

import java.util.ArrayList;

public class IngredientRowAdapter extends ArrayAdapter<Ingredient> {
    private int resource;
    private ArrayList<Ingredient> ingredients;
    public IngredientRowAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Ingredient> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.ingredients = objects;
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }
        TextView tvIngredientName = convertView.findViewById(R.id.ingredient_name);
        TextView tvIngredientCalories = convertView.findViewById(R.id.ingredient_calories);
        TextView tvIngredientWeight = convertView.findViewById(R.id.ingredient_weight);
        tvIngredientName.setText(ingredients.get(position).getIngredientInfo().getShortDescription());
        tvIngredientCalories.setText(String.valueOf(ingredients.get(position).getCalories()));
        tvIngredientWeight.setText(String.valueOf(ingredients.get(position).getWeight()));
        return convertView;
    }
}
