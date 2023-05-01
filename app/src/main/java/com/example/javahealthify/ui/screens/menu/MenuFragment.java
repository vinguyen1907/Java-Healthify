package com.example.javahealthify.ui.screens.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.Dish;
import com.example.javahealthify.data.models.User;
import com.example.javahealthify.databinding.FragmentMenuBinding;

import java.util.ArrayList;

public class MenuFragment extends Fragment implements DishRecycleViewAdapter.MealOptionsClickListener, DishRecycleViewAdapter.AddIngredientClickListener {

    MenuVM viewModel;
    private FragmentMenuBinding binding;

    private User user;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewModelProvider provider = new ViewModelProvider(requireActivity());
        viewModel = provider.get(MenuVM.class);
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        RecyclerView recyclerView = binding.meals;
        DishRecycleViewAdapter adapter = new DishRecycleViewAdapter(this.getContext(), viewModel.getDishes().getValue());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        viewModel.getTodayCalories().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("CALORIES", "onChanged: " + s);
                binding.menuTodayCalories.setText(s);
            }
        });
        viewModel.getDishes().observe(getViewLifecycleOwner(), new Observer<ArrayList<Dish>>() {
            @Override
            public void onChanged(ArrayList<Dish> dishes) {
                // Update the adapter with the new data
                Log.d("Dishes list", "setDishes:  I am change");
                adapter.setDishes(dishes);
            }
        });
        binding.addMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddMealClick();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onMealOptionClick(int position) {

    }

    @Override
    public void onAddIngredientClick(int position) {

    }

    public void onAddMealClick() {
        NavHostFragment.findNavController(MenuFragment.this).navigate(R.id.action_menuFragment_to_addMealFragment);
    }
}