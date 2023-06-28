package com.example.javahealthify.ui.screens.community_achievement_details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.javahealthify.R;
import com.example.javahealthify.data.adapters.AchievementExercisesAdapter;
import com.example.javahealthify.data.adapters.AchievementFoodsAdapter;
import com.example.javahealthify.data.models.Exercise;
import com.example.javahealthify.databinding.FragmentAchievementDetailsBinding;
import com.example.javahealthify.utils.GlobalMethods;

import java.util.ArrayList;
import java.util.List;

public class AchievementDetailsFragment extends Fragment {
    private FragmentAchievementDetailsBinding binding;
    private AchievementDetailsVM viewModel;
    private AchievementFoodsAdapter foodsAdapter;
    private AchievementExercisesAdapter exercisesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAchievementDetailsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(AchievementDetailsVM.class);
        binding.setDetailsVM(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        viewModel.setAchievement(AchievementDetailsFragmentArgs.fromBundle(getArguments()).getAchievement());
        Glide.with(requireContext()).load(viewModel.getAchievement().getValue().getUserImageUrl()).into(binding.achievementDetailUserAvatarImg);

        setUpFoodList();

        setUpExerciseList();

        setOnClick();

        return binding.getRoot();
    }

    private void setUpFoodList() {
        foodsAdapter = new AchievementFoodsAdapter(requireContext(), new ArrayList<>());
        binding.foodLst.setAdapter(foodsAdapter);
        binding.foodLst.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void setUpExerciseList() {
        exercisesAdapter = new AchievementExercisesAdapter(requireContext(), new ArrayList<>());
        binding.exerciseLst.setAdapter(exercisesAdapter);
        binding.exerciseLst.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getExercises().observe(getViewLifecycleOwner(), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                exercisesAdapter.addAll(exercises);
            }
        });
    }

    private void setOnClick() {
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalMethods.backToPreviousFragment(AchievementDetailsFragment.this);
            }
        });
    }
}