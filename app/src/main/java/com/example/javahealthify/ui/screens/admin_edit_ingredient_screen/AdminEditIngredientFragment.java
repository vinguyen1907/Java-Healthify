package com.example.javahealthify.ui.screens.admin_edit_ingredient_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.javahealthify.data.models.IngredientInfo;
import com.example.javahealthify.databinding.FragmentAdminEditIngredientBinding;
import com.example.javahealthify.ui.screens.admin_ingredient_screen.AdminIngredientVM;
import com.example.javahealthify.utils.GlobalMethods;

public class AdminEditIngredientFragment extends Fragment {

    private AdminIngredientVM adminIngredientVM;
    private FragmentAdminEditIngredientBinding binding;

    private int ingredientPosition;

    public AdminEditIngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adminIngredientVM = new ViewModelProvider(requireActivity()).get(AdminIngredientVM.class);
        binding = FragmentAdminEditIngredientBinding.inflate(inflater, container, false);
        binding.setViewModel(adminIngredientVM);
        ingredientPosition = requireArguments().getInt("position");


        setUpView();


        binding.setLifecycleOwner(getViewLifecycleOwner());


        return binding.getRoot();
    }

    private void setUpView() {
        IngredientInfo temp = adminIngredientVM.getDatabaseIngredientList().getValue().get(ingredientPosition);
        binding.etUpdateIngredientName.setText(temp.getShort_Description());
        binding.etUpdateIngredientProtein.setText(GlobalMethods.formatDoubleToString(temp.getProtein()));
        binding.etUpdateIngredientCalories.setText(GlobalMethods.formatDoubleToString(temp.getCalories()));
        binding.etUpdateIngredientCarbs.setText(GlobalMethods.formatDoubleToString(temp.getCarbs()));
        binding.etUpdateIngredientLipid.setText(GlobalMethods.formatDoubleToString(temp.getLipid()));
        binding.appBar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalMethods.backToPreviousFragment(AdminEditIngredientFragment.this);
            }
        });
        binding.updateIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etUpdateIngredientName.getText() != null && binding.etUpdateIngredientCalories.getText() != null && binding.etUpdateIngredientProtein.getText() != null && binding.etUpdateIngredientLipid.getText() != null || binding.etUpdateIngredientCarbs.getText() != null) {
                    IngredientInfo newIngredient = new IngredientInfo(binding.etUpdateIngredientName.getText().toString(), Double.parseDouble(binding.etUpdateIngredientCalories.getText().toString()), Double.parseDouble(binding.etUpdateIngredientCarbs.getText().toString()), Double.parseDouble(binding.etUpdateIngredientLipid.getText().toString()), Double.parseDouble(binding.etUpdateIngredientProtein.getText().toString()));
                    newIngredient.setId(temp.getId());
                    adminIngredientVM.updateIngredient(temp.getId(), newIngredient);
                    GlobalMethods.backToPreviousFragment(AdminEditIngredientFragment.this);
                }
            }
        });
    }
}