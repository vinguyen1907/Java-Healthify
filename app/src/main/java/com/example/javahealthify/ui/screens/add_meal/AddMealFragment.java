package com.example.javahealthify.ui.screens.add_meal;


import android.os.Bundle;
import android.util.Log;
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
    MutableLiveData<Dish> dish = new MutableLiveData<>();
    MutableLiveData<Integer> totalCalories = new MutableLiveData<>(0);
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
        recyclerViewAdapterForAddAndDelete = new IngredientRowRecyclerViewAdapterForAddAndDelete(this.getContext(), addMealVM.getIngredients().getValue());

        binding.ingredientsListRecyclerview.setAdapter(recyclerViewAdapterForAddAndDelete);
        binding.ingredientsListRecyclerview.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.mealTypeSpinner.setAdapter(adapter);

        totalCalories.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.dishTotalCalories.setText(String.valueOf(totalCalories.getValue()));
            }
        });

        addMealVM.getIngredients().observe(getViewLifecycleOwner(), new Observer<ArrayList<Ingredient>>() {
            @Override
            public void onChanged(ArrayList<Ingredient> ingredients) {
                recyclerViewAdapterForAddAndDelete.setIngredients(ingredients);
            }
        });
        setOnClick();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("RESUME", "onResume: I am resumed");
        binding.ingredientsListRecyclerview.setAdapter(recyclerViewAdapterForAddAndDelete);
        //recyclerViewAdapterForAddAndDelete.setIngredients(addMealVM.ingredients.getValue());
    }

    public void setOnClick() {
        binding.addMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalCalories.getValue() == null) {
                    return;
                }
                if (addMealVM.ingredients == null || addMealVM.ingredients.getValue() == null) {
                    Toast.makeText(getContext(), "Please add some ingredients!", Toast.LENGTH_LONG).show();
                    return;
                }

                Dish tempDish = new Dish("DD", binding.addMealDishName.getText().toString(), addMealVM.ingredients.getValue(), totalCalories.getValue(), binding.mealTypeSpinner.getSelectedItem().toString());
                dish.setValue(tempDish);
                ArrayList<Dish> tempDishes = menuVM.getDishes().getValue();
                if(tempDishes == null) {
                    tempDishes = new ArrayList<>();
                }
                tempDishes.add(tempDish);
                menuVM.getDishes().setValue(tempDishes);
                menuVM.setTodayCalories(String.valueOf(totalCalories.getValue()));
                Log.d("CALORIES", "onClick: " + menuVM.getTodayCalories().getValue());
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

        if (addMealVM.ingredients.getValue() == null) {
            return;
        }
        addMealVM.getIngredients().getValue().remove(position);
    }
}