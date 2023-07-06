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
import com.example.javahealthify.ui.screens.home_update_weight.HomeUpdateWeightVM;
import com.example.javahealthify.ui.screens.workout.WorkoutVM;
import com.example.javahealthify.utils.GlobalMethods;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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
    private MutableLiveData<Boolean> isLoadingData = new MutableLiveData<>(null);
    private MutableLiveData<Boolean> isLoadingDocument = new MutableLiveData<>(null);
    private MutableLiveData<Boolean> isLoadingLine = new MutableLiveData<>(null);
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private MutableLiveData<NormalUser> user = new MutableLiveData<>();
    private Integer steps = new Integer(0);
    private Float exerciseCalories = new Float(0);
    private Float foodCalories = new Float(0);
    private Float calories = new Float(0);

    private Float remaining = new Float(1000);
    private Float goal = new Float(1000);
    private Float startWeight = new Float(0);
    private Float goalWeight = new Float(0);
    private Float dailyCalories = new Float(0);
    private Integer weight = 0;
    ArrayList<CustomEntry> lineEntries;
    List<PieEntry> pieEntries;

    private WorkoutVM workoutVM;

    private HomeUpdateWeightVM homeUpdateWeightVM = new HomeUpdateWeightVM(100);

    public Float getCalories() {
        return calories;
    }

    public void setCalories(Float calories) {
        this.calories = calories;
    }

    public Float getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(Float startWeight) {
        this.startWeight = startWeight;
    }

    public Float getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(Float goalWeight) {
        this.goalWeight = goalWeight;
    }

    public Float getDailyCalories() {
        return dailyCalories;
    }

    public void setDailyCalories(Float dailyCalories) {
        this.dailyCalories = dailyCalories;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }
    public void getIsLoadingLine(MutableLiveData<Boolean> isLoadingLine) {
        this.isLoadingLine = isLoadingLine;
    }

    public MutableLiveData<Boolean> getIsLoadingLine() {
        return isLoadingLine;
    }

    public Float getRemaining() {
        return remaining;
    }

    public void setRemaining(Float remaining) {
        this.remaining = remaining;
    }

    public List<PieEntry> getPieEntries() {
        return pieEntries;
    }

    public void setPieEntries(List<PieEntry> pieEntries) {
        this.pieEntries = pieEntries;
    }

    private Integer x = 0;

    public ArrayList<CustomEntry> getLineEntries() {
        return lineEntries;
    }

    public void setLineEntries(ArrayList<CustomEntry> entries) {
        this.lineEntries = entries;
    }

    public Float getGoal() {
        return goal;
    }

    public void setGoal(Float goal) {
        this.goal = goal;
    }

    public MutableLiveData<Boolean> getIsLoadingDocument() {
        return isLoadingDocument;
    }

    public void setIsLoadingDocument(MutableLiveData<Boolean> isLoadingDocument) {
        this.isLoadingDocument = isLoadingDocument;
    }

    public Float getExerciseCalories() {
        return exerciseCalories;
    }

    public Float getFoodCalories() {
        return foodCalories;
    }

    public void setExerciseCalories(Float exerciseCalories) {
        this.exerciseCalories = exerciseCalories;
    }

    public void setFoodCalories(Float foodCalories) {
        this.foodCalories = foodCalories;
    }


    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }



    public MutableLiveData<Boolean> getIsLoadingData() {
        return isLoadingData;
    }

    public HomeVM() {
//        this.loadDocument();
        this.loadLineData();
    }

    public void saveDailySteps(int stepCount, Date previousDate) {

        Map<String, Object> dailyActivities = new HashMap<>();
        dailyActivities.put("steps", stepCount);
        dailyActivities.put("weight", this.getWeight());
        dailyActivities.put("exerciseCalories", this.getExerciseCalories());
        dailyActivities.put("calories", this.getCalories());
        dailyActivities.put("foodCalories", this.getFoodCalories());

        String dateString = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(previousDate);
        firestore.collection("users")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("daily_activities").document(dateString)
                .set(dailyActivities)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("success", "Lưu giá trị thành công");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("success", "Lưu giá trị thất bại");
                        Log.i("bug", e.toString());
                    }
                });
    }
    public MutableLiveData<NormalUser> getUser() {
        return user;
    }
    public void loadDocument() {
        isLoadingDocument.setValue(true);
        firestore.collection("users")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("daily_activities")
                .document(GlobalMethods.convertDateToHyphenSplittingFormat(new Date()))
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        if (documentSnapshot.contains("steps")) {
                            int stepsValue = documentSnapshot.getLong("steps").intValue();
                            setSteps(stepsValue);
                        }
                        else {
                            setSteps(0);
                        }
                        if (documentSnapshot.contains("weight")) {
                            int weightValue = documentSnapshot.getLong("steps").intValue();
                            setWeight(weightValue);
                        }
                        else {
                            setWeight(0);
                        }
                        if (documentSnapshot.contains("calories")) {
                            float caloriesValue = documentSnapshot.getLong("calories").floatValue();
                            setCalories(caloriesValue);
                        }
                        else {
                            setCalories((float) 0);
                        }
                        if (documentSnapshot.contains("exerciseCalories")) {
                            float exerciseCaloriesValue = documentSnapshot.getLong("exerciseCalories").floatValue();
                            setExerciseCalories(exerciseCaloriesValue);
                        }
                        else {
                            setExerciseCalories((float) 0);
                        }
                        if (documentSnapshot.contains("foodCalories")) {
                            float foodCaloriesValue = documentSnapshot.getLong("foodCalories").floatValue();
                            setFoodCalories(foodCaloriesValue);
                        }
                        else {
                            setFoodCalories((float) 0);
                        }
                        loadGoal();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.i("Lỗi", "abcdxyz");
                });
    }

    public void loadGoal() {
        firestore.collection("users")
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        float goal = documentSnapshot.getLong("dailyCalories").floatValue();
                        setGoal((float) this.getUser().getValue().getDailyCalories());
                        Log.i("goal", String.valueOf(goal));
                        setRemaining(this.getGoal() - this.getFoodCalories() + this.getExerciseCalories());
                        pieEntries = new ArrayList<>();
                        pieEntries.add(new PieEntry(this.getRemaining() / this.getGoal(), "Remaining"));
                        pieEntries.add(new PieEntry(this.getFoodCalories() / this.getGoal(), "Food"));
                        pieEntries.add(new PieEntry(this.getExerciseCalories() / this.getGoal(), "Exercise"));
                        float dailyCalories = documentSnapshot.getLong("dailyCalories").floatValue();
                        setDailyCalories(dailyCalories);
                        float startWeight = documentSnapshot.getLong("startWeight").floatValue();
                        setStartWeight(startWeight);
                        float goalWeight = documentSnapshot.getLong("goalWeight").floatValue();
                        setGoalWeight(goalWeight);
                        isLoadingDocument.setValue(false);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.i("Lỗi", "abcdxyz");
                });
    }

    public void loadLineData() {
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
                                if (document.contains("steps")) {
                                    int steps = document.getLong("steps").intValue();
                                    String date = document.getId();
                                    Log.i("steps line", String.valueOf(steps));
                                    Log.i("date line", date);

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
        firestore.collection("users").whereEqualTo("email", firebaseAuth.getCurrentUser().getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            user.setValue(task.getResult().getDocuments().get(0).toObject(NormalUser.class));
                            isLoadingData.setValue(false);

                        } else {
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
}
