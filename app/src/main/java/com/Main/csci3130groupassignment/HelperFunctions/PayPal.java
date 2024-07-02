package com.Main.csci3130groupassignment.HelperFunctions;

import androidx.appcompat.app.AppCompatActivity;

import com.paypal.android.sdk.payments.PayPalConfiguration;

public class PayPal {

    public static final int PAYPAL_REQUEST_CODE = 123;
    public static final String PayPal_Client_ID = "Ab6pVHLOIKtYL9z_0QiqRO9LKIwfnchQoZZOSJh2c0RhZ1gB-BSzai2xR45_m4IVVpVpGB63EK47TfpR";

    public static final PayPalConfiguration PayPalConfig = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPal_Client_ID);

}
