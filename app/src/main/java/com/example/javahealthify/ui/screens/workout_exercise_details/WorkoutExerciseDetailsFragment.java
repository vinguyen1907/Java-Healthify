package com.example.javahealthify.ui.screens.workout_exercise_details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.javahealthify.data.models.Exercise;
import com.example.javahealthify.databinding.FragmentWorkoutExerciseDetailsBinding;
import com.example.javahealthify.utils.GlideAppModule;
import com.example.javahealthify.utils.GlobalMethods;

public class WorkoutExerciseDetailsFragment extends Fragment {
    private FragmentWorkoutExerciseDetailsBinding binding;
    private WorkoutExerciseDetailsVM viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentWorkoutExerciseDetailsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(WorkoutExerciseDetailsFragment.this).get(WorkoutExerciseDetailsVM.class);
        binding.setExerciseDetailsVM(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        Exercise exercise = WorkoutExerciseDetailsFragmentArgs.fromBundle(getArguments()).getExercise();
        viewModel.setExercise(exercise);
        Glide.with(requireContext()).load(exercise.getImageUrl()).into(binding.exerciseDetailsImage);
        binding.exerciseDetailsTimeOrRepTv.setText(GlobalMethods.formatTimeOrRep(exercise.getCountNumber(), exercise.getUnit()));

        setOnClick();

        return binding.getRoot();
    }

    private void setOnClick() {
        binding.appBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalMethods.backToPreviousFragment(WorkoutExerciseDetailsFragment.this);
            }
        });
    }
}