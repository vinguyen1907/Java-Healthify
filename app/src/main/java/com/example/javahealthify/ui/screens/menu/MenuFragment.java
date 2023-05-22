package com.example.javahealthify.ui.screens.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.Dish;
import com.example.javahealthify.databinding.FragmentMenuBinding;
import com.example.javahealthify.utils.GlobalMethods;

import java.util.ArrayList;

public class MenuFragment extends Fragment implements DishRecycleViewAdapter.MealOptionsClickListener, DishRecycleViewAdapter.AddIngredientClickListener, DishRecycleViewAdapter.MealOptionDialogListener {

    MenuVM menuVM;
    private FragmentMenuBinding binding;

    DishRecycleViewAdapter adapter;

    private Double totalCalories = 0.0;


    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewModelProvider provider = new ViewModelProvider(requireActivity());
        menuVM = provider.get(MenuVM.class);
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        binding.setViewModel(menuVM);
        binding.setLifecycleOwner(this);

        // Remove the observer before setting up a new one
        menuVM.getFirestoreDishes().removeObservers(this);

        adapter = new DishRecycleViewAdapter(this.getContext(), menuVM.getFirestoreDishes().getValue(), this);
        binding.meals.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.meals.setAdapter(adapter);
        Log.d("OBSERVING DISHES", "onCreateView: " + menuVM.getFirestoreDishes().getValue());
        // Update the RecyclerView adapter with new data

        binding.addMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddMealClick();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        menuVM.getFirestoreDishes().observe(getViewLifecycleOwner(), new Observer<ArrayList<Dish>>() {
            @Override
            public void onChanged(ArrayList<Dish> dishArrayList) {
                adapter.setDishes(dishArrayList);
                totalCalories = 0.0;
                for (Dish dish: dishArrayList
                     ) {
                    totalCalories += dish.getCalories();
                    binding.menuTodayCalories.setText(GlobalMethods.formatDoubleToString(totalCalories));
                }


            }
        });
    }

    public void onAddMealClick() {
        NavHostFragment.findNavController(MenuFragment.this).navigate(R.id.action_menuFragment_to_addMealFragment);
    }

    @Override
    public void onEditMealClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        NavHostFragment.findNavController(MenuFragment.this).navigate(R.id.action_menuFragment_to_editMealFragment, bundle);
    }

    @Override
    public void onAddIngredientClick(int position) {

    }

    @Override
    public void onDeleteMealClick(int position) {
        Log.d("DELETION CALLED", "onDeleteMealClick: at " + position);
        menuVM.getFirestoreDishes().deleteDish(menuVM.getFirestoreDishes().getValue().get(position));
    }

    @Override
    public void onCancelClick(int position) {

    }

    @Override
    public void onMealOptionClick(int position) {

    }
}