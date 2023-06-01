package com.example.javahealthify.ui.screens.new_ingredient_added_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.javahealthify.R;

public class NewIngredientAddedFragment extends Fragment {

    public NewIngredientAddedFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_ingredient_added, container, false);
    }
}