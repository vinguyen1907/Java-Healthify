package com.example.javahealthify.data.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.javahealthify.R;
import com.example.javahealthify.data.models.Exercise;
import com.example.javahealthify.ui.screens.workout_categories_exercises.WorkoutCategoryExercisesFragmentDirections;
import com.example.javahealthify.ui.screens.workout_categories_exercises.WorkoutCategoryExercisesVM;
import com.example.javahealthify.utils.GlobalMethods;

import java.util.List;

public class WorkoutCategoryExercisesAdapter extends RecyclerView.Adapter<WorkoutCategoryExercisesAdapter.ViewHolder>{
    private Context context;
    private List<Exercise> exercises;
    private NavController navController;
    private AddSelectedExercise addSelectedExercise;

    public WorkoutCategoryExercisesAdapter(Context context, List<Exercise> exercises, NavController navController, AddSelectedExercise addSelectedExercise) {
        this.context = context;
        this.exercises = exercises;
        this.navController = navController;
        this.addSelectedExercise = addSelectedExercise;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView timeOrRep;
        private TextView calories;
        private ImageView informationBtn;
        private LinearLayoutCompat container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.exercise_image);
            name = itemView.findViewById(R.id.exercise_name_tv);
            timeOrRep = itemView.findViewById(R.id.exercise_time_or_rep_tv);
            calories = itemView.findViewById(R.id.exercise_calories_tv);
            informationBtn = itemView.findViewById(R.id.exercise_information_btn);
            container = itemView.findViewById(R.id.container);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.exercise_list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);

        Glide.with(context).load(exercise.getImageUrl()).into(holder.image);
        holder.name.setText(exercise.getName().toUpperCase());
        holder.timeOrRep.setText(GlobalMethods.formatTimeOrRep(exercise.getCountNumber(), exercise.getUnit()));
        holder.calories.setText(String.valueOf(exercise.getCaloriesPerUnit()) + " cal");
        holder.informationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutCategoryExercisesFragmentDirections.ActionWorkoutCategoryExercisesFragmentToWorkoutExerciseDetailsFragment action =
                        WorkoutCategoryExercisesFragmentDirections.actionWorkoutCategoryExercisesFragmentToWorkoutExerciseDetailsFragment(exercise);
                navController.navigate(action);
            }
        });
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSelectedExercise.onAddSelectedExercise(exercise);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }
}
