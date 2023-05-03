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
import android.util.Patterns;
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
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class EditProfileFragment extends Fragment {

    private EditProfileVM editProfileVM;

    private MainVM viewModel;


    Boolean isValidName = true;
    Boolean isValidEmail = true;
    Boolean isValidDay = true;
    Boolean isValidPhone = true;
    Boolean isValidAddress = true;
    private FragmentEditProfileBinding binding;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

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
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);

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

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newName = binding.editnameEdt.getText().toString();
                if (!newName.equals(user.getName())) {
                    if (newName.isEmpty()) {
                        binding.tickIcon1.setVisibility(View.GONE);
                        isValidName = false;
                    } else {
                        binding.tickIcon1.setVisibility(View.VISIBLE);
                        isValidName = true;
                    }
                } else {
                    binding.tickIcon1.setVisibility(View.GONE);
                    isValidName = true;
                }
            }

        });
        binding.editemailEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newEmail = binding.editemailEdt.getText().toString();
                if (!newEmail.equals(user.getEmail())) {
                    if (binding.editemailEdt.getText().toString().isEmpty()) {
                        binding.tickIcon2.setVisibility(View.GONE);
                        isValidEmail = false;
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.editemailEdt.getText().toString()).matches()) {
                        binding.tickIcon2.setVisibility(View.GONE);
                        isValidEmail = false;
                    } else {
                        binding.tickIcon2.setVisibility(View.VISIBLE);
                        isValidEmail = true;
                    }
                } else {
                    binding.tickIcon2.setVisibility(View.GONE);
                    isValidEmail = true;
                }

            }
        });
        binding.editdateEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String dateStr = formatter.format(((NormalUser) user).getDateOfBirth());

                if (!binding.editdateEdt.getText().toString().equals(dateStr)) {
                    try {
                        dateFormat.parse(binding.editdateEdt.getText().toString());
                    } catch (ParseException e) {
                        binding.tickIcon3.setVisibility(View.GONE);
                        isValidDay = false;
                        return;
                    }
                    binding.tickIcon3.setVisibility(View.VISIBLE);
                    isValidDay = true;
                } else {
                    binding.tickIcon3.setVisibility(View.GONE);
                    isValidDay = true;
                }
            }
        });
        binding.editphoneEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newPhone = binding.editphoneEdt.getText().toString();
                if (!newPhone.equals(((NormalUser) user).getPhone())) {
                    String phone = binding.editphoneEdt.getText().toString();
                    if (!Pattern.matches("[0-9]+", phone) || phone.length() < 10) {
                        binding.tickIcon4.setVisibility(View.GONE);
                        isValidPhone = false;
                    } else {
                        binding.tickIcon4.setVisibility(View.VISIBLE);
                        isValidPhone = true;
                    }
                } else {
                    binding.tickIcon4.setVisibility(View.GONE);
                    isValidPhone = true;
                }
            }
        });
        binding.editaddressEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newAddress = binding.editaddressEdt.getText().toString();
                if (!newAddress.equals(((NormalUser) user).getAddress())) {
                    if (binding.editaddressEdt.getText().toString().isEmpty()) {
                        binding.tickIcon5.setVisibility(View.GONE);
                        isValidAddress = false;
                    } else {
                        binding.tickIcon5.setVisibility(View.VISIBLE);
                        isValidAddress = true;
                    }
                } else {
                    binding.tickIcon5.setVisibility(View.GONE);
                    isValidAddress = true;
                }
            }
        });

        binding.editprofileSavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((binding.tickIcon1.getVisibility() == View.GONE
                        && binding.tickIcon2.getVisibility() == View.GONE
                        && binding.tickIcon3.getVisibility() == View.GONE
                        && binding.tickIcon4.getVisibility() == View.GONE
                        && binding.tickIcon5.getVisibility() == View.GONE)) {
                    Toast.makeText(requireContext(), "Vui lòng nhập thông tin cần thay đổi", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isValidName && isValidEmail && isValidDay && isValidPhone && isValidAddress) {
                    String dateString = binding.editdateEdt.getText().toString().trim();
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date date;
                    try {
                        date = format.parse(dateString);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    updateUserData(user.getUid(),
                            binding.editnameEdt.getText().toString().trim(),
                            binding.editemailEdt.getText().toString().trim(),
                            date,
                            binding.editphoneEdt.getText().toString().trim(),
                            binding.editaddressEdt.getText().toString().trim());
                } else {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ và đúng thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });


        binding.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(EditProfileFragment.this);
                navController.popBackStack();
            }
        });
        return binding.getRoot();
    }

    public void updateUserData(String userId, String name, String email, Date dateOfBirth, String phone, String address) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("email", email);
        data.put("dateOfBirth", new Timestamp(dateOfBirth));
        data.put("phone", phone);
        data.put("address", address);

        db.collection("users")
                .document(userId)
                .set(data, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        binding.tickIcon1.setVisibility(View.GONE);
                        binding.tickIcon2.setVisibility(View.GONE);
                        binding.tickIcon3.setVisibility(View.GONE);
                        binding.tickIcon4.setVisibility(View.GONE);
                        binding.tickIcon5.setVisibility(View.GONE);



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