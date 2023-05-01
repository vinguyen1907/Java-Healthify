package com.example.javahealthify.ui.screens.profile_change_goals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.FragmentProfileChangeGoalsBinding;
import com.example.javahealthify.ui.screens.profile_calories_history.ProfileCaloriesHistoryFragment;

public class ProfileChangeGoalsFragment extends Fragment {

    private FragmentProfileChangeGoalsBinding binding;


    public ProfileChangeGoalsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileChangeGoalsBinding.inflate(inflater,container,false);
        binding.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(ProfileChangeGoalsFragment.this);
                navController.popBackStack();
            }
        });
        return binding.getRoot();
    }
}