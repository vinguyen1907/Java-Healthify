package com.example.javahealthify.data.models;

import java.util.List;

public class Workout {
    private String id;
    private String name;
    private String image;
    private int calories;
    private String level; // BEGINNER, INTERMEDIATE, ADVANCE
    private String intensity;
    private int duration;
    private List<Exercise> exercises;
}
