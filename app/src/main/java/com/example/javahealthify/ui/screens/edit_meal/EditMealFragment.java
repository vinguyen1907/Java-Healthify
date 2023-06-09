package com.example.javahealthify.ui.screens.edit_meal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.Dish;
import com.example.javahealthify.data.models.Ingredient;
import com.example.javahealthify.databinding.FragmentEditMealBinding;
import com.example.javahealthify.ui.screens.menu.IngredientRowRecyclerViewAdapterForAddAndDelete;
import com.example.javahealthify.ui.screens.menu.MenuVM;
import com.example.javahealthify.utils.GlobalMethods;

import java.util.ArrayList;

public class EditMealFragment extends Fragment implements IngredientRowRecyclerViewAdapterForAddAndDelete.OnWeightChangedListener, IngredientRowRecyclerViewAdapterForAddAndDelete.RemoveIngredientClickListener {
    MenuVM menuVM;
    EditMealVM editMealVM;
    int dishPosition;
    FragmentEditMealBinding binding;
    Dish dishToEdit = new Dish();
    double totalCalories = 0;
    IngredientRowRecyclerViewAdapterForAddAndDelete recyclerViewAdapterForAddAndDelete;

    public EditMealFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewModelProvider provider = new ViewModelProvider(requireActivity());
        menuVM = provider.get(MenuVM.class);
        editMealVM = provider.get(EditMealVM.class);
        dishPosition = getArguments().getInt("position");
        dishToEdit = menuVM.getFirestoreDishes().getValue().get(dishPosition);

        MutableLiveData<ArrayList<Ingredient>> liveData = new MutableLiveData<>();
        liveData.setValue((ArrayList<Ingredient>) dishToEdit.getIngredients());
        if (editMealVM.getIngredients().getValue() == null) {
            editMealVM.setIngredients(liveData);

        }
        binding = FragmentEditMealBinding.inflate(inflater, container, false);
        binding.setViewModel(menuVM);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.editMealDishName.setText(dishToEdit.getName());

        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(requireContext(), R.array.meal_types, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        binding.editMealMealTypeSpinner.setAdapter(adapter);
        recyclerViewAdapterForAddAndDelete = new IngredientRowRecyclerViewAdapterForAddAndDelete(this.getContext(), editMealVM.getIngredients().getValue(), this, this);
        binding.editMealIngredientsListRecyclerview.setAdapter(recyclerViewAdapterForAddAndDelete);
        binding.editMealIngredientsListRecyclerview.setLayoutManager(new LinearLayoutManager(this.getContext()));

        editMealVM.getIngredients().observe(getViewLifecycleOwner(), new Observer<ArrayList<Ingredient>>() {
            @Override
            public void onChanged(ArrayList<Ingredient> ingredients) {
                if (ingredients.size() > 2) {
                    Log.d("NEW INGREDIENT", "onChanged: " + ingredients.get(2).getName());

                }
                recyclerViewAdapterForAddAndDelete.setIngredients(ingredients);

                totalCalories = 0;
                for (Ingredient ingredient : ingredients) {
                    totalCalories += ingredient.getCalories();
                }
                binding.editDishTotalCalories.setText(GlobalMethods.formatDoubleToString(totalCalories));
            }
        });
        setOnClick();
        return binding.getRoot();
    }

    private void setOnClick() {
        binding.editMealToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalMethods.backToPreviousFragment(EditMealFragment.this);
            }
        });

        binding.editMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalCalories == 0) {
                    return;
                }
                if (editMealVM.ingredients == null || editMealVM.ingredients.getValue() == null || editMealVM.ingredients.getValue().isEmpty()) {
                    Toast.makeText(getContext(), "Please add some ingredients!", Toast.LENGTH_LONG).show();
                    return;
                }
                // add data to the dish
                double totalProtein = 0;
                double totalLipid = 0;
                double totalCarb = 0;
                for (Ingredient ingredient : editMealVM.getIngredients().getValue()) {
                    totalProtein += ingredient.getProtein();
                    totalLipid += ingredient.getLipid();
                    totalCarb += ingredient.getCarb();
                }
                dishToEdit.setName(binding.editMealDishName.getText().toString());
                dishToEdit.setSession(binding.editMealMealTypeSpinner.getSelectedItem().toString());
                dishToEdit.setCalories(totalCalories);
                dishToEdit.setProtein(totalProtein);
                dishToEdit.setLipid(totalLipid);
                dishToEdit.setCarb(totalCarb);
                dishToEdit.setIngredients(editMealVM.getIngredients().getValue());
                editMealVM.setIngredients(new MutableLiveData<>());
                ArrayList<Dish> newDishes = menuVM.getFirestoreDishes().getValue();
                newDishes.set(dishPosition, dishToEdit);
                menuVM.getFirestoreDishes().updateDishes(newDishes);
                editMealVM.setIngredients(new MutableLiveData<>());
                NavHostFragment.findNavController(EditMealFragment.this).navigate(R.id.action_editMealFragment_to_menuFragment);

            }
        });

        binding.editMealAddIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("operation", "edit");
                NavHostFragment.findNavController(EditMealFragment.this).navigate(R.id.action_editMealFragment_to_findIngredientFragment, bundle);
            }
        });
    }

    @Override
    public void onRemoveIngredientClick(int position) {
        if (position >= 0 && position < editMealVM.getIngredients().getValue().size()) {
            ArrayList<Ingredient> updatedIngredients = new ArrayList<>(editMealVM.getIngredients().getValue());
            updatedIngredients.remove(position);
            editMealVM.getIngredients().postValue(updatedIngredients);
        }
    }

    @Override
    public void onWeightChanged(int position, double newValue) {
        if (position >= 0 && position < editMealVM.getIngredients().getValue().size()) {
            // Get the updated ingredient and set the new weight
            Ingredient updatedIngredient = editMealVM.getIngredients().getValue().get(position);
            updatedIngredient.updateWeight(newValue);
            totalCalories = 0;

            // Update the ingredient in the list
            ArrayList<Ingredient> updatedIngredients = new ArrayList<>(editMealVM.getIngredients().getValue());
            updatedIngredients.set(position, updatedIngredient);
            for (Ingredient ingredient : updatedIngredients) {
                totalCalories += ingredient.getCalories();
            }

            MutableLiveData<ArrayList<Ingredient>> newIngredients = new MutableLiveData<>();
            newIngredients.setValue(updatedIngredients);
            editMealVM.setIngredients(newIngredients);

            // Update the displayed total calories
            binding.editDishTotalCalories.setText(GlobalMethods.formatDoubleToString(totalCalories));
        }
    }
}