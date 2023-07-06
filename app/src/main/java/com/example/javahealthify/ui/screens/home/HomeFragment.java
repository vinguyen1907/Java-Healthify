package com.example.javahealthify.ui.screens.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.javahealthify.R;
import com.example.javahealthify.data.adapters.WorkoutCategoriesAdapter;
import com.example.javahealthify.data.models.NormalUser;
import com.example.javahealthify.data.models.User;
import com.example.javahealthify.databinding.FragmentHomeBinding;
import com.example.javahealthify.ui.screens.MainVM;
import com.example.javahealthify.ui.screens.profile.ProfileFragment;
import com.example.javahealthify.ui.screens.profile.ProfileVM;
import com.example.javahealthify.ui.screens.workout.WorkoutVM;
import com.example.javahealthify.ui.screens.workout_categories.WorkoutCategoriesFragment;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private HomeVM homeVM;
    private MutableLiveData<NormalUser> user = new MutableLiveData<>();
    private FragmentHomeBinding binding;
    private WorkoutVM workoutVM;
    private PieChart pieChart;
    private LinearLayout legendLayout;
    private LineChart lineChart;
    private TextView exerciseTv;

    private List<String> legendEntries;
    private List<Integer> legendValues;

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private TextView stepCountTextView;
    private int currentStepCount = 0;
    private Date currentDate = new Date();
    private static final int ACCELEROMETER_BUFFER_SIZE = 100;// cái này là kích thước bộ đệm gia tốc
    private static final float STEP_THRESHOLD = 8.0f; // cái này là ngưỡng phát hiện bước châm

    private float[] accelerometerBuffer = new float[ACCELEROMETER_BUFFER_SIZE];
    private int bufferIndex = 0;
    private boolean isStepDetected = false;
    private int stepCount = 0;

    // Xác định các trục gia tốc
    private float previousX = 0.0f;
    private float previousY = 0.0f;
    private float previousZ = 0.0f;

    // Xác định thời gian giữa các lần đọc gia tốc
    private long previousTimestamp = 0;
    Date previousDate;

    private MainVM mainVM;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private int remaining;
    private int foodCalories;
    private int exerciseCalories;
    private int goal;
    private String goalMsg = "Goal: ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeVM = new ViewModelProvider(requireActivity()).get(HomeVM.class);
        homeVM.getUserLiveData();
        homeVM.loadDocument();

        // Init today activity

        workoutVM = new ViewModelProvider(this).get(WorkoutVM.class);
        workoutVM.initDailyActivity();
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setViewModel(homeVM);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // ----------------------------------LINECHART----------------------------------------------

        lineChart = binding.lineChart;

        // ----------------------------------PIECHART-----------------------------------------------

        pieChart = binding.pieChart;
        legendLayout = binding.legendLayout;

//        homeVM.getIsLoadingData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean isLoadingData) {
//                if (isLoadingData != null && !isLoadingData) {
//                    homeVM.loadDocument();
//                    homeVM.loadLineData();
//                } else {
//
//                }
//            }
//        });

        homeVM.getIsLoadingDocument().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoadingDocument) {
                if (isLoadingDocument != null && !isLoadingDocument) {
                    setLoading();
                    drawPie();
                } else {

                }
            }
        });


        homeVM.getIsLoadingLine().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoadingLine) {
                if (isLoadingLine != null && !isLoadingLine) {
                    drawLine();
                }
            }
        });

        binding.updateWeightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment_to_homeUpdateWeightFragment);
            }
        });

        binding.exerciseDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment_to_excerciseDetail);
            }
        });

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (stepSensor != null) {
            sensorManager.registerListener(accelerometerSensorEventListener, stepSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(requireContext(), "Step counter is not available on your device", Toast.LENGTH_SHORT).show();
        }

        // Lấy số bước chân từ SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        long previousDateMillis = sharedPreferences.getLong("previousDate", 0);
        previousDate = new Date(previousDateMillis);
        stepCount = sharedPreferences.getInt("stepCount", 0);

        binding.stepCountTextView.setText(String.valueOf(stepCount));


        return binding.getRoot();
    }

    private void drawPie() {
        legendEntries = new ArrayList<>();
        legendEntries.add("Remaining");
        legendEntries.add("Food");
        legendEntries.add("Exercise");

        legendValues = new ArrayList<>();
        legendValues.add(homeVM.getRemaining().intValue());
        legendValues.add(homeVM.getFoodCalories().intValue());
        legendValues.add(homeVM.getExerciseCalories().intValue());

        List<Integer> iconResources = new ArrayList<>();
        iconResources.add(R.drawable.home_target);
        iconResources.add(R.drawable.home_food);
        iconResources.add(R.drawable.home_calories);


        List<PieEntry> entries = new ArrayList<>();
        entries = homeVM.getPieEntries();


//        entries.add(new PieEntry((float)remaining/goal, "Remaining"));
//        entries.add(new PieEntry((float)foodCalories/goal, "Food"));
//        entries.add(new PieEntry((float)exerciseCalories/goal, "Exercise"));

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#0DBBFC"));
        colors.add(Color.parseColor("#69E6A6"));
        colors.add(Color.parseColor("#FFAA7E"));


        PieDataSet dataSet = new PieDataSet(entries, "Categories");
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.TRANSPARENT);
        dataSet.setValueTextSize(12f);


        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setHoleRadius(70f);


        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(android.R.color.transparent);
        pieChart.setTransparentCircleRadius(58f);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setEntryLabelColor(android.R.color.black);

        pieChart.setDrawCenterText(true);
        pieChart.setCenterText(goalMsg.concat(homeVM.getGoal().toString()));
        pieChart.setCenterTextSize(16f);
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.animateXY(1000, 1000, Easing.EaseInOutBounce); // 1000 milliseconds for both X and Y axes

        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);


        for (int i = 0; i < entries.size(); i++) {
            if (entries.size() > 3) {
                Log.i("if i", String.valueOf(i));
                String legendEntry = legendEntries.get(2);
                int legendValue = legendValues.get(2);

                View legendItemView = LayoutInflater.from(getContext()).inflate(R.layout.legend_item, null);
                ImageView legendIconView = legendItemView.findViewById(R.id.legendIcon);
                TextView legendLabelTextView = legendItemView.findViewById(R.id.legendLabel);
                TextView legendValueTextView = legendItemView.findViewById(R.id.legendValue);

                legendIconView.setImageResource(iconResources.get(i));
                legendLabelTextView.setText(legendEntry);
                legendValueTextView.setText(String.valueOf(legendValue));

                LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                itemParams.setMargins(10, 10, 0, 6);
                legendItemView.setLayoutParams(itemParams);

                // Add legend item view to the legend layout
                legendLayout.addView(legendItemView);
            } else {
                Log.i("else i", String.valueOf(i));
                String legendEntry = legendEntries.get(i);
                int legendValue = legendValues.get(i);

                View legendItemView = LayoutInflater.from(getContext()).inflate(R.layout.legend_item, null);
                ImageView legendIconView = legendItemView.findViewById(R.id.legendIcon);
                TextView legendLabelTextView = legendItemView.findViewById(R.id.legendLabel);
                TextView legendValueTextView = legendItemView.findViewById(R.id.legendValue);

                legendIconView.setImageResource(iconResources.get(i));
                legendLabelTextView.setText(legendEntry);
                legendValueTextView.setText(String.valueOf(legendValue));

                LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                itemParams.setMargins(10, 10, 0, 6);
                legendItemView.setLayoutParams(itemParams);

                // Add legend item view to the legend layout
                legendLayout.addView(legendItemView);
            }
            pieChart.invalidate();

        }
    }

    private void setLoading() {
//        homeVM.getIsLoadingDocument().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean isLoadingDocument) {
//                if (isLoadingDocument != null && !isLoadingDocument) {
        binding.userNameTv.setText(homeVM.getUser().getValue().getName());
        binding.exerciseTv.setText(homeVM.getExerciseCalories().toString());
        binding.startWeight.setText(homeVM.getStartWeight().toString());
        binding.goalWeight.setText(homeVM.getGoalWeight().toString());
        binding.dailyCalories.setText(homeVM.getDailyCalories().toString());
        if (homeVM.getUser().getValue().getImageUrl() == null) {
            binding.userAvatar.setImageResource(R.drawable.default_profile_image);
        } else {
            Glide.with(requireContext()).load(homeVM.getUser().getValue().getImageUrl()).into(binding.userAvatar);
        }
//                    exerciseCalories = homeVM.getExerciseCalories();
//                    foodCalories = homeVM.getFoodCalories();
//                    goal = homeVM.getGoal();
//                    remaining = goal - foodCalories + exerciseCalories;
//                }
//                else {
//                    binding.exerciseTv.setText("");
//                }
//            }
//        });
    }

    ;

    private SensorEventListener accelerometerSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Tính toán gia tốc tổng hợp
            float acceleration = Math.abs(x + y + z - previousX - previousY - previousZ);

            // Kiểm tra xem có phát hiện bước chân hay không
            if (acceleration > STEP_THRESHOLD) {
                // Kiểm tra thời gian giữa các lần phát hiện
                long currentTimestamp = System.currentTimeMillis();
                long timeDifference = currentTimestamp - previousTimestamp;

                if (timeDifference > 300) { // Giới hạn khoảng thời gian giữa các bước chân
                    isStepDetected = true;
                    previousTimestamp = currentTimestamp;
                }
            }

            previousX = x;
            previousY = y;
            previousZ = z;

            // Đếm số bước chân
            if (isStepDetected) {
                stepCount++;
                isStepDetected = false;
            }

            binding.stepCountTextView.setText(String.valueOf(stepCount));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        // Lấy SharedPreferences để lưu trữ ngày trước đó
        SharedPreferences sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        long previousDateMillis = sharedPreferences.getLong("previousDate", 0);
        previousDate = new Date(previousDateMillis);
        currentDate = new Date();


        if (!isSameDay(currentDate, previousDate)) {

            homeVM.saveDailySteps(stepCount, previousDate);

            stepCount = 0;
            binding.stepCountTextView.setText(String.valueOf(stepCount));

            // Lưu ngày hiện tại vào SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("previousDate", currentDate.getTime());
            editor.putInt("stepCount", stepCount); // lưu giá trị mới nhất của stepCount
            editor.apply();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        homeVM.getIsLoadingDocument().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean isLoadingDocument) {
//                if (isLoadingDocument != null && !isLoadingDocument) {
//                    if (!isSameDay(currentDate, previousDate)) {
//                        homeVM.saveDailySteps(stepCount, previousDate);
//                    }
//                } else {
//
//                }
//            }
//        });


        SharedPreferences sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stepCount", stepCount);
        editor.putLong("previousDate", currentDate.getTime());
        editor.apply();
        SensorManager sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensorManager.unregisterListener(accelerometerSensorEventListener);
        }


    }


    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }


    private void drawLine() {

        ArrayList<CustomEntry> entries = new ArrayList<>();
        entries = homeVM.getLineEntries();
//        entries.add(new CustomEntry(0, 65f, "Ngày 1"));
//        entries.add(new CustomEntry(1, 68f, "Ngày 2"));
//        entries.add(new CustomEntry(2, 70f, "Ngày 3"));


        List<Entry> entryList = new ArrayList<>();
        for (CustomEntry customEntry : entries) {
            entryList.add(customEntry);
        }

        LineDataSet dataSet = new LineDataSet(entryList, "Steps");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        LineData lineData = new LineData(dataSet);

        String[] labels = new String[entries.size()];
        for (int i = 0; i < entries.size(); i++) {
            labels[i] = entries.get(i).getXLabel();
        }

        // cái này để hiển thị ngày ở cột có giá trị
        IndexAxisValueFormatter xAxisFormatter = new IndexAxisValueFormatter(labels);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setGranularity(1);

        xAxis.setAxisMaximum(entries.size() - 1);


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        lineChart.setData(lineData);
        lineChart.animateXY(1000, 1000, Easing.EaseInOutBounce);

        Description description = new Description();
        description.setText("Steps per day");
        description.setTextColor(Color.WHITE);
        lineChart.setDescription(description);

        Legend legend = lineChart.getLegend();
        legend.setTextColor(Color.WHITE);

        dataSet.setColor(Color.parseColor("#69E6A6"));
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setLineWidth(2f);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getAxisRight().setTextColor(Color.TRANSPARENT);
        lineChart.getXAxis().setTextColor(Color.WHITE);

        lineChart.invalidate();
    }

}
