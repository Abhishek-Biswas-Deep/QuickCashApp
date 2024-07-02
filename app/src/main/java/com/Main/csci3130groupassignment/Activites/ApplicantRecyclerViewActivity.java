package com.Main.csci3130groupassignment.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.Main.csci3130groupassignment.HelperFunctions.AppConstants;
import com.Main.csci3130groupassignment.HelperFunctions.ApplicantAdapter;
import com.Main.csci3130groupassignment.HelperFunctions.JobAdapter;
import com.Main.csci3130groupassignment.HelperFunctions.WrapLinearLayoutManager;
import com.Main.csci3130groupassignment.JobFiles.ApplicantObject;
import com.Main.csci3130groupassignment.JobFiles.JobObject;
import com.example.csci3130groupassignment.R;
import com.google.firebase.database.FirebaseDatabase;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class ApplicantRecyclerViewActivity extends AppCompatActivity implements View.OnClickListener {
    //ApplicantRecyclerViewActivity and it's corresponding xml files is very heavily inspired by
    //the code base from the FirebaseCRUD tutorial 4, modified to fit this format.

    private RecyclerView recyclerView;
    private ApplicantAdapter viewApplicantAdapter;
    private Button applyReturnButton;
    public static final String JOBID = AppConstants.JOBID;
    private JobObject job;

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
        setContentView(R.layout.activity_applicantlist);

        String username = getIntent().getStringExtra("USERNAME");
        String role = getIntent().getStringExtra("ROLE");
        String key = getIntent().getStringExtra("KEY");
        String buttonBool = getIntent().getStringExtra("BUTTONBOOL");

        job = (com.Main.csci3130groupassignment.JobFiles.JobObject) getIntent().getSerializableExtra(JobObject.TAG);

        init();
        if (buttonBool.equals("Applicants")) {
            initializeDatabaseAccessApplicants(username, role, key, buttonBool);
        }
        else if (buttonBool.equals("Hired")) {
            initializeDatabaseAccessHired(username, role, key, buttonBool);
        }
    }

    private void init() {
        recyclerView = findViewById(R.id.ApplicantRecyclerView);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        applyReturnButton = findViewById(R.id.ApplyReturnButton);
        applyReturnButton.setOnClickListener(this);
    }

    protected void initializeDatabaseAccessApplicants(String userID, String userRole, String key, String buttonBool) {
        final FirebaseRecyclerOptions<ApplicantObject> options = new FirebaseRecyclerOptions.Builder<ApplicantObject>()
                .setQuery(FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL))
                        .getReference()
                        .child(getString(R.string.JOBS_COLLECTION))
                        .child(key)
                        .child(getString(R.string.APPLICANTS_LIST)), ApplicantObject.class)
                .build();
        viewApplicantAdapter = new ApplicantAdapter(options, userID, userRole, buttonBool, key, job);
        recyclerView.setAdapter(viewApplicantAdapter);
    }

    protected void initializeDatabaseAccessHired(String userID, String userRole, String key, String buttonBool) {
        final FirebaseRecyclerOptions<ApplicantObject> options = new FirebaseRecyclerOptions.Builder<ApplicantObject>()
                .setQuery(FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL))
                        .getReference()
                        .child(getString(R.string.JOBS_COLLECTION))
                        .child(key)
                        .child(getString(R.string.HIRED_LIST)), ApplicantObject.class)
                .build();
        viewApplicantAdapter = new ApplicantAdapter(options, userID, userRole, buttonBool, key, job);
        recyclerView.setAdapter(viewApplicantAdapter);
    }

    /**
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewApplicantAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewApplicantAdapter.stopListening();
    }
}