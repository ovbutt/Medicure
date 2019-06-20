package com.example.ovais.medicure;

public class Config {

    public static final String URL_CREATE_USER = "http://ovaisbutt.000webhostapp.com/medicure/signup.php";
    public static final String LOGIN_URL = "http://ovaisbutt.000webhostapp.com/medicure/login.php";
    public static final String URL_USER_CHANGE_PASS = "http://ovaisbutt.000webhostapp.com/medicure/updatepass.php?";
    public static final String URL_VERIFY_USER = "http://ovaisbutt.000webhostapp.com/medicure/verifyuser.php?user_email=";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_USERNAME = "user_nicename";
    public static final String KEY_MAIL = "user_email";
    public static final String KEY_PASSWORD = "user_pass";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    // public static final String USERNAME_SHARED_PREF = "user_nicename";
    public static final String EMAIL_SHARED_PREF = "user_email";
    public static final String Name_SHARED_PREF = "user_name";

    // for signup
    public static final String SU_USER_NAME = "user_nicename";
    public static final String SU_USER_EMAIL  = "user_email";
    public static final String SU_USER_PASS = "user_pass";
    public static final String SU_USER_MOBILE  = "user_mbl";

    // For reset pass
    public static final String R_USER_ID = "user_email";
    public static final String R_USER_PASS = "user_pass";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    // For verifying the user
    public static final String VF_USER  = "name";
    public static final String JSON_ARRAY = "result";

}