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
import com.example.javahealthify.ui.screens.home.CustomEntry;
import com.example.javahealthify.ui.screens.home.HomeVM;
import com.example.javahealthify.ui.screens.workout_history.WorkoutHistoryFragment;
import com.example.javahealthify.utils.GlobalMethods;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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
        homeUpdateWeightVM.getIsLoadingData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoadingData) {
                if (isLoadingData != null && !isLoadingData) {
                    drawWeightChart(homeUpdateWeightVM.getBarEntries());
                } else {

                }
            }
        });

        return binding.getRoot();
    }

    private void drawWeightChart(List<CustomEntry> entries) {
        List<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0; i < entries.size(); i++) {
            CustomEntry entry = entries.get(i);
            barEntries.add(new BarEntry(entry.getX(), entry.getY()));
        }

        BarDataSet dataSet = new BarDataSet(barEntries, "Weight");
        dataSet.setValueTextColor(Color.WHITE);
        BarData barData = new BarData(dataSet);

        barChart.setData(barData);
        barChart.getDescription().setText("Weight by Date");
        barChart.getDescription().setTextColor(Color.BLACK);

        // Set up X-axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f); // Display one value per interval
        xAxis.setDrawGridLines(false); // Hide grid lines

        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < entries.size()) {
                    CustomEntry entry = entries.get(index);
                    return entry.getXLabel();
                }
                return "";
            }
        });

        xAxis.setTextColor(Color.WHITE);

        // Set up Y-axis
        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();

        leftAxis.setTextColor(Color.WHITE);
        rightAxis.setTextColor(Color.WHITE);

        // Custom axis value formatter for displaying "kg" next to weight values
        ValueFormatter weightValueFormatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return value + "kg";
            }
        };

        leftAxis.setValueFormatter(weightValueFormatter);
        rightAxis.setValueFormatter(weightValueFormatter);

        barChart.invalidate();
    }
}
