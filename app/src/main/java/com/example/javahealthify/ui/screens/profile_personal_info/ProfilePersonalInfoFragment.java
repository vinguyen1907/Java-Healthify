package com.example.javahealthify.ui.screens.profile_personal_info;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
        profilePersonalInfoVM.getUserLiveData();


//        user = mainVM.getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfilePersonalInfoBinding.inflate(inflater,container,false);
        binding.setViewModel(profilePersonalInfoVM);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        loadData();




        binding.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(ProfilePersonalInfoFragment.this);
                navController.popBackStack();
            }
        });
        return binding.getRoot();
    }

    private void loadData() {
        profilePersonalInfoVM.getIsLoadingData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoadingData) {
                if (isLoadingData != null && !isLoadingData) {
                    binding.personalprofileNameTv.setText(profilePersonalInfoVM.getUser().getName());
                    binding.personalprofileEmailTv.setText(profilePersonalInfoVM.getUser().getEmail());
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String dateStr = formatter.format(profilePersonalInfoVM.getUser().getDateOfBirth());
                    binding.personalprofileBirthdayTv.setText(dateStr);
                    binding.personalprofilePhoneTv.setText(profilePersonalInfoVM.getUser().getPhone());
                    binding.personalprofileAddressTv.setText(profilePersonalInfoVM.getUser().getAddress());


                } else {

                }
            }
        });

    }

}