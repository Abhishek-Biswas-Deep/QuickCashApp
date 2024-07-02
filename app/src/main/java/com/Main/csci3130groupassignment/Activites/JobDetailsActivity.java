package com.Main.csci3130groupassignment.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Main.csci3130groupassignment.JobFiles.JobObject;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.Main.csci3130groupassignment.HelperFunctions.AppConstants;
import com.example.csci3130groupassignment.R;

public class JobDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    //JobDetailsActivity and it's corresponding xml file is heavily inspired from
    //the Code Base of FirebaseCRUD tutorial 4, modified to fit this format.

    public static final String JOBID = AppConstants.JOBID;
    private TextView Description, Title;
    private Button returnButton, applyButton;
    private JobObject job;
    private String username, key;


    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        username = getIntent().getStringExtra("USERNAME");
        key = getIntent().getStringExtra("KEY");

        init();
        setActivityView();
    }

    private void init() {
        Description = findViewById(R.id.DetailsDescription);
        Title = findViewById(R.id.DetailsTitle);
        applyButton = findViewById(R.id.ApplyButton);
        returnButton = findViewById(R.id.ReturnDetailsButton);
        returnButton.setOnClickListener(this);
        applyButton.setOnClickListener(this);
    }

    private void setActivityView() {
        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            job = (com.Main.csci3130groupassignment.JobFiles.JobObject) getIntent().getSerializableExtra(JobObject.TAG);
            Description.setText(job.getJobDescription());
            Title.setText(job.getJobTitle());
        }
    }

    /**
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ReturnDetailsButton) {
            finish();
        }

        if (v.getId() == R.id.ApplyButton) {
            Intent moveWindow = new Intent(getApplicationContext(), ApplyToJobActivity.class);
            moveWindow.putExtra("USERNAME", username);
            moveWindow.putExtra("KEY", key);
            startActivity(moveWindow);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

}
