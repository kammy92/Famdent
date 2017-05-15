package com.actiknow.famdent.utils;

public class AppConfigURL {
    public static String version = "v1.2.1";
    public static String BASE_URL = "https://project-famdent-cammy92.c9users.io/api/" + version + "/";
//    public static String BASE_URL = "http://35.165.111.86/api/" + version + "/";


    public static String URL_GETOTP = BASE_URL + "visitor/otp";


    public static String URL_REGISTER = BASE_URL + "visitor/register";
    public static String URL_INIT = BASE_URL + "/init/application";
    public static String URL_LOGOUT = BASE_URL + "/logout";
    public static String URL_CHECK_VERSION = "check/status/version";

    public static String URL_EXHIBITOR_LIST = BASE_URL + "exhibitor";
    public static String URL_EXHIBITOR_FAVOURITE_LIST = BASE_URL + "exhibitor/favourite";
    public static String URL_EXHIBITOR_DETAIL = BASE_URL + "exhibitor";

    public static String URL_EVENT_LIST = BASE_URL + "event";
    public static String URL_EVENT_FAVOURITE_LIST = BASE_URL + "event/favourite";
    public static String URL_EVENT_DETAIL = BASE_URL + "event";

    public static String URL_EXHIBITOR_FAVOURITE = BASE_URL + "favourite/exhibitor";
    public static String URL_EVENT_FAVOURITE = BASE_URL + "favourite/event";
    public static String URL_SESSION_FAVOURITE = BASE_URL + "favourite/session";

    public static String URL_EXHIBITOR_NOTE = BASE_URL + "notes/exhibitor";

    public static String URL_SESSION_LIST = BASE_URL + "session";
    public static String URL_SESSION_FAVOURITE_LIST = BASE_URL + "session/favourite";
}
