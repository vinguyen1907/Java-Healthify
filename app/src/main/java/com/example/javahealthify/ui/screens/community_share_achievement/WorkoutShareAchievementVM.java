package com.example.javahealthify.ui.screens.community_share_achievement;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.Achievement;
import com.example.javahealthify.data.models.User;
import com.example.javahealthify.utils.FirebaseConstants;
import com.example.javahealthify.utils.GlobalMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;

public class WorkoutShareAchievementVM extends ViewModel {
    private MutableLiveData<Achievement> todayAchievement = new MutableLiveData<>(null);
    private MutableLiveData<String> warningDialogMessage = new MutableLiveData<>("");
    private MutableLiveData<Boolean> isAddedSuccessfully = new MutableLiveData<>(false);

    public WorkoutShareAchievementVM() {
        loadTodayAchievement();
    }

    public void loadTodayAchievement() {
        CollectionReference dailyActivitiesRef = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("daily_activities");
        dailyActivitiesRef.document(GlobalMethods.convertDateToHyphenSplittingFormat(new Date())).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Achievement newAchievement;
                            newAchievement = task.getResult().toObject(Achievement.class);
                            newAchievement.setId(task.getResult().getId());
                            todayAchievement.postValue(newAchievement);
                        }
                    }
                });
    }

    public void addAchievementToDb(User user) {
        // Check if there is any achievement today
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date startOfNextDate = calendar.getTime();

        FirebaseConstants.achievementsRef
                .whereEqualTo("userId", FirebaseConstants.firebaseAuth.getCurrentUser().getUid())
                .whereGreaterThanOrEqualTo("createdTime", startOfDate)
                .whereLessThan("createdTime", startOfNextDate).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                Achievement achievement = new Achievement(
                                        todayAchievement.getValue().getCalories(),
                                        todayAchievement.getValue().getSteps(), // TODO: Get steps and replace it
                                        todayAchievement.getValue().getExerciseCalories(),
                                        todayAchievement.getValue().getFoodCalories(),
                                        user.getUid(),
                                        user.getName(),
                                        user.getImageUrl(),
                                        GlobalMethods.convertDateToHyphenSplittingFormat(new Date()),
                                        new Date());
                                FirebaseConstants.achievementsRef.add(achievement)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    isAddedSuccessfully.setValue(true);
                                                }
                                            }
                                        });
                            } else {
                                warningDialogMessage.setValue("You have shared today achievement!");
                            }
                        }
                    }
                });


    }

    public MutableLiveData<Achievement> getTodayAchievement() {
        return todayAchievement;
    }

    public void resetWarningMessage() {
        warningDialogMessage.setValue("");
    }

    public MutableLiveData<String> getWarningDialogMessage() {
        return warningDialogMessage;
    }

    public MutableLiveData<Boolean> getIsAddedSuccessfully() {
        return isAddedSuccessfully;
    }
}
