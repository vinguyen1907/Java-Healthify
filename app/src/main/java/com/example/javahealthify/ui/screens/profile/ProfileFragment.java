package com.example.javahealthify.ui.screens.profile;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.User;

public class ProfileFragment extends Fragment {

    private ProfileVM profileVM;
    private TextView nameTextView;
    private TextView emailTextView;

    private AppCompatButton personalInfoBtn;
    private AppCompatButton changeGoalsBtn;
    private NavController navController;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileVM = new ViewModelProvider(this).get(ProfileVM.class);
        profileVM.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                nameTextView.setText(user.getName());
                emailTextView.setText(user.getEmail());
            }
        });


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameTextView = view.findViewById(R.id.profile_name_tv);
        emailTextView = view.findViewById(R.id.profile_email_tv);

        return view;
    }
}