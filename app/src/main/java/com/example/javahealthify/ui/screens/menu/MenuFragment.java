package com.example.javahealthify.ui.screens.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.FragmentMenuBinding;

public class MenuFragment extends Fragment {
    MenuVM viewModel;
    private FragmentMenuBinding binding;
    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(MenuFragment.this).get(MenuVM.class);
        binding.setMenuVM(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.setupForTesting();
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.meals);
        DishRecycleViewAdapter adapter = new DishRecycleViewAdapter(this.getContext(), viewModel.getDishes().getValue());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        return view;
    }
}