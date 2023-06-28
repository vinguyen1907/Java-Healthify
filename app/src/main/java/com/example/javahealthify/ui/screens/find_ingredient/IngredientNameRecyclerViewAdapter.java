package com.example.javahealthify.ui.screens.find_ingredient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.IngredientInfo;

import java.util.ArrayList;

public class IngredientNameRecyclerViewAdapter extends RecyclerView.Adapter<IngredientNameRecyclerViewAdapter.IngredientNameViewHolder> {

    Context context;

    public void setIngredientInfoArrayList(ArrayList<IngredientInfo> ingredientInfoArrayList) {
        this.ingredientInfoArrayList = ingredientInfoArrayList;
        notifyDataSetChanged();
    }

    ArrayList<IngredientInfo> ingredientInfoArrayList = new ArrayList<>();

    private ViewIngredientInfoClickListener ingredientInfoClickListener;
    private IngredientInfoNameClickListener ingredientInfoNameClickListener;

    IngredientNameRecyclerViewAdapter(Context context, ArrayList<IngredientInfo> ingredientInfoArrayList, ViewIngredientInfoClickListener viewIngredientInfoClickListener, IngredientInfoNameClickListener ingredientInfoNameClickListener) {
        this.context = context;
        this.ingredientInfoArrayList = ingredientInfoArrayList;
        this.ingredientInfoClickListener = viewIngredientInfoClickListener;
        this.ingredientInfoNameClickListener = ingredientInfoNameClickListener;
    }

    @NonNull
    @Override
    public IngredientNameRecyclerViewAdapter.IngredientNameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient_info_row_layout, parent, false);
        return new IngredientNameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientNameViewHolder holder, int position) {
        holder.tvIngredientName.setText(ingredientInfoArrayList.get(position).getShortDescription());
        holder.btnViewIngredientInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ingredientInfoClickListener != null) {
                    ViewGroup parent = (ViewGroup) holder.itemView.getParent();
                    int recyclerViewId = parent.getId();
                    ingredientInfoClickListener.onViewIngredientInfoClick(position, recyclerViewId);
                }
            }
        });
        holder.tvIngredientName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ingredientInfoNameClickListener != null) {
                    ViewGroup parent = (ViewGroup) holder.itemView.getParent();
                    int recyclerViewId = parent.getId();
                    ingredientInfoNameClickListener.onIngredientInfoNameClick(position, recyclerViewId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientInfoArrayList == null ? 0 : ingredientInfoArrayList.size();
    }

    public static class IngredientNameViewHolder extends RecyclerView.ViewHolder {
        TextView tvIngredientName;
        AppCompatButton btnViewIngredientInfo;


        public IngredientNameViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredientName = itemView.findViewById(R.id.ingredient_name_info);
            btnViewIngredientInfo = itemView.findViewById(R.id.view_ingredient_info_button);
        }
    }

    public interface ViewIngredientInfoClickListener {
        void onViewIngredientInfoClick(int position, int recyclerViewId);
    }

    public interface IngredientInfoNameClickListener {
        void onIngredientInfoNameClick(int position, int recyclerViewId);
    }
}
