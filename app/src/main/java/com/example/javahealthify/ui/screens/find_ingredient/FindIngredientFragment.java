package com.example.javahealthify.ui.screens.find_ingredient;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        binding.setLifecycleOwner(this);
        operation = requireArguments().getString("operation");


        IngredientNameRecyclerViewAdapter adapter = new IngredientNameRecyclerViewAdapter(this.getContext(), findIngredientVM.ingredientInfoArrayList.getValue(), this, this);
        binding.ingredientSearchResults.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.ingredientSearchResults.setAdapter(adapter);
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
                adapter.setIngredientInfoArrayList(ingredientInfoArrayList);
            }
        });
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



    private void showResult(String searchQuery) {
        findIngredientVM.search(searchQuery);

    }

    @Override
    public void onViewIngredientInfoClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
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
        tempIngredient.setName(selectedIngredientInfo.getShortDescription());
        tempIngredient.setProtein(selectedIngredientInfo.getProtein() * tempIngredient.getWeight() / 100);
        tempIngredient.setLipid(selectedIngredientInfo.getLipid() * tempIngredient.getWeight() / 100);
        tempIngredient.setCarb(selectedIngredientInfo.getCarbs() * tempIngredient.getWeight() / 100);
        tempIngredient.setCalories(selectedIngredientInfo.getCalories() * tempIngredient.getWeight() / 100);
        return tempIngredient;
    }

    @Override
    public void onIngredientInfoNameClick(int position) {
        IngredientInfo selectedIngredientInfo = findIngredientVM.ingredientInfoArrayList.getValue().get(position);
        Ingredient tempIngredient = createTempIngredient(selectedIngredientInfo);
        addToIngredients(tempIngredient);
        findIngredientVM.ingredientInfoArrayList.setValue(new ArrayList<>());
        GlobalMethods.backToPreviousFragment(FindIngredientFragment.this);
    }


}