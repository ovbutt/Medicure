package com.example.ovais.medicure;

/**
 * Created by Ovais Butt on 3/2/2018.
 */
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    public static Context mCtx;

    public static final String SHARED_PREF_NAME = "myloginapp";
    //public static final String KEY_USERNAME = "user_nicename";
    public static final String KEY_USER_EMAIL = "user_email";
    //public static final String KEY_USER_ID = "userid";


    private SharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id, String username, String email){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

      //  editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USER_EMAIL, email);
        //editor.putString(KEY_USERNAME, username);

        editor.apply();

        return true;
    }
//
//    public boolean isLoggedIn(){
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        if(sharedPreferences.getString(KEY_USERNAME, null) != null){
//            return true;
//        }
//        return false;
//    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


//    public String getUsername(){
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(KEY_USERNAME, null);
//    }

    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }
}
