package com.example.javahealthify.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.javahealthify.R;
import com.example.javahealthify.data.models.Exercise;
import com.example.javahealthify.ui.screens.workout_exercise_practicing.PracticingOnBackDialogInterface;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
        if (unitType.equals("reps")) {
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

    public static boolean isToday(Date date) {
        LocalDate today = LocalDate.now();
        LocalDate givenDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return today.isEqual(givenDate);
    }

    public static String convertTimeInSeconds(int timeInSeconds) {
        int hour = timeInSeconds / 3600;
        int minute = (timeInSeconds - hour * 3600) / 60;
        int second = timeInSeconds - hour * 3600 - minute * 60;
        return (hour == 0 ? "" :String.valueOf(hour) + ":") + (minute ==0 ? "00:" : String.valueOf(minute) + ":") + (second < 10 ? "0" : "")  + String.valueOf(second);
    }

    public static void showWarningDialog(Context context, String message, PracticingOnBackDialogInterface onBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onBack.onPositiveButton();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onBack.onNegativeButton();
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(true);
        builder.setIcon(R.drawable.ic_warning);
        AlertDialog warningDialog = builder.create();
        warningDialog.show();
    }

    public static List<String> generateKeyword(String name) {
        String nameInLowerCase = name.toLowerCase(Locale.ROOT);
        String[] words = nameInLowerCase.split(" ");
        List<String> generatedStrings = new ArrayList<>();

        int startPosition = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            for (int j = startPosition; j < nameInLowerCase.length(); j++) {
                    sb.append(nameInLowerCase.charAt(j));
                    if (sb.charAt(sb.length() - 1) != ' ') { // If the last character is space, no need to add this sub string to list
                        generatedStrings.add(sb.toString());
                    }
            }
            sb.setLength(0);

            startPosition += words[i].length() + 1;
        }

        return generatedStrings;
    }
}
