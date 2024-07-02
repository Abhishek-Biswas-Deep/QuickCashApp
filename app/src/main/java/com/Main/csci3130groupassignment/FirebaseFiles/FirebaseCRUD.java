package com.Main.csci3130groupassignment.FirebaseFiles;
import androidx.annotation.NonNull;

import com.Main.csci3130groupassignment.UserFiles.Employee;
import com.Main.csci3130groupassignment.UserFiles.Employer;
import com.example.csci3130groupassignment.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.Main.csci3130groupassignment.UserFiles.User;


public class FirebaseCRUD {
    //FirebaseCRUD class is heavily inspired by Assignment 2 and Lecture 5b, with changes
    //to allow it to function for this project.

    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private String extractedUsername, extractedPassword, extractedRole;


    /**
     *
     * @param database
     */
    public FirebaseCRUD(FirebaseDatabase database) {
        this.database = database;
        this.initializeDatabaseRefs();
        this.initializeDatabaseListeners();
    }

    /**
     *
     * @param key
     * @return
     */
    protected DatabaseReference getUserRef(String key) {
        return this.database.getReference().child("User").child(key);
    }

    /**
     *
     * @param username
     * @param password
     * @param role
     */
    public void setUser(String username, String password, String role) {
        userRef = this.getUserRef(username);
        if (role.equals("Employee")) {
            this.userRef.setValue(new Employee(username, password, role));
        }
        else if (role.equals("Employer")) {
            this.userRef.setValue(new Employer(username, password, role));
        }
    }

    /**
     *
     * @param key
     */
    public void setUserListener(String key) {

        userRef = this.getUserRef(key);
        this.userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                extractedUsername = snapshot.child("Username").getValue(String.class);
                extractedPassword = snapshot.child("Password").getValue(String.class);
                extractedRole = snapshot.child("Role").getValue(String.class);

            }

            //This part is unused but required in the function structure.
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    protected void initializeDatabaseRefs() {
        this.userRef = getUserRef("temp");
    }

    protected void initializeDatabaseListeners() {
        this.setUserListener("temp");
    }

    public String getExtractedUser() {
        return this.extractedUsername;
    }
    public String getExtractedRole() {
        return this.extractedRole;
    }
    public String getExtractedPassword() {
        return this.extractedPassword;
    }
}
