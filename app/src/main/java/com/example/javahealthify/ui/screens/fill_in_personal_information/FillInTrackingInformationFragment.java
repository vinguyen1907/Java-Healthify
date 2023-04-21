package com.example.javahealthify.ui.screens.fill_in_personal_information;



import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.FragmentFillInTrackingInformationBinding;

import java.util.Calendar;

public class FillInTrackingInformationFragment extends Fragment {

    private FragmentFillInTrackingInformationBinding binding;
    private FillInPersonalInformationVM viewModel;
    private int day;
    private int month;
    private int year;

    public FillInTrackingInformationFragment() {
        final Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFillInTrackingInformationBinding.inflate(inflater, container, false);

        viewModel =  new ViewModelProvider(requireActivity()).get(FillInPersonalInformationVM.class);

        binding.setPersonalInformationVM(viewModel);
        binding.setLifecycleOwner(this);

        setOnClick();

        viewModel.getIsSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess == true) {
                    NavHostFragment.findNavController(FillInTrackingInformationFragment.this).navigate(R.id.homeFragment);
                }
            }
        });

        viewModel.getMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }

    private void setOnClick() {
        binding.goalTimeEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                                binding.goalTimeEdt.setText(i2 + "/" + i1 + "/" + i);
                                viewModel.setGoalTime(i2 + "/" + i1 + "/" + i);
                                day = i2;
                                month = i1;
                                year = i;
                            }
                        }, year, month, day
                );
                datePickerDialog.show();
            }
        });

        binding.currentWeightEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FillInTrackingInformationFragment.this).navigate(R.id.currentWeightPickerBottomSheetFragment);
            }
        });

        binding.currentHeightEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FillInTrackingInformationFragment.this).navigate(R.id.currentHeightPickerBottomSheetFragment);
            }
        });

        binding.goalWeightEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FillInTrackingInformationFragment.this).navigate(R.id.goalWeightPickerBottomSheetFragment);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}