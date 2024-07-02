package com.Main.csci3130groupassignment.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.Main.csci3130groupassignment.FirebaseFiles.FirebaseCRUD;
import com.Main.csci3130groupassignment.HelperFunctions.JobAdapter;
import com.Main.csci3130groupassignment.HelperFunctions.WrapLinearLayoutManager;
import com.Main.csci3130groupassignment.JobFiles.JobObject;
import com.example.csci3130groupassignment.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;



public class MyJobsRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private JobAdapter jobAdapter;

    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jobs);
        init();
        userID = getIntent().getStringExtra("USERNAME");
        initializeMyJobsView(userID);
    }

    protected void init(){
        recyclerView = findViewById(R.id.MyJobsRecyclerView);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    protected void initializeMyJobsView(String userID){
        final FirebaseRecyclerOptions<JobObject> options = new FirebaseRecyclerOptions.Builder<JobObject>()
                .setQuery(FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL))
                        .getReference()
                        .child("User")
                        .child(userID)
                        .child("Accepted"),JobObject.class)
                .build();
        jobAdapter = new JobAdapter(options, userID, "Employee");
        recyclerView.setAdapter(jobAdapter);
    }

    protected void onStart(){
        super.onStart();
        jobAdapter.startListening();
    }

    protected void onStop(){
        super.onStop();
        jobAdapter.stopListening();
    }
























}
