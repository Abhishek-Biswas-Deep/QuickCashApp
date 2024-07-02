package com.Main.csci3130groupassignment.Activites;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.Main.csci3130groupassignment.FirebaseFiles.FirebaseCRUD;
import com.Main.csci3130groupassignment.HelperFunctions.LoginValidator;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import com.example.csci3130groupassignment.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //LoginActivity class is heavily inspired by Assignment 2 and Lecture 5b, with changes
    //to allow it to function for this project.

    FirebaseDatabase database;
    FirebaseCRUD crud = null;
    boolean validating = false;
    String usernamecheck = "temp";

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setupLoginButton();
        this.initializeDatabaseAccess();
    }

    protected void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_URL));
        crud = new FirebaseCRUD(database);
    }

    protected String getUsername() {
        EditText LogUsernameBox = findViewById(R.id.LoginUsername);
        return LogUsernameBox.getText().toString().trim();
    }

    protected String getPassword() {
        EditText LogPasswordBox = findViewById(R.id.LoginPassword);
        return LogPasswordBox.getText().toString().trim();
    }

    protected void setupLoginButton() {
        Button loginButton = findViewById(R.id.logButton);
        loginButton.setOnClickListener(this);
    }

    protected void MoveToEmployer(String username) {
        Intent moveWindow = new Intent(getApplicationContext(), EmployerWelcomeActivity.class);
        moveWindow.putExtra("USERNAME", username);
        moveWindow.putExtra("ROLE", "Employer");
        startActivity(moveWindow);
    }

    protected void MoveToEmployee(String username) {
        Intent moveWindow = new Intent(getApplicationContext(), EmployeeWelcomeActivity.class);
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

        String username = getUsername();
        String password = getPassword();
        String ErrorMessage;

        crud.setUserListener(username);
        String Fireusername = crud.getExtractedUser();
        String Firepassword = crud.getExtractedPassword();
        String role = crud.getExtractedRole();

        LoginValidator validator = new LoginValidator();

        //Finding Firebase key to load account info from.
        if (!validating) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Validating... Press again", Snackbar.LENGTH_LONG);
            snackbar.show();
            usernamecheck = username;
            validating = true;
        }
        else if (!usernamecheck.equals(username)) {
            ErrorMessage = "Validation interrupted, reenter username";
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), ErrorMessage, Snackbar.LENGTH_LONG);
            snackbar.show();
            validating = false;
        }

        //Username valid checking
        else if (validator.isEmptyUsername(username)) {
            ErrorMessage = "Enter Username";
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), ErrorMessage, Snackbar.LENGTH_LONG);
            snackbar.show();
            validating = false;
        }
        else if (!validator.isValidUsername(username, Fireusername)) {
            ErrorMessage = "Unknown Username";
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), ErrorMessage, Snackbar.LENGTH_LONG);
            snackbar.show();
            validating = false;
        }

        //Password valid checking
        else if (!validator.isValidPassword(password, Firepassword)) {
            ErrorMessage = "Wrong Password";
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), ErrorMessage, Snackbar.LENGTH_LONG);
            snackbar.show();
            validating = false;
        }
        else {
            if (role.equals("Employer")) {
                MoveToEmployer(Fireusername);
            }
            else if (role.equals("Employee")) {
                MoveToEmployee(Fireusername);
            }
            else {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_LONG);
                snackbar.show();
                validating = false;
            }
        }
    }
}