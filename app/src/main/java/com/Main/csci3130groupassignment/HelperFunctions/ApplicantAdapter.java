package com.Main.csci3130groupassignment.HelperFunctions;

import static com.Main.csci3130groupassignment.HelperFunctions.PayPal.PAYPAL_REQUEST_CODE;

import com.Main.csci3130groupassignment.Activites.PaymentActivity;
import com.Main.csci3130groupassignment.JobFiles.ApplicantObject;
import com.Main.csci3130groupassignment.JobFiles.JobObject;
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
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ApplicantAdapter extends FirebaseRecyclerAdapter<ApplicantObject, ApplicantAdapter.ApplicantViewHolder> {
    //ApplicantAdapter and it's corresponding xml file is heavily inspired from
    //the Code Base of FirebaseCRUD tutorial 4, modified to fit this format.

    private String username, role, buttonBool, key;

    private JobObject job;
    /**
     *
     * @param options
     */
    public ApplicantAdapter(@NonNull FirebaseRecyclerOptions<ApplicantObject> options, String userUsername, String userRole, String ButtonBool, String Key, JobObject Job) {
        super(options);
        this.username = userUsername;
        this.role = userRole;
        this.buttonBool = ButtonBool;
        this.key = Key;
        this.job = Job;
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
    public ApplicantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.applicant_item_view, parent, false);
        return new ApplicantViewHolder(view);
    }

    public class ApplicantViewHolder extends RecyclerView.ViewHolder {
        private final TextView Name;
        private final TextView Contact;
        private final Button HirePayButton;

        public ApplicantViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.ApplicantNameTextView);
            Contact = itemView.findViewById(R.id.ApplicantContactInfoTextView);
            HirePayButton = itemView.findViewById(R.id.ApplicantOmniButton);

            if (buttonBool.equals("Hired")) {
                HirePayButton.setText(AppConstants.Pay);
            }
        }
    }


    private void initiatePaymentProcess(Context context, String amount,  String name) {
        if(context instanceof PaymentActivity) {
            try {
                BigDecimal paymentAmount = new BigDecimal(amount.replaceAll("[^\\d.]", ""));
                ((PaymentActivity) context).initiatePayPalPayment(paymentAmount.toPlainString(), name);
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Invalid payment amount", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     * @param holder
     * @param position
     * @param model the model object containing the data that should be used to populate the view.
     */
    @Override
    protected void onBindViewHolder(@NonNull ApplicantViewHolder holder, int position, @NonNull ApplicantObject model) {


        holder.Name.setText(model.getName());
        holder.Contact.setText(model.getContact());

        if (buttonBool.equals("Applicants")) {
            holder.HirePayButton.setOnClickListener(view -> {
                //creating a map for applicant
                Map<String, Object> map = new HashMap<>();

                // Get applicant details from EditText fields
                map.put("Name", model.getName());
                map.put("Contact", model.getContact());
                map.put("UserID", model.getUserID());

                FirebaseDatabase.getInstance("https://csci3130-group-assignment-default-rtdb.firebaseio.com/")
                        .getReference()
                        .child("Jobs")
                        .child(key)
                        .child(holder.HirePayButton.getContext().getString(R.string.HIRED_LIST))
                        .push()
                        .setValue(map);

                Map<String, Object> map2 = new HashMap<>();

                // Get applicant details from job object
                map2.put("JobTitle", job.getJobTitle());
                map2.put("EmployerName", job.getEmployerName());
                map2.put("JobType", job.getJobType());
                map2.put("JobDescription", job.getJobDescription());
                map2.put("Pay", job.getPay());
                map2.put("Location", job.getLocation());
                map2.put("UserID", job.getUserID());

                FirebaseDatabase.getInstance("https://csci3130-group-assignment-default-rtdb.firebaseio.com/")
                        .getReference()
                        .child("User")
                        .child(model.getUserID())
                        .child(holder.HirePayButton.getContext().getString(R.string.ACCEPTED_LIST))
                        .push()
                        .setValue(map2);
            });
        }

        else if (buttonBool.equals("Hired")) {
            holder.HirePayButton.setOnClickListener(view -> {
                initiatePaymentProcess(view.getContext(), job.getPay(), model.getName());
            });
        }
    }
}