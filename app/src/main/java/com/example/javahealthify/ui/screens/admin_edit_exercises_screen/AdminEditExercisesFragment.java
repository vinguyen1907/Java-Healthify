package com.example.javahealthify.ui.screens.admin_edit_exercises_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.Exercise;
import com.example.javahealthify.databinding.FragmentAdminEditExercisesBinding;
import com.example.javahealthify.ui.screens.admin_workout_screen.AdminWorkoutVM;
import com.example.javahealthify.utils.GlobalMethods;

import java.util.ArrayList;

public class AdminEditExercisesFragment extends Fragment implements AdminExerciseRecyclerViewAdapter.OnEditClick, AdminExerciseRecyclerViewAdapter.OnInfoClick, AdminExerciseRecyclerViewAdapter.OnDeleteClick {

    FragmentAdminEditExercisesBinding binding;
    AdminWorkoutVM adminWorkoutVM;

    AdminExerciseRecyclerViewAdapter adapter;



    public AdminEditExercisesFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewModelProvider provider = new ViewModelProvider(requireActivity());
        adminWorkoutVM = provider.get(AdminWorkoutVM.class);
        binding = FragmentAdminEditExercisesBinding.inflate(inflater, container, false);
        adapter = new AdminExerciseRecyclerViewAdapter(requireContext(), adminWorkoutVM.getExercises().getValue(), this, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.exerciseRecyclerView.setLayoutManager(linearLayoutManager);
        binding.exerciseRecyclerView.setAdapter(adapter);
        binding.setViewModel(adminWorkoutVM);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.appBar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminWorkoutVM.clearExerciseList();

                GlobalMethods.backToPreviousFragment(AdminEditExercisesFragment.this);
            }
        });

        binding.addExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("operation", "add");
                NavHostFragment.findNavController(AdminEditExercisesFragment.this).navigate(R.id.action_adminEditExercisesFragment_to_addExerciseFragment, bundle);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adminWorkoutVM.getExercises().observe(getViewLifecycleOwner(), new Observer<ArrayList<Exercise>>() {
            @Override
            public void onChanged(ArrayList<Exercise> exercises) {
                adapter.setExerciseArrayList(exercises);
            }
        });
    }

    @Override
    public void onDelete(int position) {
//        adminWorkoutVM.deleteExercise(adminWorkoutVM.getExercises().getValue().get(position).getId(), position);
    }

    @Override
    public void onInfo(int position) {

    }

    @Override
    public void onEdit(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("operation", "edit");
        bundle.putInt("position", position);
        NavHostFragment.findNavController(AdminEditExercisesFragment.this).navigate(R.id.action_adminEditExercisesFragment_to_addExerciseFragment, bundle);
    }
}