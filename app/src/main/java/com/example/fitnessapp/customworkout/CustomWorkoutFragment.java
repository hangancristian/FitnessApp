package com.example.fitnessapp.customworkout;

import android.arch.persistence.room.Room;

import android.content.Intent;
import android.os.Bundle;


import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fitnessapp.MainActivity;
import com.example.fitnessapp.R;

import java.util.List;




/**
 * A simple {@link Fragment} subclass.
 */
public class CustomWorkoutFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private FloatingActionButton fab;

    public CustomWorkoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom, container, false);


        recyclerView = view.findViewById(R.id.recycler_view);


        final AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "production").allowMainThreadQueries().build();

        List<Workout> workouts = db.workoutDao().getAllWorkouts();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WorkoutAdapter(workouts);
        recyclerView.setAdapter(adapter);

        fab = view.findViewById(R.id.fab_new);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), CreateWorkout.class);
                startActivity(in);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
               db.workoutDao().deleteWorkout();

            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }


}
