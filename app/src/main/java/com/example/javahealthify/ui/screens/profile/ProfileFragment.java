package com.example.javahealthify.ui.screens.profile;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.User;
import com.example.javahealthify.databinding.FragmentProfileBinding;
import com.example.javahealthify.ui.screens.MainVM;

public class ProfileFragment extends Fragment {

    private ProfileVM profileVM;
    private FragmentProfileBinding binding;

    private User user;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileVM = new ViewModelProvider(this).get(ProfileVM.class);

        MainVM mainVM = new ViewModelProvider(requireActivity()).get(MainVM.class);

        user = mainVM.getUser();

//        profileVM.getUserLiveData().observe(this, new Observer<User>() {
//            @Override
//            public void onChanged(User user) {
//                mainVM.loadUser();
//            }
//        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater,container,false);
        binding.setViewModel(profileVM);
        binding.setLifecycleOwner(this);

        binding.profileEmailTv.setText(user.getEmail());
        binding.profileNameTv.setText(user.getName());

        binding.personalInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.action_profileFragment_to_profilePersonalInfoFragment);
            }
        });

        binding.editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.action_profileFragment_to_editProfileFragment);
            }
        });

        binding.changeGoalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.action_profileFragment_to_profileChangeGoalsFragment);
            }
        });
        binding.caloriesHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.action_profileFragment_to_profileCaloriesHistoryFragment);
            }
        });
        binding.changeNotiTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.action_profileFragment_to_profileChangeNotiTimeFragment2);
            }
        });
        binding.settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.action_profileFragment_to_profileSettingFragment);
            }
        });
        return binding.getRoot();
    }
}