package com.example.javahealthify.ui.screens.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientRowRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRowRecyclerViewAdapter.IngredientRowViewHolder> {
    Context context;

    public void setIngredientArrayList(ArrayList<Ingredient> ingredientArrayList) {
        this.ingredientArrayList = ingredientArrayList;
        notifyDataSetChanged();
    }

    List<Ingredient> ingredientArrayList = new ArrayList<>();
    public IngredientRowRecyclerViewAdapter(Context context, List<Ingredient> ingredientList) {
        this.context = context;
        this.ingredientArrayList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientRowRecyclerViewAdapter.IngredientRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient_row_layout, parent, false);
        return new IngredientRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientRowRecyclerViewAdapter.IngredientRowViewHolder holder, int position) {
        holder.tvIngredientName.setText(ingredientArrayList.get(position).getName());
        holder.tvIngredientCalories.setText(String.valueOf(ingredientArrayList.get(position).getCalories()));
        holder.tvIngredientWeight.setText(String.valueOf(ingredientArrayList.get(position).getWeight()));

    }

    @Override
    public int getItemCount() {
        return ingredientArrayList == null? 0 : ingredientArrayList.size();
    }
    public static class IngredientRowViewHolder extends RecyclerView.ViewHolder {
        TextView tvIngredientName, tvIngredientCalories, tvIngredientWeight;

        public IngredientRowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredientName = itemView.findViewById(R.id.ingredient_name);
            tvIngredientCalories = itemView.findViewById(R.id.ingredient_calories);
            tvIngredientWeight = itemView.findViewById(R.id.ingredient_weight);
        }
    }
}
