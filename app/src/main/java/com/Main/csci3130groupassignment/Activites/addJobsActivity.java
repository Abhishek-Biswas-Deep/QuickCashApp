package com.Main.csci3130groupassignment.Activites;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.Main.csci3130groupassignment.FirebaseFiles.FirebaseCRUD;
import com.Main.csci3130groupassignment.JobFiles.JobObject;
import com.Main.csci3130groupassignment.Maps.GoogleMaps;
import com.Main.csci3130groupassignment.Maps.LocationTracker;
import com.example.csci3130groupassignment.R;
import com.google.firebase.database.FirebaseDatabase;

public class addJobsActivity extends AppCompatActivity {
    //AddJobsActivity and is very heavily inspired by
    //the code base from the FirebaseCRUD tutorial 4, modified to fit this format.

    private EditText jobTitleEditText;
    private EditText employerNameEditText;
    private Spinner jobTypeSpinner;
    private EditText jobDescriptionEditText;
    private EditText jobPayEditText;
    private LocationTracker locationTracker;

    FirebaseDatabase database;
    FirebaseCRUD crud = null;

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        jobTitleEditText = findViewById(R.id.jobName);
        employerNameEditText = findViewById(R.id.employerName);
        jobTypeSpinner = findViewById(R.id.jobType);
        jobDescriptionEditText = findViewById(R.id.jobDescription);
        jobPayEditText = findViewById(R.id.jobPay);

        locationTracker = new LocationTracker(this);

        loadJobTypeSpinner();


        Button addJobButton = findViewById(R.id.addJobButton);
        addJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addJobToFirebase();
            }
        });
    }


    private void addJobToFirebase() {
        //creating a map for job
        Map<String, Object> map = new HashMap<>();

        // Get job details from EditText fields
        if (!jobTitleEditText.getText().toString().isEmpty()
                && !employerNameEditText.getText().toString().isEmpty()
                && !jobDescriptionEditText.getText().toString().isEmpty()
                && !jobPayEditText.getText().toString().isEmpty()) {
            map.put("JobTitle", jobTitleEditText.getText().toString());
            map.put("EmployerName", employerNameEditText.getText().toString());
            map.put("JobType", jobTypeSpinner.getSelectedItem().toString());
            map.put("JobDescription", jobDescriptionEditText.getText().toString());
            map.put("Pay", jobPayEditText.getText().toString());
            map.put("Location", getLatLongAsString());
            map.put("UserID", getIntent().getStringExtra("USERNAME"));

            FirebaseDatabase.getInstance("https://csci3130-group-assignment-default-rtdb.firebaseio.com/")
                    .getReference()
                    .child("Jobs")
                    .push()
                    .setValue(map)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getApplicationContext(), "Job added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(getApplicationContext(), "Job insertion failed", Toast.LENGTH_SHORT).show());
        }
        else {
            Toast.makeText(getApplicationContext(), "Job creation failed, please fill in all boxes", Toast.LENGTH_SHORT).show();
        }
        clearInputFields();
    }

    protected String getLatLongAsString() {
        if (locationTracker.getLocation() != null) {
            double latitude = locationTracker.getLocation().getLatitude();
            double longitude = locationTracker.getLocation().getLongitude();
            String latLongString = latitude + ", " + longitude;
            return latLongString;
        } else {
            return "Location not available";
        }
    }

    private void loadJobTypeSpinner() {
        // Define job types
        String[] jobTypes = {"Manual Labour", "Technical", "Care Taking", "HealthCare"};

        // Create ArrayAdapter using jobTypes array
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jobTypes);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        jobTypeSpinner.setAdapter(adapter);
    }

    private void clearInputFields() {
        jobTitleEditText.setText("");
        employerNameEditText.setText("");
        jobDescriptionEditText.setText("");
        jobPayEditText.setText("");
    }
}