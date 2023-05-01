package com.example.javahealthify.ui.screens.profile_calories_history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.FragmentProfileCaloriesHistoryBinding;
import com.example.javahealthify.ui.screens.profile_personal_info.ProfilePersonalInfoFragment;

public class ProfileCaloriesHistoryFragment extends Fragment {
    private FragmentProfileCaloriesHistoryBinding binding;

    public ProfileCaloriesHistoryFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileCaloriesHistoryBinding.inflate(inflater,container,false);
        binding.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(ProfileCaloriesHistoryFragment.this);
                navController.popBackStack();
            }
        });
        return binding.getRoot();
    }
}