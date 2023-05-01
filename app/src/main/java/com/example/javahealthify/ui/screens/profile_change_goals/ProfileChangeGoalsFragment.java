package com.example.javahealthify.ui.screens.profile_change_goals;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.FragmentProfileBinding;
import com.example.javahealthify.databinding.FragmentProfileChangeGoalsBinding;
import com.example.javahealthify.ui.screens.profile_calories_history.ProfileCaloriesHistoryFragment;

import java.util.Calendar;

public class ProfileChangeGoalsFragment extends Fragment {

    private int day = 1;
    private int month = 1;
    private int year = 2000;
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

        binding.timeGoalEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                binding.timeGoalEdt.setText(i2 + "/" + i1 + "/" + i);
                                day = i2;
                                month = i1;
                                year = i;
                            }
                        }, year, month, day
                );
                datePickerDialog.show();
            }
        });

        return binding.getRoot();

    }
}