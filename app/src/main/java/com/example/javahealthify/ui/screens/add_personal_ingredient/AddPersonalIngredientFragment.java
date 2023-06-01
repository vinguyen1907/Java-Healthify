package com.example.javahealthify.ui.screens.add_personal_ingredient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.javahealthify.databinding.FragmentAddPersonalIngredientBinding;

public class AddPersonalIngredientFragment extends Fragment {

    private AddPersonalIngredientVM viewModel;
    private FragmentAddPersonalIngredientBinding binding;

    public AddPersonalIngredientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(AddPersonalIngredientVM.class);
        binding = FragmentAddPersonalIngredientBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }
}