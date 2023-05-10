package com.example.javahealthify.utils;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

import com.example.javahealthify.data.models.Exercise;

import java.util.List;
@BindingMethods({
        @BindingMethod(type = TextView.class, attribute = "selectedExercisesSize", method = "setSelectedExercisesSize")
})
public class BindingAdapters {
    @BindingAdapter("selectedExercisesSize")
    public static void setSelectedExercisesSize(TextView textView, List<Exercise> list) {
        if (list != null) {
            textView.setText(String.valueOf(list.size()) + " exercises selected");
        } else {
            textView.setText("0");
        }
    }
}
