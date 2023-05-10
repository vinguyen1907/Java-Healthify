package com.example.javahealthify.ui.screens.workout_categories_exercises;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javahealthify.R;
import com.example.javahealthify.data.adapters.AddSelectedExercise;
import com.example.javahealthify.data.adapters.WorkoutCategoryExercisesAdapter;
import com.example.javahealthify.data.adapters.WorkoutCategorySelectedExercisesAdapter;
import com.example.javahealthify.data.models.Exercise;
import com.example.javahealthify.databinding.FragmentWorkoutCategoriesBinding;
import com.example.javahealthify.databinding.FragmentWorkoutCategoryExercisesBinding;
import com.example.javahealthify.ui.screens.workout.WorkoutVM;
import com.example.javahealthify.utils.GlobalMethods;

import java.util.List;

public class WorkoutCategoryExercisesFragment extends Fragment implements AddSelectedExercise {
    private FragmentWorkoutCategoryExercisesBinding binding;
    private WorkoutCategoryExercisesVM viewModel;
    private WorkoutVM workoutVM;
    private WorkoutCategoryExercisesAdapter adapter;
    private WorkoutCategorySelectedExercisesAdapter selectedExercisesAdapter;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(WorkoutCategoryExercisesFragment.this).get(WorkoutCategoryExercisesVM.class);
        workoutVM = new ViewModelProvider(requireActivity()).get(WorkoutVM.class);
        String categoryId = getArguments().getString("categoryId");
        viewModel.setCategoryId(categoryId);
        viewModel.loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentWorkoutCategoryExercisesBinding.inflate(inflater, container, false);
        binding.setCategoryExerciseVM(viewModel);
        binding.setWorkoutVM(workoutVM);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        navController = NavHostFragment.findNavController(WorkoutCategoryExercisesFragment.this);

        viewModel.getExercises().observe(getViewLifecycleOwner(), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                if (!exercises.isEmpty()) {
                    adapter = new WorkoutCategoryExercisesAdapter(requireContext(), viewModel.getExercises().getValue(), navController, WorkoutCategoryExercisesFragment.this);
                    binding.exerciseLst.setAdapter(adapter);
                    binding.exerciseLst.setLayoutManager(new LinearLayoutManager(requireContext()));
                }
            }
        });

        workoutVM.getSelectedTotalCalories().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer total) {
                binding.totalCaloriesTv.setText(String.valueOf(total) + " cal");
            }
        });

        selectedExercisesAdapter = new WorkoutCategorySelectedExercisesAdapter(requireContext(), workoutVM.getSelectedExercises().getValue(), navController);
        binding.selectedExerciseLst.setAdapter(selectedExercisesAdapter);
        LinearLayoutManager selectedExercisesLayoutManager = new LinearLayoutManager(requireContext()) {
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
        };
        binding.selectedExerciseLst.setLayoutManager(selectedExercisesLayoutManager);

        workoutVM.getSelectedExercises().observe(getViewLifecycleOwner(), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                selectedExercisesAdapter.notifyDataSetChanged();
            }
        });

        setOnClick();

        return binding.getRoot();
    }

    private void setOnClick() {
        binding.appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalMethods.backToPreviousFragment(WorkoutCategoryExercisesFragment.this);
            }
        });
    }

    @Override
    public void onAddSelectedExercise(Exercise exercise) {
        workoutVM.onAddSelectedExercise(exercise);
    }
}