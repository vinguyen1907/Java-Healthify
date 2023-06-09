package com.example.javahealthify.ui.screens.find_ingredient;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.Ingredient;
import com.example.javahealthify.data.models.IngredientInfo;
import com.example.javahealthify.databinding.FragmentFindIngredientBinding;
import com.example.javahealthify.ui.screens.add_meal.AddMealVM;
import com.example.javahealthify.ui.screens.edit_meal.EditMealVM;
import com.example.javahealthify.utils.GlobalMethods;

import java.util.ArrayList;

public class FindIngredientFragment extends Fragment implements IngredientNameRecyclerViewAdapter.ViewIngredientInfoClickListener, IngredientNameRecyclerViewAdapter.IngredientInfoNameClickListener {
    private FindIngredientVM findIngredientVM;
    private String operation;
    private AddMealVM addMealVM;
    private EditMealVM editMealVM;
    private FragmentFindIngredientBinding binding;
    private IngredientNameRecyclerViewAdapter adapter;
    private IngredientNameRecyclerViewAdapter personalIngredientAdapter;

    public FindIngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewModelProvider provider = new ViewModelProvider(requireActivity());
        findIngredientVM = provider.get(FindIngredientVM.class);
        addMealVM = provider.get(AddMealVM.class);
        editMealVM = provider.get(EditMealVM.class);
        binding = FragmentFindIngredientBinding.inflate(inflater, container, false);
        binding.setViewModel(findIngredientVM);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        operation = requireArguments().getString("operation");
        binding.ingredientSearchResults.setVisibility(View.GONE);
        binding.personalIngredientSearchResults.setVisibility(View.GONE);
        binding.personalIngredientTv.setVisibility(View.GONE);

        adapter = new IngredientNameRecyclerViewAdapter(this.getContext(), findIngredientVM.ingredientInfoArrayList.getValue(), this, this);

        binding.ingredientSearchResults.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.ingredientSearchResults.setAdapter(adapter);
        personalIngredientAdapter = new IngredientNameRecyclerViewAdapter(this.getContext(), findIngredientVM.personalIngredientInfoArrayList.getValue(), this, this);
        binding.personalIngredientSearchResults.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.personalIngredientSearchResults.setAdapter(personalIngredientAdapter);

        binding.findIngredientBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalMethods.backToPreviousFragment(FindIngredientFragment.this);
            }
        });
        binding.addOwnIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FindIngredientFragment.this).navigate(R.id.action_findIngredientFragment_to_addPersonalIngredientFragment);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findIngredientVM.fetchFavoriteIngredients();
        binding.findIngredientSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String searchQuery = binding.findIngredientSearch.getText().toString();
                showResult(searchQuery);
                return true;
            }
        });
        findIngredientVM.getIngredientInfoArrayList().observe(getViewLifecycleOwner(), new Observer<ArrayList<IngredientInfo>>() {
            @Override
            public void onChanged(ArrayList<IngredientInfo> ingredientInfoArrayList) {
                if (ingredientInfoArrayList.isEmpty()) {
                    binding.searchResultsTv.setText("");
                    binding.ingredientSearchResults.setVisibility(View.GONE);
                } else {
                    binding.searchResultsTv.setText("Results");

                    binding.ingredientSearchResults.setVisibility(View.VISIBLE);

                    adapter.setIngredientInfoArrayList(ingredientInfoArrayList);

                }
            }
        });
        findIngredientVM.getPersonalIngredientInfoArrayList().observe(getViewLifecycleOwner(), new Observer<ArrayList<IngredientInfo>>() {
            @Override
            public void onChanged(ArrayList<IngredientInfo> ingredientInfoArrayList) {
                if (ingredientInfoArrayList.isEmpty()) {
                    binding.personalIngredientTv.setVisibility(View.GONE);
                    binding.personalIngredientSearchResults.setVisibility(View.GONE);
                } else {
                    binding.personalIngredientTv.setVisibility(View.VISIBLE);

                    binding.personalIngredientSearchResults.setVisibility(View.VISIBLE);
                    personalIngredientAdapter.setIngredientInfoArrayList(ingredientInfoArrayList);
                }
            }
        });

        findIngredientVM.favoriteIngredient.observe(getViewLifecycleOwner(), new Observer<ArrayList<IngredientInfo>>() {
            @Override
            public void onChanged(ArrayList<IngredientInfo> ingredientInfoArrayList) {
                if(findIngredientVM.ingredientInfoArrayList.getValue() == null || findIngredientVM.ingredientInfoArrayList.getValue().isEmpty()) {
                    binding.searchResultsTv.setText("Favorite Ingredients");
                    if(ingredientInfoArrayList.isEmpty()) {
                        binding.ingredientSearchResults.setVisibility(View.GONE);
                    } else {
                        binding.ingredientSearchResults.setVisibility(View.VISIBLE);

                    }
                    adapter.setIngredientInfoArrayList(ingredientInfoArrayList);
                }
            }
        });
    }

    private void showResult(String searchQuery) {
        findIngredientVM.search(searchQuery);
    }

    @Override
    public void onViewIngredientInfoClick(int position, int recyclerViewId) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        if (recyclerViewId == binding.ingredientSearchResults.getId()) {
            Log.d("globalId", "onViewIngredientInfoClick: " + recyclerViewId);
            bundle.putString("type", "global");
        } else {
            bundle.putString("type", "personal");
            Log.d("personalId", "onViewIngredientInfoClick: " + recyclerViewId);

        }
        NavHostFragment.findNavController(this).navigate(R.id.action_findIngredientFragment_to_ingredientInfoFragment, bundle);
    }

    private void addToIngredients(Ingredient tempIngredient) {
        Log.d("New ingredient name", "addToIngredients: " + tempIngredient.getName());
        ArrayList<Ingredient> tempList;
        if (operation.equals("add")) {
            tempList = addMealVM.getIngredients().getValue();
        } else {
            tempList = editMealVM.getIngredients().getValue();
        }
        if (tempList != null) {
            tempList.add(tempIngredient);
        } else {
            tempList = new ArrayList<>();
            tempList.add(tempIngredient);
        }
        if (operation.equals("add")) {
            addMealVM.getIngredients().postValue(tempList);
        } else {
            editMealVM.getIngredients().postValue(tempList);
        }
    }

    private Ingredient createTempIngredient(IngredientInfo selectedIngredientInfo) {
        Ingredient tempIngredient = new Ingredient();
        tempIngredient.setWeight(100);
        tempIngredient.setName(selectedIngredientInfo.getShort_Description());
        tempIngredient.setProtein(selectedIngredientInfo.getProtein() * tempIngredient.getWeight() / 100);
        tempIngredient.setLipid(selectedIngredientInfo.getLipid() * tempIngredient.getWeight() / 100);
        tempIngredient.setCarb(selectedIngredientInfo.getCarbs() * tempIngredient.getWeight() / 100);
        tempIngredient.setCalories(selectedIngredientInfo.getCalories() * tempIngredient.getWeight() / 100);
        return tempIngredient;
    }

    @Override
    public void onIngredientInfoNameClick(int position, int recyclerViewId) {
        Log.d("globalID", String.valueOf(binding.ingredientSearchResults.getId()));
        Log.d("personalID", String.valueOf(binding.personalIngredientSearchResults.getId()));

        IngredientInfo selectedIngredientInfo = new IngredientInfo();
        if (recyclerViewId == binding.ingredientSearchResults.getId()) {
            Log.d("clickedId", String.valueOf(recyclerViewId));

            if (findIngredientVM.ingredientInfoArrayList.getValue() == null || findIngredientVM.ingredientInfoArrayList.getValue().isEmpty()) {
                selectedIngredientInfo = findIngredientVM.favoriteIngredient.getValue().get(position);
            } else {
                selectedIngredientInfo = findIngredientVM.ingredientInfoArrayList.getValue().get(position);
            }
        } else {
            Log.d("clickedId", String.valueOf(recyclerViewId));

            selectedIngredientInfo = findIngredientVM.personalIngredientInfoArrayList.getValue().get(position);
        }
        Ingredient tempIngredient = createTempIngredient(selectedIngredientInfo);
        addToIngredients(tempIngredient);
        findIngredientVM.ingredientInfoArrayList.setValue(new ArrayList<>());
        GlobalMethods.backToPreviousFragment(FindIngredientFragment.this);
    }

}