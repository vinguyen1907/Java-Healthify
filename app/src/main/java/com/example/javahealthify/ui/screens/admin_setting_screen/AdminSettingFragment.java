package com.example.javahealthify.ui.screens.admin_setting_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.javahealthify.databinding.FragmentAdminSettingBinding;
import com.example.javahealthify.ui.screens.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AdminSettingFragment extends Fragment {

    FragmentAdminSettingBinding binding;

    public AdminSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminSettingBinding.inflate(inflater, container, false);
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
}