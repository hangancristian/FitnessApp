package com.example.fitnessapp.jsonworkout;

import android.os.Bundle;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.fitnessapp.R;

import org.json.JSONArray;
import org.json.JSONException;


import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private WorkoutJsonAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = myview.findViewById(R.id.userRecyclerView);

        // set the Layout Manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        parseUsersFromJSON();

        return myview;
    }

    private void parseUsersFromJSON(){
        String url = "https://my-json-server.typicode.com/hangancristian/json/workouts";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<WorkoutJson> workoutJsons = new ArrayList<>();

                for (int index = 0; index < response.length(); index++) {
                    try {
                        WorkoutJson workoutJson = new WorkoutJson().fromJSON(response.getJSONObject(index));
                        workoutJsons.add(workoutJson);
                    } catch (JSONException ex) {
                    }
                }
                mAdapter = new WorkoutJsonAdapter(workoutJsons);
                recyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Volley error " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }

}
