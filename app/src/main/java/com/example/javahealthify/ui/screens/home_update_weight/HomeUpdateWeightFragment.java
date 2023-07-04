package com.example.javahealthify.ui.screens.home_update_weight;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.javahealthify.databinding.FragmentHomeBinding;
import com.example.javahealthify.databinding.FragmentHomeWeightReportBinding;
import com.example.javahealthify.ui.screens.home.HomeVM;
import com.example.javahealthify.ui.screens.workout_history.WorkoutHistoryFragment;
import com.example.javahealthify.utils.GlobalMethods;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class HomeUpdateWeightFragment extends Fragment {
    private HomeUpdateWeightVM homeUpdateWeightVM;
    private FragmentHomeWeightReportBinding binding;
    private BarChart barChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeUpdateWeightVM = new ViewModelProvider(requireActivity()).get(HomeUpdateWeightVM.class);
        homeUpdateWeightVM.getIsLoadingData();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeWeightReportBinding.inflate(inflater,container,false);
        binding.setViewModel(homeUpdateWeightVM);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        binding.weightReportBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalMethods.backToPreviousFragment(HomeUpdateWeightFragment.this);
            }
        });

        binding.updateWeightDailyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeUpdateWeightVM.saveDailyWeight(binding.addWeightDaily.getText().toString());
            }
        });

        barChart = binding.barChart;
        drawWeightChart();




        return binding.getRoot();
    }

    private void drawWeightChart() {
        // Replace the following code with your Firestore data retrieval logic
        List<BarEntry> entries = new ArrayList<>();
//        entries = homeUpdateWeightVM.getBarEntries();
        entries.add(new BarEntry(0f, 65f)); // Example data for day 1: weight 65kg
        entries.add(new BarEntry(1f, 67f)); // Example data for day 2: weight 67kg
        entries.add(new BarEntry(2f, 66f)); // Example data for day 3: weight 66kg

        BarDataSet dataSet = new BarDataSet(entries, "Weight");
        BarData barData = new BarData(dataSet);

        barChart.setData(barData);
        barChart.getDescription().setText("Weight by Day");
        barChart.getDescription().setTextColor(Color.BLACK); // Set description text color to black
        barChart.getXAxis().setLabelCount(entries.size());
        barChart.getXAxis().setTextColor(Color.WHITE); // Set x-axis text color to white
        barChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int day = (int) value;
                return "Day " + (day + 1);
            }
        });

        barChart.getAxisLeft().setTextColor(Color.WHITE); // Set y-axis left text color to white

        // Custom axis value formatter for displaying "kg" next to weight values
        ValueFormatter weightValueFormatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return value + "kg";
            }
        };

        barChart.getAxisLeft().setValueFormatter(weightValueFormatter); // Set y-axis left value formatter
        barChart.getAxisRight().setValueFormatter(weightValueFormatter); // Set y-axis right value formatter

        barChart.invalidate(); // Refresh the chart
    }


}
