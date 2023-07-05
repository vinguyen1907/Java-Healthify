package com.example.javahealthify.ui.screens.profile_calories_history;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.NormalUser;
import com.example.javahealthify.ui.screens.home.CustomEntry;
import com.example.javahealthify.utils.FirebaseConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProfileCaloriesHistoryVM extends ViewModel {
    private MutableLiveData<Boolean> isLoadingData = new MutableLiveData<>(null);

    public MutableLiveData<Boolean> getIsLoadingData() {
        return isLoadingData;
    }

    public void setIsLoadingData(MutableLiveData<Boolean> isLoadingData) {
        this.isLoadingData = isLoadingData;
    }
    private MutableLiveData<Boolean> isLoadingLine = new MutableLiveData<>(null);

    public MutableLiveData<Boolean> getIsLoadingLine() {
        return isLoadingLine;
    }

    public void setIsLoadingLine(MutableLiveData<Boolean> isLoadingLine) {
        this.isLoadingLine = isLoadingLine;
    }
    ArrayList<CustomEntry> lineEntries;
    private Integer x = 0;

    public ArrayList<CustomEntry> getLineEntries() {
        return lineEntries;
    }

    public void setLineEntries(ArrayList<CustomEntry> lineEntries) {
        this.lineEntries = lineEntries;
    }

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private NormalUser user = new NormalUser();

    public ProfileCaloriesHistoryVM() {
        loadChart();
    }

    public void loadChart() {
        isLoadingLine.setValue(true);
        firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("daily_activities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            lineEntries = new ArrayList<>();
                            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                if (document.contains("calories")) {
                                    int steps = document.getLong("calories").intValue();
                                    String date = document.getId();
                                    Log.i("calories line profile", String.valueOf(steps));
                                    Log.i("date line profile", date);

                                    SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM");
                                    try {
                                        Date parsedDate = inputFormat.parse(date);
                                        date = outputFormat.format(parsedDate);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    lineEntries.add(new CustomEntry(x, steps, date));
                                    x++;
                                }
                            }
                            isLoadingLine.setValue(false);
                        } else {
                            Exception e = task.getException();
                            Log.i("bugg", "Lỗi khi lấy dữ liệu steps");
                        }
                    }
                });
    }

    public void getUserLiveData() {
        isLoadingData.setValue(true);
        FirebaseConstants.usersRef.document(firebaseAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                user = document.toObject(NormalUser.class);
                                isLoadingData.setValue(false);
                            }
                        } else {
                            Log.d("Get user data error", "Error getting user documents: ", task.getException());
                            isLoadingData.setValue(false);
                        }
                    }
                });

//        if (userLiveData == null) {
//            userLiveData = new MutableLiveData<>();
//            loadUser();
//        }
    }

}
