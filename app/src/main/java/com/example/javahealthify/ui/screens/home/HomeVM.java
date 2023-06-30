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

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private NormalUser user = new NormalUser();
    private MutableLiveData<Integer> steps = new MutableLiveData<>(0);
    private MutableLiveData<Integer> exerciseCalories = new MutableLiveData<>();
    private MutableLiveData<Integer> foodCalories = new MutableLiveData<>();

    public LiveData<Integer> getSteps() {
        return steps;
    }

    public LiveData<Integer> getExerciseCalories() {
        return exerciseCalories;
    }

    public LiveData<Integer> getFoodCalories() {
        return foodCalories;
    }

    public NormalUser getUser() {
        return user;
    }

    public MutableLiveData<Boolean> getIsLoadingData() {
        return isLoadingData;
    }

    public void saveDailySteps(int stepCount) {
        // Tạo một đối tượng Map để đại diện cho các trường trong daily_activities
        Map<String, Object> dailyActivities = new HashMap<>();
        dailyActivities.put("steps", stepCount); // Giả sử giá trị steps là 5000

        // Lưu giá trị vào collection daily_activities với tên document là ngày hiện tại
        Date currentDate = new Date();
        String dateString = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(currentDate);
        firestore.collection("users")
                .document(this.getUser().getUid())
                .collection("daily_activities").document(dateString)
                .set(dailyActivities)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("success","Lưu giá trị thành công");
                        // Lưu giá trị thành công
                        // Thực hiện các tác vụ khác (nếu cần)
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("success","Lưu giá trị thất bại");
                        Log.i("bug",e.toString());
                        // Xử lý khi lưu giá trị thất bại
                    }
                });
    }

    public void loadDocument() {
        isLoadingData.setValue(true);
        firestore.collection("users")
                .document(this.getUser().getUid())
                .collection("daily_activities")
                .document(GlobalMethods.convertDateToHyphenSplittingFormat(new Date()))
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Log.i("buggg","vô đúng document rồi");
                        int stepsValue = documentSnapshot.getLong("steps").intValue();
                        if (documentSnapshot.contains("steps")) {
//                            int stepsValue = documentSnapshot.getLong("steps").intValue();
                            steps.setValue(stepsValue);
                            isLoadingData.setValue(false);
                            Log.i("steps", String.valueOf(stepsValue));
                        }
                        else {
                            Log.i("steps", "không vô được steps");
                        }
                        if (documentSnapshot.contains("exerciseCalories")) {
                            int exerciseCaloriesValue = documentSnapshot.getLong("exerciseCalories").intValue();
                            isLoadingData.setValue(false);
                            exerciseCalories.setValue(exerciseCaloriesValue);
                        }else {
                            Log.i("exercise", "không vô được exercise");
                        }
                        if (documentSnapshot.contains("foodCalories")) {
                            int foodCaloriesValue = documentSnapshot.getLong("foodCalories").intValue();
                            isLoadingData.setValue(false);
                            foodCalories.setValue(foodCaloriesValue);
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

//    public void getDailyActivities(){
//        CollectionReference activitiesRef =
//                firestore
//                .collection("users")
//                .document(firebaseAuth.getCurrentUser().getUid())
//                .collection("daily_activities");
//
//        activitiesRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                    // Lấy dữ liệu từ document
//                    String date = documentSnapshot.getId();
//                    steps = documentSnapshot.getLong("steps");
//                    exerciseCalories = documentSnapshot.getLong("exerciseCalories");
//                    foodCalories = documentSnapshot.getLong("foodCalories");
//                    // Hiển thị dữ liệu trong giao diện của bạn
//                    // ...
//                    Log.i("steps", String.valueOf(steps));
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                // Xử lý lỗi nếu có
//            }
//        });
//
//    }
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
