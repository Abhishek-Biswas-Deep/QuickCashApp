package com.Main.csci3130groupassignment.JobFiles;

import java.io.Serializable;

public class JobObject implements Serializable {

    /*
    Basic Object for Jobs that allows us to store it in Firebase and later retrieve it
    for the Jobs List
     */
    public static final String TAG = "JobTag";
    private String JobTitle;
    private String JobType;
    private String JobDescription;
    private String Pay;
    private String EmployerName;
    private String Location;
    private String UserID;

    public JobObject(String JobTitle, String JobType, String JobDescription,
                     String Pay, String EmployerName, String Location,
                     String UserID) {
        this.JobTitle = JobTitle;
        this.JobType = JobType;
        this.JobDescription = JobDescription;
        this.Pay = Pay;
        this.EmployerName = EmployerName;
        this.Location = Location;
        this.UserID = UserID;
    }

    public JobObject() {
    }

    public void setJobTitle(String newJobTitle) {
        this.JobTitle = newJobTitle;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobType(String newJobType) {
        this.JobType = newJobType;
    }

    public String getJobType() {
        return JobType;
    }

    public void setJobDescription(String newJobDescription) {
        this.JobDescription = newJobDescription;
    }

    public String getJobDescription() {
        return JobDescription;
    }

    public void setPay(String newPay) {
        this.Pay = newPay;
    }

    public String getPay() {
        return Pay;
    }

    public void setEmployerName(String newEmployerName) {
        this.EmployerName = newEmployerName;
    }

    public String getEmployerName() {
        return EmployerName;
    }


    public void setLocation(String newLocation) {
        this.Location = newLocation;
    }

    public String getLocation() {
        return Location;
    }

    public void setUserID(String newUserID) {
        this.UserID = newUserID;
    }

    public String getUserID() {
        return UserID;
    }




}
