package com.example.javahealthify.ui.screens.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.FragmentMenuBinding;

import java.util.ArrayList;

public class MenuFragment extends Fragment implements DishRecycleViewAdapter.MealOptionsClickListener, DishRecycleViewAdapter.AddIngredientClickListener {

    MenuVM menuVM;
    private FragmentMenuBinding binding;

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

        RecyclerView recyclerView = binding.meals;
        DishRecycleViewAdapter adapter = new DishRecycleViewAdapter(this.getContext(), new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setDishes(menuVM.getFirestoreDishes().getValue());

        // Update the RecyclerView adapter with new data
        menuVM.getFirestoreDishes().observe(getViewLifecycleOwner(), adapter::setDishes);
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