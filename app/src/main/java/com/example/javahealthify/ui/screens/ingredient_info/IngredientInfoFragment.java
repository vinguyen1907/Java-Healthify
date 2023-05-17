package com.example.javahealthify.ui.screens.ingredient_info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.FragmentIngredientInfoBinding;
import com.example.javahealthify.ui.screens.find_ingredient.FindIngredientVM;

public class IngredientInfoFragment extends Fragment {
    IngredientInfoVM ingredientInfoVM;
    FragmentIngredientInfoBinding binding;
    FindIngredientVM findIngredientVM;
    int position;


    public IngredientInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ingredientInfoVM = new ViewModelProvider(requireActivity()).get(IngredientInfoVM.class);
        findIngredientVM = new ViewModelProvider(requireActivity()).get(FindIngredientVM.class);
        binding = FragmentIngredientInfoBinding.inflate(inflater, container, false);
        binding.setViewModel(ingredientInfoVM);
        position = requireArguments().getInt("position");
        ingredientInfoVM.setIngredientInfo(findIngredientVM.getIngredientInfoArrayList().getValue().get(position));
        setUpTable();
        return inflater.inflate(R.layout.fragment_ingredient_info, container, false);
    }

    private void setUpTable() {
        TableRow newRow = new TableRow(this.getContext());
        TextView cell1 = new TextView(this.getContext());
        cell1.setText("Cell 3");
        newRow.addView(cell1);

        TextView cell2 = new TextView(this.getContext());
        cell2.setText("Cell 4");
        newRow.addView(cell2);

        binding.ingredientInfoTable.addView(newRow);
    }
}