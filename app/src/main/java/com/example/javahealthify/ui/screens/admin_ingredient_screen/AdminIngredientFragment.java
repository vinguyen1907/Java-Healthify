package com.example.javahealthify.ui.screens.admin_ingredient_screen;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahealthify.data.models.IngredientInfo;
import com.example.javahealthify.databinding.FragmentAdminIngredientBinding;
import com.example.javahealthify.ui.screens.add_meal.AdminIngredientRecyclerViewAdapter;

import java.util.ArrayList;

public class AdminIngredientFragment extends Fragment {

    FragmentAdminIngredientBinding binding;
    AdminIngredientVM adminIngredientVM;
    AdminIngredientRecyclerViewAdapter ingredientRecyclerViewAdapter;
    private boolean isLoading = false;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public AdminIngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewModelProvider provider = new ViewModelProvider(requireActivity());
        adminIngredientVM = provider.get(AdminIngredientVM.class);
        ingredientRecyclerViewAdapter = new AdminIngredientRecyclerViewAdapter(this.getContext(), adminIngredientVM.databaseIngredientList.getValue());

        binding = FragmentAdminIngredientBinding.inflate(inflater, container, false);
        binding.setViewModel(adminIngredientVM);

        binding.adminIngredientRecyclerView.setAdapter(ingredientRecyclerViewAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        binding.adminIngredientRecyclerView.setLayoutManager(linearLayoutManager);

        binding.adminIngredientRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                Log.d("SCROLL", "onScrolled: total item count" + totalItemCount);
                lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                Log.d("SCROLL", "onScrolled: last visible item" + lastVisibleItem);

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    Log.d("SCROLL", "onScrolled: scrolled to bottom");

                    loadMore();
                    isLoading = true;
                }
            }
        });


        binding.setLifecycleOwner(this);

        return binding.getRoot();

    }

    private void loadMore() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adminIngredientVM.loadMore();
                isLoading = false;
            }
        }, 2000);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adminIngredientVM.databaseIngredientList.observe(getViewLifecycleOwner(), new Observer<ArrayList<IngredientInfo>>() {
            @Override
            public void onChanged(ArrayList<IngredientInfo> ingredientInfoArrayList) {
               ingredientRecyclerViewAdapter.setIngredientInfoArrayList(ingredientInfoArrayList);
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if(adminIngredientVM.registration != null) {
            adminIngredientVM.registration.remove();
        }
    }
}