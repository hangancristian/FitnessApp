package com.example.fitnessapp.jsonworkout;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitnessapp.R;

import java.util.List;

public class WorkoutJsonAdapter extends RecyclerView.Adapter<WorkoutJsonAdapter.MyViewHolder> {

    private List<WorkoutJson> workoutJsons;

    // Constructor (depends on the kind of dataset)
    public WorkoutJsonAdapter(List<WorkoutJson> myDataset){
        workoutJsons = myDataset;
    }

    public void setWorkoutJsons(List<WorkoutJson> workoutJsons) {
        this.workoutJsons = workoutJsons;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_json_info, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){

        holder.name.setText(workoutJsons.get(position).getName());
        holder.difficulty.setText("Difficulty: " + workoutJsons.get(position).getDifficulty());
        holder.time.setText("Duration: " + workoutJsons.get(position).getTime() + " minutes");
    }



    @Override
    public int getItemCount() {
        return workoutJsons.size();
    }


    public void addUser (WorkoutJson workoutJson) {
        List<WorkoutJson> workoutJsons = this.workoutJsons;
        workoutJsons.add(workoutJson);
        this.workoutJsons = workoutJsons;


    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView difficulty;
        public TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.workout_name);
            difficulty = itemView.findViewById(R.id.workout_difficulty);
            time = itemView.findViewById(R.id.workout_time);
        }
    }
}
