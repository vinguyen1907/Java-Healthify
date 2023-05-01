package com.example.javahealthify.ui.screens.workout;

import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.javahealthify.R;
import com.example.javahealthify.data.adapters.WorkoutPageAdapter;
import com.example.javahealthify.databinding.FragmentWorkoutBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class WorkoutFragment extends Fragment {
    private FragmentWorkoutBinding binding;
    private WorkoutVM viewModel;
    private WorkoutPageAdapter adapter;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    private int[] tabTitles = {
        R.string.tab_title_beginner,
        R.string.tab_title_intermediate,
        R.string.tab_title_advance
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentWorkoutBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(WorkoutVM.class);
        binding.setWorkoutVM(viewModel);
        binding.setLifecycleOwner(this);

        adapter = new WorkoutPageAdapter(this);
        binding.viewPager.setAdapter(adapter);

        setUpTabLayoutWithViewPager();

        return binding.getRoot();
    }

    private void setUpTabLayoutWithViewPager() {
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,  (tab, position) ->
            tab.setText(tabTitles[position])
        ).attach();

        for (int i = 0; i < 3; i++) {
            RelativeLayout tabView = (RelativeLayout) LayoutInflater.from(requireContext()).inflate(R.layout.workout_tab_title, null);

            TabLayout.Tab  tab = binding.tabLayout.getTabAt(i);
            if (tab != null) {
                tabView.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                tabView.setPadding(0, 0, 0, 0);
                tab.setCustomView(tabView);
            }
        }
    }
}