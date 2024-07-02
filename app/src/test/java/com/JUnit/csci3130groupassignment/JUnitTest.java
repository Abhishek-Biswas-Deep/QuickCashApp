package com.JUnit.csci3130groupassignment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.Main.csci3130groupassignment.HelperFunctions.LoginValidator;

public class JUnitTest {

    LoginValidator validator;

    @Before
    public void setup() {
        validator = new LoginValidator();
    }

    @Test
    public void checkIfUsernameIsEmpty() {
        assertTrue(validator.isEmptyUsername(""));
        assertFalse(validator.isEmptyUsername("Username"));
    }

    @Test
    public void checkIfUsernameIsInDatabase() {
        assertTrue(validator.isValidUsername(("Userman"), ("Userman")));
        assertFalse(validator.isValidUsername(("Userman"), ("Bongoman")));
    }

    @Test
    public void checkIfPasswordisInDatabase() {
        assertTrue(validator.isValidPassword(("Passman"), ("Passman")));
        assertFalse(validator.isValidUsername(("Passman"), ("Bongoman")));
    }
}
