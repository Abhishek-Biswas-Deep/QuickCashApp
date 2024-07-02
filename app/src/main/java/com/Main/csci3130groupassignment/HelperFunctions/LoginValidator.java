package com.Main.csci3130groupassignment.HelperFunctions;

public class LoginValidator {
    //LoginValidator class is heavily inspired by Assignment 2 and Lecture 5b, with changes
    //to allow it to function for this project.

    /**
     *
     * @param username
     * @return
     */
    public boolean isEmptyUsername(String username) {
        return username.isEmpty();
    }

    /**
     *
     * @param username
     * @param Fireusername
     * @return
     */
    public boolean isValidUsername(String username, String Fireusername) {

        boolean valid = false;

        if (username.equals(Fireusername)) {
            valid = true;
        }
        return valid;
    }

    /**
     *
     * @param password
     * @param Firepassword
     * @return
     */
    public boolean isValidPassword(String password, String Firepassword) {

        boolean valid = false;

        if (password.equals(Firepassword)) {
            valid = true;
        }
        return valid;
    }
}
