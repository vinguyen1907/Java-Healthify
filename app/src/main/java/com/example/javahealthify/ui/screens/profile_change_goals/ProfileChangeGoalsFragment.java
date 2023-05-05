package com.example.javahealthify.ui.screens.profile_change_goals;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.NormalUser;
import com.example.javahealthify.data.models.User;
import com.example.javahealthify.databinding.FragmentProfileBinding;
import com.example.javahealthify.databinding.FragmentProfileChangeGoalsBinding;
import com.example.javahealthify.ui.screens.MainVM;
import com.example.javahealthify.ui.screens.profile_calories_history.ProfileCaloriesHistoryFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfileChangeGoalsFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private int day = 1;
    private int month = 1;
    private int year = 2000;
    private FragmentProfileChangeGoalsBinding binding;

    private User user;



    public ProfileChangeGoalsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainVM mainVM = new ViewModelProvider(requireActivity()).get(MainVM.class);

        user = mainVM.getUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileChangeGoalsBinding.inflate(inflater,container,false);

        String goalWeight = String.valueOf(((NormalUser) user).getGoalWeight());
        binding.weightEdt.setText(goalWeight);
        binding.stepEdt.setText("Chưa có trong database");

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = formatter.format(((NormalUser) user).getGoalTime());
        binding.timeGoalEdt.setText(dateStr);




        binding.updateGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> data = new HashMap<>();
                String dateString = binding.timeGoalEdt.getText().toString().trim();
                Date date;
                try {
                    date = formatter.parse(dateString);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                int goalWeight = Integer.parseInt(binding.weightEdt.getText().toString());
                data.put("goalWeight", goalWeight);
//                data.put("dailySteps", binding.stepEdt.getText().toString().trim());
                data.put("goalTime",  new Timestamp(date));

                db.collection("users")
                        .document(user.getUid())
                        .set(data, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "User data updated successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("TAG", "Error updating user data", e);
                                Toast.makeText(getContext(), "Failed to update user data", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        binding.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(ProfileChangeGoalsFragment.this);
                navController.popBackStack();
            }
        });

        binding.timeGoalEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                binding.timeGoalEdt.setText(i2 + "/" + i1 + "/" + i);
                                day = i2;
                                month = i1;
                                year = i;
                            }
                        }, year, month, day
                );
                datePickerDialog.show();
            }
        });

        return binding.getRoot();

    }
}