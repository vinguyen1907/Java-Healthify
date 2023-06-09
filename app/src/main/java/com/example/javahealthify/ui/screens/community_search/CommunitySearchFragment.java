package com.example.javahealthify.ui.screens.community_search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.javahealthify.R;
import com.example.javahealthify.data.adapters.SearchUsersAdapter;
import com.example.javahealthify.data.models.NormalUser;
import com.example.javahealthify.data.models.User;
import com.example.javahealthify.databinding.FragmentCommunitySearchBinding;
import com.example.javahealthify.utils.GlobalMethods;

import java.util.ArrayList;
import java.util.List;

public class CommunitySearchFragment extends Fragment {
    private FragmentCommunitySearchBinding binding;
    private CommunitySearchVM viewModel;
    private SearchUsersAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentCommunitySearchBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CommunitySearchVM.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        setOnClick();
        setUpResult();

        return binding.getRoot();
    }

    private void setOnClick() {
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalMethods.backToPreviousFragment(CommunitySearchFragment.this);
            }
        });

        binding.searchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = binding.searchEdt.getText().toString().toLowerCase();
                    if (!keyword.isEmpty()) {
                        viewModel.search(keyword);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void setUpResult() {
        adapter = new SearchUsersAdapter(requireContext(), new ArrayList<>(), NavHostFragment.findNavController(CommunitySearchFragment.this));
        binding.userSearchResultLst.setAdapter(adapter);
        binding.userSearchResultLst.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getResult().observe(getViewLifecycleOwner(), new Observer<List<NormalUser>>() {
            @Override
            public void onChanged(List<NormalUser> users) {
                adapter.setData(users);
            }
        });
    }
}