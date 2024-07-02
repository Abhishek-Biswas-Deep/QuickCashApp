package com.Main.csci3130groupassignment.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import com.example.csci3130groupassignment.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //MainActivity class is heavily inspired by Assignment 2 and Lecture 5b, with changes
    //to allow it to function for this project.

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setupLoginButton();
        this.setupRegistrationButton();
    }

    protected void setupLoginButton() {
        Button loginButton = findViewById(R.id.LoginActivityButton);
        loginButton.setOnClickListener(this);
    }

    protected void setupRegistrationButton() {
        Button registerButton = findViewById(R.id.RegisterActivityButton);
        registerButton.setOnClickListener(this);
    }

    protected void MovetoRegistration() {
        Intent moveWindow = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(moveWindow);
    }

    protected void MovetoLogin() {
        Intent moveWindow = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(moveWindow);
    }

    /**
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.LoginActivityButton) {
            MovetoLogin();
        }

        if (v.getId() == R.id.RegisterActivityButton) {
            MovetoRegistration();
        }

    }
}
