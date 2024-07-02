package com.Main.csci3130groupassignment.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import com.example.csci3130groupassignment.R;

public class EmployeeWelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeewelcome);
        setupJobListButton();
        setupMyJobsButton();
    }

    protected void setupJobListButton() {
        Button jobListButton = findViewById(R.id.JobListButton);
        jobListButton.setOnClickListener(this);
    }

    protected void setupMyJobsButton(){
        Button myJobsButton = findViewById(R.id.MyJobsButton);
        myJobsButton.setOnClickListener(this);
    }

    protected void movetoJobList() {
        Intent moveWindow = new Intent(getApplicationContext(), JobRecyclerViewActivity.class);
        String username = getIntent().getStringExtra("USERNAME");
        moveWindow.putExtra("USERNAME", username);
        moveWindow.putExtra("ROLE", "Employee");
        startActivity(moveWindow);
    }

    protected void movetoMyJobs() {
        Intent moveWindow = new Intent(getApplicationContext(), MyJobsRecyclerViewActivity.class);
        String username = getIntent().getStringExtra("USERNAME");
        moveWindow.putExtra("USERNAME", username);
        moveWindow.putExtra("ROLE", "Employee");
        startActivity(moveWindow);
    }



    /**
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.JobListButton) {
            movetoJobList();
        }

        if(v.getId() == R.id.MyJobsButton){
            movetoMyJobs();
        }


    }
}