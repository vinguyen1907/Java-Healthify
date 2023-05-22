package com.example.javahealthify.ui.screens.add_personal_ingredient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.javahealthify.R;

public class AddPersonalIngredientFragment extends Fragment {

    public AddPersonalIngredientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_personal_ingredient, container, false);
    }
}