package com.example.javahealthify.ui.screens.profile_setting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.FragmentProfileSettingBinding;
import com.example.javahealthify.ui.screens.profile.ProfileFragment;
import com.example.javahealthify.ui.screens.profile_calories_history.ProfileCaloriesHistoryFragment;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileSettingFragment extends Fragment {
    private FragmentProfileSettingBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileSettingBinding.inflate(inflater,container,false);
        binding.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(ProfileSettingFragment.this);
                navController.popBackStack();
            }
        });

        binding.changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileSettingFragment.this).navigate(R.id.action_profileSettingFragment_to_profileChangePassFragment2);
            }
        });

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                NavHostFragment.findNavController(ProfileSettingFragment.this).navigate(R.id.action_profileSettingFragment_to_signInFragment);
            }
        });
        return binding.getRoot();
    }
}