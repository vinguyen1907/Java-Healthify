package com.example.javahealthify.utils;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.javahealthify.data.models.Exercise;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class GlobalMethods {
    public static void backToPreviousFragment(Fragment fragment) {
        NavHostFragment.findNavController(fragment).popBackStack();
    }

    public static Map<String, Object> toKeyValuePairs(Object instance) {
        return Arrays.stream(instance.getClass().getDeclaredFields())
                .collect(Collectors.toMap(
                        Field::getName,
                        field -> {
                            try {
                                Object result = null;
                                field.setAccessible(true);
                                result = field.get(instance);
                                return result != null ? result : "";
                            } catch (Exception e) {
                                return "";
                            }
                        }));
    }

    public static String formatDoubleToString(double value) {
        return String.format(Locale.US, "%." + 1 + "f", value);
    }
    public static String convertDateToSlashSplittingFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static String convertDateToHyphenSplittingFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }


    public static String formatTimeOrRep(int count, String unitType) {
        if (unitType == "rep") {
            return "x" + String.valueOf(count);
        } else {
            int minute = count / 60;

            return String.format("%02d", minute) + ":" + String.valueOf(count % 60);
        }
    }

    public static int calculateTotalCalories(List<Exercise> exercises) {
        int totalCalories = 0;
        for (Exercise exercise : exercises) {
            totalCalories += exercise.getCaloriesPerUnit();
        }
        return totalCalories;
    }
}
