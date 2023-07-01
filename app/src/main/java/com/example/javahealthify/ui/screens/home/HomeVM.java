package com.example.javahealthify.ui.screens.home;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.NormalUser;
import com.example.javahealthify.data.models.User;
import com.example.javahealthify.data.models.WorkoutCategory;
import com.example.javahealthify.utils.GlobalMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeVM extends ViewModel {
    private MutableLiveData<Boolean> isLoadingData = new MutableLiveData<>( null);
    private MutableLiveData<Boolean> isLoadingDocument = new MutableLiveData<>( null);
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private NormalUser user = new NormalUser();
    private Integer steps = new Integer(0);
    private Integer exerciseCalories = new Integer(0);
    private Integer foodCalories = new Integer(0);

    public MutableLiveData<Boolean> getIsLoadingDocument() {
        return isLoadingDocument;
    }

    public void setIsLoadingDocument(MutableLiveData<Boolean> isLoadingDocument) {
        this.isLoadingDocument = isLoadingDocument;
    }

    public Integer getExerciseCalories() {
        return exerciseCalories;
    }

    public Integer getFoodCalories() {
        return foodCalories;
    }

    public void setExerciseCalories(Integer exerciseCalories) {
        this.exerciseCalories = exerciseCalories;
    }

    public void setFoodCalories(Integer foodCalories) {
        this.foodCalories = foodCalories;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public NormalUser getUser() {
        return user;
    }

    public MutableLiveData<Boolean> getIsLoadingData() {
        return isLoadingData;
    }
    public void saveDailySteps(int stepCount, Date previousDate) {

        Map<String, Object> dailyActivities = new HashMap<>();
        dailyActivities.put("steps", stepCount);

        String dateString = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(previousDate);
        firestore.collection("users")
                .document(this.getUser().getUid())
                .collection("daily_activities").document(dateString)
                .set(dailyActivities)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("success","Lưu giá trị thành công");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("success","Lưu giá trị thất bại");
                        Log.i("bug",e.toString());
                    }
                });
    }

    public void loadDocument() {
        isLoadingDocument.setValue(true);
        firestore.collection("users")
                .document(this.getUser().getUid())
                .collection("daily_activities")
                .document(GlobalMethods.convertDateToHyphenSplittingFormat(new Date()))
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        if (documentSnapshot.contains("steps")) {
                            int stepsValue = documentSnapshot.getLong("steps").intValue();
                            setSteps(stepsValue);
                            isLoadingDocument.setValue(false);
                            Log.i("steps", String.valueOf(stepsValue));
                        }
                        else {
                            Log.i("steps", "không vô được steps");
                        }
                        if (documentSnapshot.contains("exerciseCalories")) {
                            int exerciseCaloriesValue = documentSnapshot.getLong("exerciseCalories").intValue();
                            setExerciseCalories(exerciseCaloriesValue);
                            isLoadingDocument.setValue(false);
                        }else {
                            Log.i("exercise", "không vô được exercise");
                        }
                        if (documentSnapshot.contains("foodCalories")) {
                            int foodCaloriesValue = documentSnapshot.getLong("foodCalories").intValue();
                            setFoodCalories(foodCaloriesValue);
                            isLoadingDocument.setValue(false);
                        }else {
                            Log.i("food", "không vô được food");
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.i("Lỗi","abcdxyz");
                    // Xử lý khi không thể tải dữ liệu từ Firestore
                });
    }
    public void getUserLiveData() {
        isLoadingData.setValue(true);
        firestore.collection("users").whereEqualTo("email", firebaseAuth.getCurrentUser().getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            user = task.getResult().getDocuments().get(0).toObject(NormalUser.class);
                            isLoadingData.setValue(false);

                        }
                        else {
                            Log.d("Get user data error", "Error getting user documents: ", task.getException());
                            isLoadingData.setValue(false);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Error", e.getMessage());
                    }
                });
//        if (userLiveData == null) {
//            userLiveData = new MutableLiveData<>();
//            loadUser();
//        }
    }

//    private void loadUser() {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (firebaseUser != null) {
//            User user = new User();
//            user.setName(firebaseUser.getDisplayName());
//            user.setEmail(firebaseUser.getEmail());
//            userLiveData.setValue(user);
//        }
//    }
}
