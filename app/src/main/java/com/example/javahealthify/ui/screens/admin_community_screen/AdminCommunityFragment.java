package com.example.javahealthify.ui.screens.admin_community_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.javahealthify.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminCommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminCommunityFragment extends Fragment {

    public AdminCommunityFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_community, container, false);
    }
}