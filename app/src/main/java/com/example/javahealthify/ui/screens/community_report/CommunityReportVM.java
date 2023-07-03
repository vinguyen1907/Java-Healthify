package com.example.javahealthify.ui.screens.community_report;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javahealthify.data.models.Achievement;
import com.example.javahealthify.data.models.User;
import com.example.javahealthify.utils.FirebaseConstants;
import com.example.javahealthify.utils.GlobalMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;

public class CommunityReportVM extends ViewModel {
    private MutableLiveData<String> title = new MutableLiveData<>("");
    private MutableLiveData<String> description = new MutableLiveData<>("");
    private Achievement achievement;
    private User user;
    private MutableLiveData<String> message = new MutableLiveData<>("");

    public CommunityReportVM() {
        achievement = null;
        user = null;
    }

    public void sendReport() {
        if (user != null && achievement != null) {
            HashMap<String, Object> newReport = new HashMap<>();
            newReport.put("achievementId", achievement.getId());
            newReport.put("achievementUserId", achievement.getUserId());
            newReport.put("achievementUserName", achievement.getUserName());
            newReport.put("achievementUserImageUrl", achievement.getUserImageUrl());
            newReport.put("achievementSteps", achievement.getSteps());
            newReport.put("achievementExerciseCalories", achievement.getExerciseCalories());
            newReport.put("achievementFoodCalories", achievement.getFoodCalories());
            newReport.put("achievementCalories", achievement.getCalories());
            newReport.put("achievementCreatedTime", achievement.getCreatedTime());
            newReport.put("reportUserId", user.getUid());
            newReport.put("reportUserName", user.getName());
            newReport.put("reportUserImageUrl", user.getImageUrl());
            newReport.put("title", title.getValue());
            newReport.put("description", description.getValue());
            newReport.put("activitiesId", GlobalMethods.convertDateToHyphenSplittingFormat(achievement.getCreatedTime()));

            FirebaseConstants.reportsRef.add(newReport)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                message.setValue("We sent this report to admin. Thanks for your contribution.");
                                title.setValue("");
                                description.setValue("");
                            } else {
                                message.setValue("Something went wrong. Please try again");
                            }
                        }
                    });
        } else {
            message.setValue("Something went wrong. Please try again");
        }
    }

    public MutableLiveData<String> getTitle() {
        return title;
    }

    public void setTitle(MutableLiveData<String> title) {
        this.title = title;
    }

    public MutableLiveData<String> getDescription() {
        return description;
    }

    public void setDescription(MutableLiveData<String> description) {
        this.description = description;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }
}
