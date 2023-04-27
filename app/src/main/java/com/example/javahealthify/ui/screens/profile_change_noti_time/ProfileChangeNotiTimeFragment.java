package com.example.javahealthify.ui.screens.profile_change_noti_time;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.FragmentProfileChangeNotiTimeBinding;
import com.example.javahealthify.ui.screens.profile_calories_history.ProfileCaloriesHistoryFragment;


public class ProfileChangeNotiTimeFragment extends Fragment {
    private FragmentProfileChangeNotiTimeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileChangeNotiTimeBinding.inflate(inflater,container,false);
        binding.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(ProfileChangeNotiTimeFragment.this);
                navController.popBackStack();
            }
        });
        return binding.getRoot();
    }
}