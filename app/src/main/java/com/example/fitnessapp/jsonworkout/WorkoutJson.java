package com.example.fitnessapp.jsonworkout;

import org.json.JSONException;
import org.json.JSONObject;

public class WorkoutJson {
    private String name;
    private String difficulty;
    private String time;

    public WorkoutJson(){
    }

    public WorkoutJson fromJSON(JSONObject userJson) {
        try {

            name = userJson.getString("name");
            difficulty = userJson.getString("difficulty");
            time = userJson.getString("time");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getTime() {
        return time;
    }
}
