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

public class ApplyToJobActivity extends AppCompatActivity {
    //AddApplicantActivity and is very heavily inspired by
    //the code base from the FirebaseCRUD tutorial 4, modified to fit this format.

    private EditText applicantNameEditText;
    private EditText aplicantContactEditText;
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
        setContentView(R.layout.activity_apply_to_job);

        String key = getIntent().getStringExtra("KEY");

        applicantNameEditText = findViewById(R.id.ApplyName);
        aplicantContactEditText = findViewById(R.id.ApplyContact);
        Button addApplicantButton = findViewById(R.id.ApplicationButton);


        addApplicantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addApplicantToFirebase(key);
            }
        });
    }


    private void addApplicantToFirebase(String key) {
        //creating a map for applicant
        Map<String, Object> map = new HashMap<>();

        // Get applicant details from EditText fields
        if (!applicantNameEditText.getText().toString().isEmpty()
                && !aplicantContactEditText.getText().toString().isEmpty()) {
            map.put("Name", applicantNameEditText.getText().toString());
            map.put("Contact", aplicantContactEditText.getText().toString());
            map.put("UserID", getIntent().getStringExtra("USERNAME"));

            FirebaseDatabase.getInstance("https://csci3130-group-assignment-default-rtdb.firebaseio.com/")
                    .getReference()
                    .child("Jobs")
                    .child(key)
                    .child(getString(R.string.APPLICANTS_LIST))
                    .push()
                    .setValue(map)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getApplicationContext(), "Applied successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(getApplicationContext(), "Application failed", Toast.LENGTH_SHORT).show());
        }
        else {
            Toast.makeText(getApplicationContext(), "Application failed, please fill in all boxes", Toast.LENGTH_SHORT).show();
        }
        clearInputFields();
    }

    private void clearInputFields() {
        applicantNameEditText.setText("");
        aplicantContactEditText.setText("");
    }
}