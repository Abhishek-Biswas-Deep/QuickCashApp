package com.Main.csci3130groupassignment.Activites;

import static com.Main.csci3130groupassignment.HelperFunctions.PayPal.PAYPAL_REQUEST_CODE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Main.csci3130groupassignment.HelperFunctions.AppConstants;
import com.Main.csci3130groupassignment.HelperFunctions.ApplicantAdapter;
import com.Main.csci3130groupassignment.HelperFunctions.PayPal;
import com.Main.csci3130groupassignment.HelperFunctions.WrapLinearLayoutManager;
import com.Main.csci3130groupassignment.JobFiles.ApplicantObject;
import com.Main.csci3130groupassignment.JobFiles.JobObject;
import com.example.csci3130groupassignment.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;



import java.math.BigDecimal;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    public static final String JOBID = AppConstants.JOBID;
    private JobObject job;
    private ApplicantAdapter viewApplicantAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicantlist);

        String username = getIntent().getStringExtra("USERNAME");
        String role = getIntent().getStringExtra("ROLE");
        String key = getIntent().getStringExtra("KEY");
        String buttonBool = getIntent().getStringExtra("BUTTONBOOL");

        job = (com.Main.csci3130groupassignment.JobFiles.JobObject) getIntent().getSerializableExtra(JobObject.TAG);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPal.PayPalConfig);
        startService(intent);
        init();

        initializeDatabaseAccessHired(username, role, key, buttonBool);
    }

    private void init() {
        recyclerView = findViewById(R.id.ApplicantRecyclerView);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    public void initiatePayPalPayment(String amount, String name){

        BigDecimal Amount = new BigDecimal(amount);
        PayPalPayment payPalPayment = new PayPalPayment(Amount, "CAD", "Payment for:" + name, PayPalPayment.PAYMENT_INTENT_SALE );

        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPal.PayPalConfig);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, payPalPayment);

        startActivityForResult(intent, PAYPAL_REQUEST_CODE);

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

    protected void onStart() {
        super.onStart();
        viewApplicantAdapter.startListening();
    }
    protected void onStop() {
        super.onStop();
        viewApplicantAdapter.stopListening();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(6);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}