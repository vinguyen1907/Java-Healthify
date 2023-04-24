package com.example.javahealthify.ui.screens.profile_personal_info;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.FragmentProfileBinding;
import com.example.javahealthify.databinding.FragmentProfilePersonalInfoBinding;
import com.example.javahealthify.ui.screens.profile.ProfileFragment;

public class ProfilePersonalInfoFragment extends Fragment {
    private @NonNull FragmentProfilePersonalInfoBinding binding;

    public ProfilePersonalInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfilePersonalInfoBinding.inflate(inflater,container,false);
        binding.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(ProfilePersonalInfoFragment.this);
                navController.popBackStack();
            }
        });
        return binding.getRoot();
    }

}