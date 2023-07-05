package com.example.javahealthify.ui.screens.community_share_achievement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.javahealthify.databinding.FragmentCommunityShareAchievementBinding;
import com.example.javahealthify.ui.screens.MainVM;
import com.example.javahealthify.ui.screens.community.CommunityVM;
import com.example.javahealthify.ui.screens.workout_exercise_practicing.PracticingOnBackDialogInterface;
import com.example.javahealthify.utils.GlobalMethods;

import java.util.Date;

public class CommunityShareAchievementFragment extends Fragment {
    private FragmentCommunityShareAchievementBinding binding;
    private WorkoutShareAchievementVM viewModel;
    private CommunityVM communityVM;
    private MainVM mainVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCommunityShareAchievementBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(WorkoutShareAchievementVM.class);
        mainVM = new ViewModelProvider(requireActivity()).get(MainVM.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        communityVM = new ViewModelProvider(requireActivity()).get(CommunityVM.class);

        binding.achievementLayout.nameTv.setText(mainVM.getUser().getValue().getName());
        binding.achievementLayout.dateTv.setText(GlobalMethods.convertDateToHyphenSplittingFormat(new Date()));
        binding.achievementLayout.achievementMenuBtn.setVisibility(View.GONE);

        viewModel.getWarningDialogMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.equals("")) {
                    PracticingOnBackDialogInterface action = new PracticingOnBackDialogInterface() {
                        @Override
                        public void onPositiveButton() {

                        }

                        @Override
                        public void onNegativeButton() {

                        }
                    };
                    GlobalMethods.showWarningDialog(requireContext(), s, action);
                }
            }
        });

        viewModel.getIsAddedSuccessfully().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isAdded) {
                if (isAdded) {
                    Toast.makeText(requireContext(), "Congratulate! You have added an achievement.", Toast.LENGTH_SHORT).show();
                    communityVM.loadAchievements();
                    GlobalMethods.backToPreviousFragment(CommunityShareAchievementFragment.this);
                }
            }
        });

        setOnClick();

        return binding.getRoot();
    }

    private void setOnClick() {
        binding.appBar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalMethods.backToPreviousFragment(CommunityShareAchievementFragment.this);
            }
        });

        binding.submitBtn.setOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.addAchievementToDb(mainVM.getUser().getValue());
            }
        });
    }
}