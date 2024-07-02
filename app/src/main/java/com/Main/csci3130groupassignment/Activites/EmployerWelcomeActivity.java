package com.Main.csci3130groupassignment.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import com.Main.csci3130groupassignment.JobFiles.ApplicantObject;
import com.example.csci3130groupassignment.R;

import java.util.ArrayList;
import java.util.List;

public class EmployerWelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employerwelcome);
        setupJobCreateButton();
        setupJobViewButton();

        String username = getIntent().getStringExtra("USERNAME");
    }

    protected void setupJobCreateButton() {
        Button jobCreateButton = findViewById(R.id.JobCreateButton);
        jobCreateButton.setOnClickListener(this);
    }

    protected void setupJobViewButton() {
        Button jobViewButton = findViewById(R.id.JobsViewButton);
        jobViewButton.setOnClickListener(this);
    }

    public void moveToJobCreation(String username) {
        Intent moveWindow = new Intent(getApplicationContext(), addJobsActivity.class);
        moveWindow.putExtra("USERNAME", username);
        startActivity(moveWindow);
    }

    public void moveToJobView(String username) {
        Intent moveWindow = new Intent(getApplicationContext(), JobRecyclerViewActivity.class);
        moveWindow.putExtra("USERNAME", username);
        moveWindow.putExtra("ROLE", "Employer");
        startActivity(moveWindow);
    }

    /**
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        String username = getIntent().getStringExtra("USERNAME");

        if (v.getId() == R.id.JobCreateButton) {
            moveToJobCreation(username);
        }
        if (v.getId() == R.id.JobsViewButton) {
            moveToJobView(username);
        }
    }
}