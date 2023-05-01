package com.example.javahealthify.data.models;


import androidx.annotation.Keep;

import java.util.Date;
import java.util.List;
public class NormalUser extends User {

    private String phone;
    private Date dateOfBirth;
    private String address;
    private String gender; // { "MALE", "FEMALE" }
    private int startWeight;
    private int goalWeight;
    private Date startTime;
    private Date goalTime;
    private int dailyCalories;
    private List<String> following;
    private List<String> followers;

    public NormalUser() {}

    public NormalUser(String uid, String email, String name, String phone, Date dateOfBirth, String address, String gender, int startWeight, int goalWeight, Date startTime, Date goalTime, int dailyCalories, List<String> following, List<String> followers) {
        super(uid, email, name, "NORMAL_USER");
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
        this.startWeight = startWeight;
        this.goalWeight = goalWeight;
        this.startTime = startTime;
        this.goalTime = goalTime;
        this.dailyCalories = dailyCalories;
        this.following = following;
        this.followers = followers;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(int startWeight) {
        this.startWeight = startWeight;
    }

    public int getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(int goalWeight) {
        this.goalWeight = goalWeight;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getGoalTime() {
        return goalTime;
    }

    public void setGoalTime(Date goalTime) {
        this.goalTime = goalTime;
    }

    public int getDailyCalories() {
        return dailyCalories;
    }

    public void setDailyCalories(int dailyCalories) {
        this.dailyCalories = dailyCalories;
    }

    public List<String> getFollowing() {
        return following;
    }

    public void setFollowing(List<String> following) {
        this.following = following;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }
//    private  List<DailyActivity> dailyActivities;
}
