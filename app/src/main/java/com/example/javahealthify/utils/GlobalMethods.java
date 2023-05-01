package com.example.javahealthify.utils;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class GlobalMethods {
    public static void backToPreviousFragment(Fragment fragment) {
        NavHostFragment.findNavController(fragment).popBackStack();
    }
}
