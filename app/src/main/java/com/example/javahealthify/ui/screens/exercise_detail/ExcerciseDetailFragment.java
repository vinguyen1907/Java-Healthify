package com.example.javahealthify.ui.screens.exercise_detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.FragmentExcerciseDetailBinding;
import com.example.javahealthify.databinding.FragmentHomeBinding;
import com.example.javahealthify.ui.screens.home.HomeFragment;
import com.example.javahealthify.ui.screens.workout_categories.WorkoutCategoriesFragment;
import com.example.javahealthify.utils.GlobalMethods;

public class ExcerciseDetailFragment extends Fragment {

    private FragmentExcerciseDetailBinding binding;

    private ExerciseDetailVM exerciseDetailVM;

    public ExcerciseDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExcerciseDetailBinding.inflate(inflater,container,false);
        binding.setViewModel(exerciseDetailVM);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.exerciseDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalMethods.backToPreviousFragment(ExcerciseDetailFragment.this);
            }
        });

        return binding.getRoot();
    }
}