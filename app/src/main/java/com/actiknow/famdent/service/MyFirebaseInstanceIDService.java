package com.actiknow.famdent.service;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.actiknow.famdent.utils.Constants;
import com.actiknow.famdent.utils.UserDetailsPref;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName ();

    @Override
    public void onTokenRefresh () {
        super.onTokenRefresh ();
        String refreshedToken = FirebaseInstanceId.getInstance ().getToken ();
        // Saving reg id to shared preferences
        storeRegIdInPref (refreshedToken);
        // sending reg id to your server
        sendRegistrationToServer (refreshedToken);
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent (Constants.REGISTRATION_COMPLETE);
        registrationComplete.putExtra ("token", refreshedToken);
        LocalBroadcastManager.getInstance (this).sendBroadcast (registrationComplete);
    }

    private void sendRegistrationToServer (final String token) {
        // sending gcm token to server
        Log.e (TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref (String token) {
        UserDetailsPref userDetailsPref = UserDetailsPref.getInstance ();
        userDetailsPref.putStringPref (getApplicationContext (), UserDetailsPref.USER_FIREBASE_ID, token);
    }
}

