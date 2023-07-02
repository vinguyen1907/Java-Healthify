package com.example.javahealthify.ui.screens.ingredient_info;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.javahealthify.databinding.FragmentIngredientInfoBinding;
import com.example.javahealthify.ui.screens.find_ingredient.FindIngredientVM;
import com.example.javahealthify.utils.GlobalMethods;

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
        position = requireArguments().getInt("position");
        String type = requireArguments().getString("type");
        if (type == "global") {
            ingredientInfoVM.setIngredientInfo(findIngredientVM.getIngredientInfoArrayList().getValue().get(position));
        } else {
            ingredientInfoVM.setIngredientInfo(findIngredientVM.getPersonalIngredientInfoArrayList().getValue().get(position));
        }
        Log.d("INGREDIENT NAME", "onCreateView: " + ingredientInfoVM.ingredientInfo.getShort_Description());
        binding.setViewModel(ingredientInfoVM);
        binding.setLifecycleOwner(getViewLifecycleOwner());
binding.ingredientInfoToolbar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        GlobalMethods.backToPreviousFragment(IngredientInfoFragment.this);
    }
});
        return binding.getRoot();
    }
}