package com.example.javahealthify.ui.screens.profile_change_noti_time;

import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.javahealthify.R;
import com.example.javahealthify.databinding.FragmentProfileChangeNotiTimeBinding;
import com.example.javahealthify.ui.screens.MainActivity;
import com.example.javahealthify.ui.screens.profile_calories_history.ProfileCaloriesHistoryFragment;


public class ProfileChangeNotiTimeFragment extends Fragment {
    private FragmentProfileChangeNotiTimeBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileChangeNotiTimeBinding.inflate(inflater,container,false);
        binding.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(ProfileChangeNotiTimeFragment.this);
                navController.popBackStack();
            }
        });

        binding.weightEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    final int DRAWABLE_RIGHT = 2;
                    if (motionEvent.getRawX() >= (binding.weightEdt.getRight() - binding.weightEdt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        final Calendar calendar = Calendar.getInstance();
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        String time = String.format("%02d:%02d", hourOfDay, minute);
                                        binding.weightEdt.setText(time);
                                    }
                                }, hour, minute, true);

                        timePickerDialog.show();
                        return true;
                    }
                }
                return false;
            }
        });
        binding.weightEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.weightEdt.getRight() - binding.weightEdt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        final Calendar calendar = Calendar.getInstance();
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        String time = String.format("%02d:%02d", hourOfDay, minute);
                                        binding.weightEdt.setText(time);
                                    }
                                }, hour, minute, true);

                        timePickerDialog.show();
                    }
                }
                return false;
            }
        });

        binding.mealEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String time = String.format("%02d:%02d", hourOfDay, minute);
                                binding.mealEdt.setText(time);
                            }
                        }, hour, minute, true);

                timePickerDialog.show();
            }
        });
        binding.mealEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.mealEdt.getRight() - binding.mealEdt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        final Calendar calendar = Calendar.getInstance();
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        String time = String.format("%02d:%02d", hourOfDay, minute);
                                        binding.mealEdt.setText(time);
                                    }
                                }, hour, minute, true);

                        timePickerDialog.show();
                    }
                }
                return false;
            }
        });

        return binding.getRoot();
    }
}