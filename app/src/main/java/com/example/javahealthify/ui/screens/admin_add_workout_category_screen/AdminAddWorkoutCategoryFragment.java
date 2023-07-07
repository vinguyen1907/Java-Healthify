package com.example.javahealthify.ui.screens.admin_add_workout_category_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.javahealthify.databinding.FragmentAdminAddWorkoutCategoryBinding;
import com.example.javahealthify.ui.screens.admin_workout_screen.AdminWorkoutVM;
import com.example.javahealthify.utils.GlobalMethods;

public class AdminAddWorkoutCategoryFragment extends Fragment {
    FragmentAdminAddWorkoutCategoryBinding binding;
    AdminWorkoutVM viewModel;
    private ViewModelProvider provider;

    public AdminAddWorkoutCategoryFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminAddWorkoutCategoryBinding.inflate(inflater, container, false);
        ViewModelProvider provider = new ViewModelProvider(requireActivity());
        viewModel = provider.get(AdminWorkoutVM.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        setUpOnClick();
        return binding.getRoot();
    }

    private void setUpOnClick() {
        binding.appBar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.categoryNameEt.setText("");
                GlobalMethods.backToPreviousFragment(AdminAddWorkoutCategoryFragment.this);
            }
        });
    }
}