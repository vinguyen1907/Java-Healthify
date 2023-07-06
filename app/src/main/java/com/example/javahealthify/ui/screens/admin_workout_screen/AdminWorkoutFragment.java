package com.example.javahealthify.ui.screens.admin_workout_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.javahealthify.databinding.FragmentAdminWorkoutBinding;

public class AdminWorkoutFragment extends Fragment {

    FragmentAdminWorkoutBinding binding;
    public AdminWorkoutFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminWorkoutBinding.inflate(inflater,container,false);
//        binding.adminLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth mAuth = FirebaseAuth.getInstance();
//                mAuth.signOut();
////                NavHostFragment.findNavController(AdminWorkoutFragment.this).navigate(R.id.signUpFragment);
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        });
        return binding.getRoot();
    }
}