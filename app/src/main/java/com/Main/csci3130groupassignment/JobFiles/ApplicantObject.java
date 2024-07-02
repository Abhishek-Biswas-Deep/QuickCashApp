package com.Main.csci3130groupassignment.JobFiles;

public class ApplicantObject {
    private String Name;
    private String Contact;
    private String UserID;

    public ApplicantObject(String name, String contact, String userID) {
        this.Name = name;
        this.Contact = contact;
        this.UserID = userID;
    }

    public ApplicantObject() {

    }

    public String getName() {
        return Name;
    }

    public String getContact() {
        return Contact;
    }

    public String getUserID() {
        return UserID;
    }
}
