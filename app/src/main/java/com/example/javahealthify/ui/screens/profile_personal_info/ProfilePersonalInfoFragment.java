package com.example.javahealthify.ui.screens.profile_personal_info;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.NormalUser;
import com.example.javahealthify.data.models.User;
import com.example.javahealthify.databinding.FragmentProfileBinding;
import com.example.javahealthify.databinding.FragmentProfilePersonalInfoBinding;
import com.example.javahealthify.ui.screens.MainVM;
import com.example.javahealthify.ui.screens.profile.ProfileFragment;
import com.example.javahealthify.ui.screens.profile.ProfileVM;

import java.text.SimpleDateFormat;

public class ProfilePersonalInfoFragment extends Fragment {
    private ProfilePersonalInfoVM profilePersonalInfoVM;
    private @NonNull FragmentProfilePersonalInfoBinding binding;

    private User user;

    public ProfilePersonalInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profilePersonalInfoVM = new ViewModelProvider(this).get(ProfilePersonalInfoVM.class);

        MainVM mainVM = new ViewModelProvider(requireActivity()).get(MainVM.class);

        user = mainVM.getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfilePersonalInfoBinding.inflate(inflater,container,false);


        binding.personalprofileNameTv.setText(((NormalUser) user).getName());
        binding.personalprofileEmailTv.setText(((NormalUser) user).getEmail());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = formatter.format(((NormalUser) user).getDateOfBirth());
        binding.personalprofileBirthdayTv.setText(dateStr);
        binding.personalprofileEmailTv.setText(((NormalUser) user).getEmail());
        binding.personalprofileEmailTv.setText(((NormalUser) user).getAddress());


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