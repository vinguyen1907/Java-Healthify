package com.example.javahealthify.ui.screens.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.jvm.internal.Intrinsics;
public final class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        if (this.binding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }

        this.binding.excerciseDetail.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it) {
                HomeFragment.this.navigateToExerciseDetail();
            }
        }));

        return this.binding.getRoot();
    }

    private void navigateToExerciseDetail() {
        FragmentKt.findNavController(this).navigate(R.id.action_homeFragment_to_excerciseDetail);
    }
}