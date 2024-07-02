package com.AndroidTest.csci3130groupassignment;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UIAutomatorTest {

    private static final int LAUNCH_TIMEOUT = 5000;

    final String launcherPackage = "com.example.csci3130groupassignment";
    private UiDevice device;


    @Before
    public void setup() {
        device = UiDevice.getInstance(getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        final Intent appIntent = context.getPackageManager().getLaunchIntentForPackage(launcherPackage);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(appIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkIfMainPageLoads() {
        UiObject WelcomeText = device.findObject(new UiSelector().textContains("Welcome!"));
        assertTrue(WelcomeText.exists());
        UiObject RegistrationButton = device.findObject(new UiSelector().text("REGISTER"));
        assertTrue(RegistrationButton.exists());
        UiObject LoginButton = device.findObject(new UiSelector().text("LOGIN"));
        assertTrue(LoginButton.exists());
    }

    @Test
    public void checkIfMovedToLogin() throws UiObjectNotFoundException {
        UiObject LoginButton = device.findObject(new UiSelector().text("LOGIN"));
        LoginButton.clickAndWaitForNewWindow();
        UiObject LogButton = device.findObject(new UiSelector().text("LOGIN"));
        assertTrue(LogButton.exists());
    }

    @Test
    public void checkIfMoveToRegister() throws UiObjectNotFoundException {
        UiObject RegisterButton = device.findObject(new UiSelector().text("REGISTER"));
        RegisterButton.clickAndWaitForNewWindow();
        UiObject RegButton = device.findObject(new UiSelector().text("REGISTER"));
        assertTrue(RegButton.exists());

    }

    @Test
    public void checkIfMovedToEmployer() throws UiObjectNotFoundException {
        UiObject LoginButton = device.findObject(new UiSelector().text("LOGIN"));
        LoginButton.clickAndWaitForNewWindow();
        UiObject netIDBox = device.findObject(new UiSelector().textContains("Username"));
        netIDBox.setText("UsermanR");
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Password"));
        emailIDBox.setText("Passman");
        UiObject LogButton = device.findObject(new UiSelector().text("LOGIN"));
        LogButton.click();
        LogButton.clickAndWaitForNewWindow();
        UiObject WelcomeText = device.findObject(new UiSelector().textContains("Welcome Employer"));
        assertTrue(WelcomeText.exists());
    }

    @Test
    public void checkIfMovedToEmployee() throws UiObjectNotFoundException {
        UiObject LoginButton = device.findObject(new UiSelector().text("LOGIN"));
        LoginButton.clickAndWaitForNewWindow();
        UiObject netIDBox = device.findObject(new UiSelector().textContains("Username"));
        netIDBox.setText("UsermanE");
        UiObject emailIDBox = device.findObject(new UiSelector().textContains("Password"));
        emailIDBox.setText("Passman");
        UiObject LogButton = device.findObject(new UiSelector().text("LOGIN"));
        LogButton.click();
        LogButton.clickAndWaitForNewWindow();
        UiObject WelcomeText = device.findObject(new UiSelector().textContains("Welcome Employee"));
        assertTrue(WelcomeText.exists());
    }

}
