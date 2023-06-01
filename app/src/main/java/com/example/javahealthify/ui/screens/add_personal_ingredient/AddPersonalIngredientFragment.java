package com.example.javahealthify.ui.screens.add_personal_ingredient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.javahealthify.data.models.IngredientInfo;
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
        binding.addNewIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etNewIngredientName.getText() == null || binding.etNewIngredientServingSize.getText() == null || binding.etNewIngredientCalories.getText() == null || binding.etNewIngredientProtein.getText() == null || binding.etNewIngredientLipid.getText() == null) {
                    return;
                }
                IngredientInfo temp = new IngredientInfo(binding.etNewIngredientName.getText().toString(), Double.parseDouble(binding.etNewIngredientCalories.getText().toString()), Double.parseDouble(binding.etNewIngredientCarbs.getText().toString()), Double.parseDouble(binding.etNewIngredientLipid.getText().toString()), Double.parseDouble(binding.etNewIngredientProtein.getText().toString()));
                MutableLiveData<IngredientInfo> tempMutableLiveData = new MutableLiveData<>(temp);
                viewModel.setNewIngredient(tempMutableLiveData);
                viewModel.addPersonalIngredient();
            }
        });
        return binding.getRoot();
    }
}