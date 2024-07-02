package com.Main.csci3130groupassignment.Activites;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Main.csci3130groupassignment.HelperFunctions.JobAdapter;
import com.Main.csci3130groupassignment.HelperFunctions.PreferencesManager;
import com.Main.csci3130groupassignment.HelperFunctions.WrapLinearLayoutManager;
import com.Main.csci3130groupassignment.JobFiles.JobObject;
import com.example.csci3130groupassignment.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class JobRecyclerViewActivity extends AppCompatActivity implements View.OnClickListener {
    //JobRecyclerViewActivity and it's corresponding xml files is very heavily inspired by
    //the code base from the FirebaseCRUD tutorial 4, modified to fit this format.

    private RecyclerView recyclerView;
    private JobAdapter viewJobAdapter;

    private PreferencesManager preferencesManager;

    String userID, role;

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
        setContentView(R.layout.activity_joblist);
        userID = getIntent().getStringExtra("USERNAME");
        role = getIntent().getStringExtra("ROLE");
        if (role.equals("Employee")) {
            init();
            initializeDatabaseAccessEmployee(role);
            loadJobTypeSpinner();
            setupSearchButton();
        }
        else if (role.equals("Employer")) {
            init();
            initializeDatabaseAccessEmployer(role);
            hideViews();
        }

        preferencesManager = new PreferencesManager(this);
    }

    private void init() {
        recyclerView = findViewById(R.id.JobRecyclerView);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    protected void hideViews() {
        Spinner roleSpinner = findViewById(R.id.JobTypeSpinner);
        roleSpinner.setVisibility(View.GONE);
        Button SearchButton = findViewById(R.id.JobSearchButton);
        SearchButton.setVisibility(View.GONE);
    }

    protected void loadJobTypeSpinner() {
        Spinner roleSpinner = findViewById(R.id.JobTypeSpinner);
        List<String> types = new ArrayList<>();
        types.add("Select Type");
        types.add("Manual Labour");
        types.add("Technical");
        types.add("HealthCare");
        types.add("Care Taking");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, types);
        spinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        roleSpinner.setAdapter(spinnerAdapter);
    }

    protected void initializeDatabaseAccessEmployee(String userRole) {
        final FirebaseRecyclerOptions<JobObject> options = new FirebaseRecyclerOptions.Builder<JobObject>()
                .setQuery(FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL))
                        .getReference()
                        .child(getString(R.string.JOBS_COLLECTION)), JobObject.class)
                .build();
        viewJobAdapter = new JobAdapter(options, userID, userRole);
        recyclerView.setAdapter(viewJobAdapter);
    }

    protected void initializeDatabaseAccessEmployer(String userRole) {
        final FirebaseRecyclerOptions<JobObject> options = new FirebaseRecyclerOptions.Builder<JobObject>()
                .setQuery(FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL))
                                .getReference()
                                .child(getString(R.string.JOBS_COLLECTION))
                                .orderByChild("UserID").equalTo(userID)
                        , JobObject.class)
                .build();
        viewJobAdapter = new JobAdapter(options, userID, userRole);
        recyclerView.setAdapter(viewJobAdapter);
    }

    /**
     *
     * @param jobType
     */
    protected void SearchJobs(String jobType) {
        final FirebaseRecyclerOptions<JobObject> options = new FirebaseRecyclerOptions.Builder<JobObject>()
                .setQuery(FirebaseDatabase.getInstance(getString(R.string.FIREBASE_URL))
                        .getReference()
                        .child(getString(R.string.JOBS_COLLECTION))
                        .orderByChild("JobType").equalTo(jobType)
                        , JobObject.class)
                .build();
        viewJobAdapter = new JobAdapter(options, userID, "Employee");
        recyclerView.setAdapter(viewJobAdapter);
    }

    protected void setupSearchButton() {
        Button SearchButton = findViewById(R.id.JobSearchButton);
        SearchButton.setOnClickListener(this);
    }

    protected String getJobType() {
        Spinner JobTypeSpinner = findViewById(R.id.JobTypeSpinner);
        return JobTypeSpinner.getSelectedItem().toString().trim();
    }

    /**
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        String JobType = getJobType();

        if (!JobType.equals("Select Type")) {
            viewJobAdapter.stopListening();
            SearchJobs(JobType);
            viewJobAdapter.startListening();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewJobAdapter.startListening();
        preferencesManager.checkForNewJobs(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewJobAdapter.stopListening();
    }
}
