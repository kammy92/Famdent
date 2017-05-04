package com.actiknow.famdent.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class VisitorDetailsPref {
    public static String VISITOR_ID = "visitor_id";
    public static String VISITOR_NAME = "visitor_name";
    public static String VISITOR_EMAIL = "visitor_email";
    public static String VISITOR_MOBILE = "visitor_mobile";
    public static String VISITOR_TYPE = "visitor_type";
    public static String VISITOR_LOGIN_KEY = "visitor_login_key";
    public static String VISITOR_FIREBASE_ID = "visitor_firebase_id";
    public static String LOGGED_IN_SESSION = "logged_in_session";
    private static VisitorDetailsPref visitorDetailsPref;
    private String USER_DETAILS = "USER_DETAILS";

    public static VisitorDetailsPref getInstance () {
        if (visitorDetailsPref == null)
            visitorDetailsPref = new VisitorDetailsPref ();
        return visitorDetailsPref;
    }

    private SharedPreferences getPref (Context context) {
        return context.getSharedPreferences (USER_DETAILS, Context.MODE_PRIVATE);
    }

    public String getStringPref (Context context, String key) {
        return getPref (context).getString (key, "");
    }

    public int getIntPref (Context context, String key) {
        return getPref (context).getInt (key, 0);
    }

    public boolean getBooleanPref (Context context, String key) {
        return getPref (context).getBoolean (key, false);
    }

    public void putBooleanPref (Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getPref (context).edit ();
        editor.putBoolean (key, value);
        editor.apply ();
    }

    public void putStringPref (Context context, String key, String value) {
        SharedPreferences.Editor editor = getPref (context).edit ();
        editor.putString (key, value);
        editor.apply ();
    }

    public void putIntPref (Context context, String key, int value) {
        SharedPreferences.Editor editor = getPref (context).edit ();
        editor.putInt (key, value);
        editor.apply ();
    }
}
