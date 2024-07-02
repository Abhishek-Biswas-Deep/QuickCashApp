package com.Main.csci3130groupassignment.HelperFunctions;

import com.Main.csci3130groupassignment.Activites.ApplicantRecyclerViewActivity;
import com.Main.csci3130groupassignment.Activites.JobDetailsActivity;
import com.Main.csci3130groupassignment.Activites.JobRecyclerViewActivity;
import com.Main.csci3130groupassignment.Activites.MainActivity;
import com.Main.csci3130groupassignment.Activites.PaymentActivity;
import com.Main.csci3130groupassignment.JobFiles.JobObject;
import com.Main.csci3130groupassignment.Maps.GoogleMaps;
import com.example.csci3130groupassignment.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;

public class JobAdapter extends FirebaseRecyclerAdapter<JobObject, JobAdapter.JobViewHolder> {
    //JobAdapter and it's corresponding xml file is heavily inspired from
    //the Code Base of FirebaseCRUD tutorial 4, modified to fit this format.

    private String username;
    private String role;

    /**
     *
     * @param options
     */
    public JobAdapter(@NonNull FirebaseRecyclerOptions<JobObject> options, String userUsername, String userRole) {
        super(options);
        this.username = userUsername;
        this.role = userRole;
    }

    /**
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item_view, parent, false);
        return new JobViewHolder(view);
    }

    public class JobViewHolder extends RecyclerView.ViewHolder {
        private final TextView JobTitle;
        private final TextView JobType;
        private final TextView Pay;
        private final TextView EmployerName;
        private final Button DetailsButton;
        private final Button LocationButton;
        private final Context context;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            JobTitle = itemView.findViewById(R.id.JobTitleTextView);
            JobType = itemView.findViewById(R.id.JobTypeTextView);
            Pay = itemView.findViewById(R.id.PayTextView);
            EmployerName = itemView.findViewById(R.id.EmployerNameTextView);
            DetailsButton = itemView.findViewById(R.id.DetailsButton);
            LocationButton = itemView.findViewById(R.id.LocationButton);
            context = itemView.getContext();

            if (role.equals("Employer")) {
                LocationButton.setText(AppConstants.Applicants);
                DetailsButton.setText((AppConstants.Hired));
            }
        }
    }

    /**
     *
     * @param holder
     * @param position
     * @param model the model object containing the data that should be used to populate the view.
     */
    @Override
    protected void onBindViewHolder(@NonNull JobViewHolder holder, int position, @NonNull JobObject model) {

        holder.JobTitle.setText(model.getJobTitle());
        holder.JobType.setText(model.getJobType());
        holder.Pay.setText(model.getPay());
        holder.EmployerName.setText(model.getEmployerName());

        if (role == null || role.equals("Employee")) {
            holder.DetailsButton.setOnClickListener(view -> {
                final Intent intent = new Intent(holder.context, JobDetailsActivity.class);

                intent.putExtra(JobDetailsActivity.JOBID, getRef(position).getKey());
                intent.putExtra(JobObject.TAG, model);

                intent.putExtra("USERNAME", username);
                intent.putExtra("KEY", getRef(position).getKey());

                holder.context.startActivity(intent);
            });

            holder.LocationButton.setOnClickListener(view -> {
                final Intent intent = new Intent(holder.context, GoogleMaps.class);
                String location = model.getLocation();
                String jobName = model.getJobTitle();

                if (!location.equals("Location not available")) {
                    String[] LatAndLng = location.split(",");
                    double latitude = Double.parseDouble(LatAndLng[0]);
                    double longitude = Double.parseDouble(LatAndLng[1]);

                    intent.putExtra("EXTRA_LATITUDE", latitude);
                    intent.putExtra("EXTRA_LONGITUDE", longitude);
                    intent.putExtra("EXTRA_JOB_NAME", jobName);

                    intent.putExtra(JobDetailsActivity.JOBID, getRef(position).getKey());
                    intent.putExtra(JobObject.TAG, model);
                    holder.context.startActivity(intent);
                }
                else {
                    Snackbar snackbar = Snackbar.make(view, "Location not available", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
        }

        else if (role.equals("Employer")) {
            holder.LocationButton.setOnClickListener(view -> {
                final Intent intent = new Intent(holder.context, ApplicantRecyclerViewActivity.class);
                intent.putExtra("USERNAME", username);
                intent.putExtra("ROLE", "Employer");
                intent.putExtra("KEY", getRef(position).getKey());
                intent.putExtra("BUTTONBOOL", "Applicants");

                intent.putExtra(JobDetailsActivity.JOBID, getRef(position).getKey());
                intent.putExtra(JobObject.TAG, model);

                holder.context.startActivity(intent);
            });

            holder.DetailsButton.setOnClickListener(view -> {
                final Intent intent = new Intent(holder.context, PaymentActivity.class);
                intent.putExtra("USERNAME", username);
                intent.putExtra("ROLE", "Employer");
                intent.putExtra("KEY", getRef(position).getKey());
                intent.putExtra("BUTTONBOOL", "Hired");

                intent.putExtra(JobDetailsActivity.JOBID, getRef(position).getKey());
                intent.putExtra(JobObject.TAG, model);

                holder.context.startActivity(intent);
            });
        }



    }
}
