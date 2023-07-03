package com.example.javahealthify.ui.screens.community_following;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javahealthify.R;
import com.example.javahealthify.data.adapters.CommunityFollowingAdapter;
import com.example.javahealthify.data.models.FollowingActivity;
import com.example.javahealthify.data.models.NormalUser;
import com.example.javahealthify.databinding.FragmentCommunityFollowingBinding;
import com.example.javahealthify.ui.screens.MainVM;
import com.example.javahealthify.utils.GlobalMethods;

import java.util.ArrayList;
import java.util.List;

public class CommunityFollowingFragment extends Fragment {
    private FragmentCommunityFollowingBinding binding;
    private CommunityFollowingVM viewModel;
    private MainVM mainVM;
    private CommunityFollowingAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCommunityFollowingBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CommunityFollowingVM.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        mainVM = new ViewModelProvider(requireActivity()).get(MainVM.class);
        viewModel.setFollowers(((NormalUser) mainVM.getUser().getValue()).getFollowers());

        adapter = new CommunityFollowingAdapter(requireContext(), new ArrayList<>());
        binding.followingTodayLst.setAdapter(adapter);
        binding.followingTodayLst.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getActivities().observe(getViewLifecycleOwner(), new Observer<List<FollowingActivity>>() {
            @Override
            public void onChanged(List<FollowingActivity> followingActivities) {
                adapter.addAll(followingActivities);
            }
        });

        setOnClick();

        return binding.getRoot();
    }

    private void setOnClick() {
        binding.appBar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalMethods.backToPreviousFragment(CommunityFollowingFragment.this);
            }
        });
    }
}