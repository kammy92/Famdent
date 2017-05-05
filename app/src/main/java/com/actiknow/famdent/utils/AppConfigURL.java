package com.actiknow.famdent.utils;

public class AppConfigURL {

    //    public static String BASE_URL = "https://blood-connect-cammy92.c9users.io/api/v1/user";
//    public static String BASE_URL = "http://www.actiknow-test.in/bloodkonnect/api/v1/user";
//    public static String BASE_URL = "http://ec2-52-42-89-17.us-west-2.compute.amazonaws.com/bloodkonnect/api/v1/user";
//    public static String BASE_URL = "http://52.39.244.218/bloodkonnect/api/v1/user";

    public static String version = "v1";
    public static String BASE_URL = "https://project-famdent-cammy92.c9users.io/api/" + version + "/";

    public static String URL_GETOTP = BASE_URL + "visitor/otp";


    public static String URL_REGISTER = BASE_URL + "visitor/register";
    public static String URL_INIT = BASE_URL + "/init";
    public static String URL_LOGOUT = BASE_URL + "/logout";
    public static String URL_CHECK_VERSION = "check/status/version";

    public static String URL_EXHIBITOR_LIST = BASE_URL + "exhibitor";

    public static String URL_EVENT = BASE_URL + "event";

}
