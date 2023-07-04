package com.example.javahealthify.ui.screens.community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.javahealthify.R;
import com.example.javahealthify.data.adapters.AchievementsListAdapter;
import com.example.javahealthify.data.models.Achievement;
import com.example.javahealthify.databinding.FragmentCommunityBinding;
import com.example.javahealthify.ui.interfaces.ActionOnAchievementMenu;
import com.example.javahealthify.ui.screens.community.CommunityFragmentDirections;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment implements ActionOnAchievementMenu {
    private FragmentCommunityBinding binding;
    private CommunityVM viewModel;
    private AchievementsListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCommunityBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CommunityVM.class);
        binding.setCommunityVM(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        adapter = new AchievementsListAdapter(requireContext(), new ArrayList<>(), NavHostFragment.findNavController(this), this);
        binding.achievementLst.setAdapter(adapter);
        binding.achievementLst.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getAchievements().observe(getViewLifecycleOwner(), new Observer<List<Achievement>>() {
            @Override
            public void onChanged(List<Achievement> achievements) {
                adapter.addAll(achievements);
            }
        });

        setOnClick();

        return binding.getRoot();
    }

    private void setOnClick() {
        binding.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CommunityFragment.this).navigate(R.id.action_communityFragment_to_workoutShareAchievementFragment);
            }
        });

        binding.followingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CommunityFragment.this).navigate(R.id.action_communityFragment_to_communityFollowingFragment2);
            }
        });

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CommunityFragment.this).navigate(R.id.action_communityFragment_to_communitySearchFragment);
            }
        });
    }

    @Override
    public void showPopupMenu(Achievement achievement, View button) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), button);
        popupMenu.inflate(R.menu.achievement_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.achievement_menu_1:
                        CommunityFragmentDirections.ActionCommunityFragmentToCommunityReportFragment ToCommunityReportFragmentAction =
                                CommunityFragmentDirections.actionCommunityFragmentToCommunityReportFragment(achievement);
                        NavHostFragment.findNavController(CommunityFragment.this).navigate(ToCommunityReportFragmentAction);
                        return true;
                    case R.id.achievement_menu_2:
                        CommunityFragmentDirections.ActionCommunityFragmentToCommunityUserProfileFragment ToCommunityUserProfileFragmentAction =
                                CommunityFragmentDirections.actionCommunityFragmentToCommunityUserProfileFragment(achievement.getUserId());
                        NavHostFragment.findNavController(CommunityFragment.this).navigate(ToCommunityUserProfileFragmentAction);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }
}