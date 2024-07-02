package com.JUnit.csci3130groupassignment.SearchJobListTests;

import com.Main.csci3130groupassignment.JobFiles.JobObject;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JobSearchTest {

    @Test
    public void SearchByJobType() {
        JobObject job = new JobObject("title1", "Technical", "technical Solving", "14", "man1", "Unavailable", "EmployeEE1");
        JobObject job2 = new JobObject("title2", "Manual Labour", "Manual work", "12", "man2", "Unavailable", "EmployeEE1");

        assertEquals("Technical", job.getJobType());
        assertEquals("Manual Labour", job2.getJobType());

    }

    @Test
    public void SearchByJobPay() {
        JobObject job = new JobObject("title1", "Technical", "technical Solving", "14", "man1", "Unavailable", "EmployeEE1");
        JobObject job2 = new JobObject("title2", "Manual Labour", "Manual work", "12", "man2", "Unavailable", "EmployeEE1");

        assertEquals("14", job.getPay());
        assertEquals("12", job2.getPay());

    }
}


