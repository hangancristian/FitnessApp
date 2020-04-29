package com.example.fitnessapp.customworkout;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fitnessapp.MainActivity;
import com.example.fitnessapp.R;


public class CreateWorkout extends AppCompatActivity {

    EditText title;
    EditText content;
    Button buttonAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_workout);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        buttonAdd = findViewById(R.id.buttonAdd);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "production").allowMainThreadQueries().build();


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Workout workout = new Workout(title.getText().toString(), content.getText().toString());
                db.workoutDao().insertAll(workout);

                startActivity(new Intent(CreateWorkout.this, MainActivity.class));
            }
        });

    }
}
