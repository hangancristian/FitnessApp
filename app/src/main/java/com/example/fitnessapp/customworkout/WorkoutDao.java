package com.example.fitnessapp.customworkout;



import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WorkoutDao {
    @Query("SELECT * FROM workout")
    List<Workout> getAllWorkouts();

    @Insert
    void insertAll(Workout... workouts);

    @Delete
    void deleteWorkout(Workout... workouts);

}
