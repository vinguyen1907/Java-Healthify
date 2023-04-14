package com.example.javahealthify.ui.screens.sign_in;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.FragmentSignInBinding;

public class SignInFragment extends Fragment {
    private FragmentSignInBinding binding;
    private SignInVM viewModel;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(SignInFragment.this).get(SignInVM.class);
        binding.setSignInVM(viewModel);
        binding.setLifecycleOwner(this);

        setOnClick();

        viewModel.getToastMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show();
                }
            }
        });

        viewModel.getSignInSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean signInSuccess) {
                if (signInSuccess) {
//                    NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.);
                    //TODO: Navigate to home
                }
            }
        });

        return binding.getRoot();
    }

    private void setOnClick() {
        binding.toSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.signUpFragment);
            }
        });
    }
}