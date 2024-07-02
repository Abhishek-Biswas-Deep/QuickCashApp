package com.Main.csci3130groupassignment.Activites;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.Main.csci3130groupassignment.FirebaseFiles.FirebaseCRUD;
import com.Main.csci3130groupassignment.HelperFunctions.LoginValidator;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import com.example.csci3130groupassignment.R;

import java.util.ArrayList;
import java.util.List;




public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    //RegisterActivity class is heavily inspired by Assignment 2 and Lecture 5b, with changes
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
        setContentView(R.layout.activity_register);
        this.loadRoleSpinner();
        this.RegButtonSetup();
        this.initializeDatabaseAccess();
    }

    protected void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_URL));
        crud = new FirebaseCRUD(database);
    }

    protected String getUsername() {
        EditText RegUsernameBox = findViewById(R.id.RegisterUsername);
        return RegUsernameBox.getText().toString().trim();
    }

    protected String getPassword() {
        EditText RegPasswordBox = findViewById(R.id.RegisterPassword);
        return RegPasswordBox.getText().toString().trim();
    }

    protected String getRole() {
        Spinner roleSpinner = findViewById(R.id.roleSpinner);
        return roleSpinner.getSelectedItem().toString().trim();
    }

    protected void loadRoleSpinner() {
        Spinner roleSpinner = findViewById(R.id.roleSpinner);
        List<String> roles = new ArrayList<>();
        roles.add("Select Role");
        roles.add("Employer");
        roles.add("Employee");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, roles);
        spinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        roleSpinner.setAdapter(spinnerAdapter);
    }

    protected void RegButtonSetup() {
        Button registerButton = findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(this);
    }

    protected void MoveBackHome() {
        Intent moveWindow = new Intent(getApplicationContext(), MainActivity.class);
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
        String role = getRole();
        String ErrorMessage;

        crud.setUserListener(username);
        String Fireusername = crud.getExtractedUser();

        LoginValidator validator = new LoginValidator();

        //Loading Firebase to ensure Username is not already taken.
        if (!validating) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Registering... Press again", Snackbar.LENGTH_LONG);
            snackbar.show();
            usernamecheck = username;
            validating = true;
        }
        else if (!usernamecheck.equals(username)) {
            ErrorMessage = "Registration interrupted, reenter username";
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), ErrorMessage, Snackbar.LENGTH_LONG);
            snackbar.show();
            validating = false;
        }
        else if (username.equals(Fireusername)) {
            ErrorMessage = "Registration failed, Username is taken, enter new username";
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), ErrorMessage, Snackbar.LENGTH_LONG);
            snackbar.show();
            validating = false;
        }
        else {
            this.crud.setUser(username, password, role);
            MoveBackHome();
        }
    }
}
