package com.example.javahealthify.ui.screens.profile_edit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.javahealthify.data.models.NormalUser;
import com.example.javahealthify.data.models.User;
import com.example.javahealthify.databinding.FragmentEditProfileBinding;
import com.example.javahealthify.databinding.FragmentProfilePersonalInfoBinding;
import com.example.javahealthify.ui.screens.MainVM;
import com.example.javahealthify.ui.screens.profile_calories_history.ProfileCaloriesHistoryFragment;
import com.example.javahealthify.ui.screens.profile_personal_info.ProfilePersonalInfoVM;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EditProfileFragment extends Fragment {

    private EditProfileVM editProfileVM;
    private FragmentEditProfileBinding binding;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editProfileVM = new ViewModelProvider(this).get(EditProfileVM.class);

        MainVM mainVM = new ViewModelProvider(requireActivity()).get(MainVM.class);

        user = mainVM.getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater,container,false);

        binding.editnameEdt.setText(((NormalUser) user).getName());
        binding.editemailEdt.setText(((NormalUser) user).getEmail());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = formatter.format(((NormalUser) user).getDateOfBirth());
        binding.editdateEdt.setText(dateStr);
        binding.editphoneEdt.setText(((NormalUser) user).getPhone());
        binding.editaddressEdt.setText(((NormalUser) user).getAddress());


        binding.editnameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();

                if (binding.editnameEdt.getText().equals(user.getName())) {
                    binding.tickIcon1.setVisibility(View.GONE);
                } else {
                    binding.tickIcon1.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.editemailEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();

                if (binding.editemailEdt.getText().equals(user.getEmail())) {
                    binding.tickIcon2.setVisibility(View.GONE);
                } else {
                    binding.tickIcon2.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.editdateEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();

                if (binding.editdateEdt.getText().equals(((NormalUser) user).getDateOfBirth())) {
                    binding.tickIcon3.setVisibility(View.GONE);
                } else {
                    binding.tickIcon3.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.editphoneEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();

                if (binding.editphoneEdt.getText().equals(((NormalUser) user).getPhone())) {
                    binding.tickIcon4.setVisibility(View.GONE);
                } else {
                    binding.tickIcon4.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.editaddressEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();

                if (binding.editnameEdt.getText().equals(((NormalUser) user).getAddress())) {
                    binding.tickIcon5.setVisibility(View.GONE);
                } else {
                    binding.tickIcon5.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.tickIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserData(user.getUid(), binding.editnameEdt.getText().toString().trim(), binding.editphoneEdt.getText().toString().trim() );
            }
        });
//        binding.tickIcon2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                updateUserData(user.getUid(),"email",binding.editemailEdt.getText().toString().trim(), 1);
//            }
//        });
//        binding.tickIcon3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                updateUserData(user.getUid(),"name",binding.editdateEdt.getText().toString().trim(), 1);
//            }
//        });
//        binding.tickIcon4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                updateUserData(user.getUid(),"name",binding.editphoneEdt.getText().toString().trim(), 1);
//            }
//        });
//        binding.tickIcon5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                updateUserData(user.getUid(),"name",binding.editaddressEdt.getText().toString().trim(), 1);
//            }
//        });




        binding.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(EditProfileFragment.this);
                navController.popBackStack();
            }
        });
        return binding.getRoot();
    }
    public void updateUserData(String userId, String name, String phone) {
        db.collection("users")
                .document(userId)
                .update("name", name, "phone", phone)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to update user data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}