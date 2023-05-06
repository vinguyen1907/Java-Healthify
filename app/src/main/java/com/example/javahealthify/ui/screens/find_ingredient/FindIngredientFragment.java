package com.example.javahealthify.ui.screens.find_ingredient;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.javahealthify.data.models.Ingredient;
import com.example.javahealthify.data.models.IngredientInfo;
import com.example.javahealthify.databinding.FragmentFindIngredientBinding;
import com.example.javahealthify.ui.screens.add_meal.AddMealVM;
import com.example.javahealthify.utils.GlobalMethods;

import java.util.ArrayList;

public class FindIngredientFragment extends Fragment implements IngredientNameRecyclerViewAdapter.ViewIngredientInfoClickListener, IngredientNameRecyclerViewAdapter.IngredientInfoNameClickListener {
    private FindIngredientVM findIngredientVM;
    private AddMealVM addMealVM;
    private FragmentFindIngredientBinding binding;

    public FindIngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewModelProvider provider = new ViewModelProvider(requireActivity());
        findIngredientVM = provider.get(FindIngredientVM.class);
        addMealVM = provider.get(AddMealVM.class);
        binding = FragmentFindIngredientBinding.inflate(inflater, container, false);
        binding.setViewModel(findIngredientVM);
        binding.setLifecycleOwner(this);

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
        return binding.getRoot();
    }

    private void showResult(String searchQuery) {
        findIngredientVM.search(searchQuery);

    }

    @Override
    public void onViewIngredientInfoClick(int position) {

    }

    @Override
    public void onIngredientInfoNameClick(int position) {
//        // add to ingredient list
        IngredientInfo selectedIngredientInfo = findIngredientVM.ingredientInfoArrayList.getValue().get(position);
//        Log.d("INGREDIENT INFO", "onIngredientInfoNameClick: " + selectedIngredientInfo.getShortDescription());
        Ingredient tempIngredient = new Ingredient();
        tempIngredient.setWeight(100);
        tempIngredient.setName(selectedIngredientInfo.getShortDescription() );
        tempIngredient.setProtein(selectedIngredientInfo.getProtein()* tempIngredient.getWeight()/100);
        tempIngredient.setLipid(selectedIngredientInfo.getLipid()* tempIngredient.getWeight()/100);
        tempIngredient.setCarb(selectedIngredientInfo.getCarbs()* tempIngredient.getWeight()/100);
        tempIngredient.setCalories(selectedIngredientInfo.getCalories()* tempIngredient.getWeight()/100);
//        Log.d("NEW INGREDIENT", "onIngredientInfoNameClick: " + tempIngredient.getName());
        ArrayList<Ingredient> tempList = addMealVM.getIngredients().getValue();
        if (tempList != null) {
            tempList.add(tempIngredient);
        } else {
            tempList = new ArrayList<>();
            tempList.add(tempIngredient);
        }
        MutableLiveData<ArrayList<Ingredient>> newList = new MutableLiveData<ArrayList<Ingredient>>();
        newList.setValue(tempList);
        addMealVM.setIngredients(newList);
//        Log.d("NEW INGREDIENT LIST", "onIngredientInfoNameClick: " + addMealVM.getIngredients().getValue().size());
        GlobalMethods.backToPreviousFragment(FindIngredientFragment.this);
    }

}