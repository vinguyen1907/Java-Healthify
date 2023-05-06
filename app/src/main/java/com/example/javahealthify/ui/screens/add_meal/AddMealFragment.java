package com.example.javahealthify.ui.screens.add_meal;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.Dish;
import com.example.javahealthify.data.models.Ingredient;
import com.example.javahealthify.databinding.FragmentAddMealBinding;
import com.example.javahealthify.ui.screens.menu.IngredientRowRecyclerViewAdapterForAddAndDelete;
import com.example.javahealthify.ui.screens.menu.MenuVM;

import java.util.ArrayList;

public class AddMealFragment extends Fragment implements IngredientRowRecyclerViewAdapterForAddAndDelete.RemoveIngredientClickListener {
    MenuVM menuVM;
    AddMealVM addMealVM;
    FragmentAddMealBinding binding;
    Dish dish = new Dish();
    double totalCalories = 0;
    IngredientRowRecyclerViewAdapterForAddAndDelete recyclerViewAdapterForAddAndDelete;


    public AddMealFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewModelProvider provider = new ViewModelProvider(requireActivity());
        menuVM = provider.get(MenuVM.class);
        addMealVM = provider.get(AddMealVM.class);
        binding = FragmentAddMealBinding.inflate(inflater, container, false);
        binding.setViewModel(menuVM);
        binding.setLifecycleOwner(this);

        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(requireContext(), R.array.meal_types, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        recyclerViewAdapterForAddAndDelete = new IngredientRowRecyclerViewAdapterForAddAndDelete(this.getContext(), addMealVM.getIngredients().getValue(), this);

        binding.ingredientsListRecyclerview.setAdapter(recyclerViewAdapterForAddAndDelete);
        binding.ingredientsListRecyclerview.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.mealTypeSpinner.setAdapter(adapter);


        addMealVM.getIngredients().observe(getViewLifecycleOwner(), new Observer<ArrayList<Ingredient>>() {
            @Override
            public void onChanged(ArrayList<Ingredient> ingredients) {
                recyclerViewAdapterForAddAndDelete.setIngredients(ingredients);

                totalCalories = 0;
                for( Ingredient ingredient: ingredients) {
                    totalCalories += ingredient.getCalories();
                }
                binding.dishTotalCalories.setText(String.valueOf(totalCalories));
            }
        });
        setOnClick();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.ingredientsListRecyclerview.setAdapter(recyclerViewAdapterForAddAndDelete);
    }

    public void setOnClick() {
        binding.addMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalCalories == 0) {
                    return;
                }
                if (addMealVM.ingredients == null || addMealVM.ingredients.getValue() == null || addMealVM.ingredients.getValue().isEmpty()) {
                    Toast.makeText(getContext(), "Please add some ingredients!", Toast.LENGTH_LONG).show();
                    return;
                }
                // add data to the dish
                double totalProtein = 0;
                double totalLipid = 0;
                double totalCarb = 0;
                for (Ingredient ingredient: addMealVM.getIngredients().getValue()
                     ) {
                    totalProtein += ingredient.getProtein();
                    totalLipid += ingredient.getLipid();
                    totalCarb += ingredient.getCarb();
                }
                dish.setName(binding.addMealDishName.getText().toString());
                dish.setSession(binding.mealTypeSpinner.getSelectedItem().toString());
                dish.setCalories(totalCalories);
                dish.setProtein(totalProtein);
                dish.setLipid(totalLipid);
                dish.setCarb(totalCarb);
                dish.setIngredients(addMealVM.getIngredients().getValue());
                //add dish to database
                addMealVM.setIngredients(new MutableLiveData<>());
                menuVM.getFirestoreDishes().addDish(dish);

                NavHostFragment.findNavController(AddMealFragment.this).navigate(R.id.action_addMealFragment_to_menuFragment);
            }
        });
        binding.addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AddMealFragment.this).navigate(R.id.action_addMealFragment_to_findIngredientFragment);
            }

        });
    }


    @Override
    public void onRemoveIngredientClick(int position) {
        if (position >= 0 && position < addMealVM.getIngredients().getValue().size()) {
            ArrayList<Ingredient> updatedIngredients = new ArrayList<>(addMealVM.getIngredients().getValue());
            updatedIngredients.remove(position);
            addMealVM.getIngredients().setValue(updatedIngredients);
        }
    }

}