package com.phyapp.root.physiotherapy.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.phyapp.root.physiotherapy.LoginActivity;
import com.phyapp.root.physiotherapy.MainActivity;
import com.phyapp.root.physiotherapy.RegisterActivity;
import com.phyapp.root.physiotherapy.SelectionActivity;

import java.util.HashMap;

/**
 * Created by nbays on 15/2/18.
 */

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";


    private static final String IS_Lang_selected = "IsLang";

    private static final String IS_Lang_Badge = "Isbadage";

    // User name (make variable public to access from outside)
    public static final String KEY_USERID = "userId";


    public static final String KEY_Badge = "bada";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // Email address (make variable public to access from outside)
    public static final String KEY_LOGIN_TYPE = "loginType";

    // Email address (make variable public to access from outside)
    public static final String KEY_USER_NAME = "userName";

    // Email address (make variable public to access from outside)
    public static final String KEY_PROFILE_IMAGE = "profileImage";


    public static final String KEY_PROFILE_NUMBER = "1234687";


    public static final String KEY_USERS_TYPE="Engineer";


    public static final String KEY_USERS_ADDRESS="addresssssssssss";


    public static final String KEY_USERS_Language="Engl";


    public static final String KEY_Admin_number="14876";










    // Constructor
    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createloginNumber(String phonenumber,String loginType,String userId)
    {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_PROFILE_NUMBER,phonenumber);

        editor.putString(KEY_LOGIN_TYPE, loginType);

        editor.putString(KEY_USERID, userId);

        editor.commit();

    }


    /**
     * Create login session
     * */
    public void createLoginSession(String userId, String loginType, String userName, String phonenumber, String email, String address){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_USERID, userId);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // Storing loginType in pref
        editor.putString(KEY_LOGIN_TYPE, loginType);

        // Storing loginType in pref
        editor.putString(KEY_USER_NAME, userName);

        // Storing loginType in pref
        //editor.putString(KEY_PROFILE_IMAGE, profileImage);

        editor.putString(KEY_PROFILE_NUMBER,phonenumber);


        editor.putString(KEY_USERS_ADDRESS,address);

        // commit changes
        editor.commit();
    }



    public void  CreatelanguageSelect(String lan)
    {
        editor.putBoolean(IS_Lang_selected, true);

        editor.putString(KEY_USERS_Language,lan);

        editor.commit();


    }


    /**
     * Create login session
     * */
    public void createDoctorLoginSession(String userId, String loginType, String userName, String phonenumber, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_USERID, userId);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // Storing loginType in pref
        editor.putString(KEY_LOGIN_TYPE, loginType);

        // Storing loginType in pref
        editor.putString(KEY_USER_NAME, userName);

        // Storing loginType in pref
        //editor.putString(KEY_PROFILE_IMAGE, profileImage);

        editor.putString(KEY_PROFILE_NUMBER,phonenumber);


      //  editor.putString(KEY_USERS_ADDRESS,address);

        // commit changes
        editor.commit();
    }


    public void CreateBadge(){

        editor.putBoolean(IS_Lang_Badge,true);

       // editor.putString(KEY_Badge,bda);

        editor.commit();
    }

    public  void createAdminnosession(String num)
    {

        editor.putString(KEY_Admin_number,num);

        editor.commit();

    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_USERID, pref.getString(KEY_USERID, null));


        user.put(KEY_PROFILE_NUMBER, pref.getString(KEY_PROFILE_NUMBER, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // user login type
        user.put(KEY_LOGIN_TYPE, pref.getString(KEY_LOGIN_TYPE, null));

        // user name
        user.put(KEY_USER_NAME, pref.getString(KEY_USER_NAME, null));

        // user profile pic
        user.put(KEY_PROFILE_IMAGE, pref.getString(KEY_PROFILE_IMAGE, null));

         // user login type
        user.put(KEY_USERS_TYPE, pref.getString(KEY_USERS_TYPE, null));


        user.put(KEY_USERS_Language, pref.getString(KEY_USERS_Language, null));


        user.put(KEY_USERS_ADDRESS, pref.getString(KEY_USERS_ADDRESS, null));


        user.put(KEY_Admin_number, pref.getString(KEY_Admin_number, null));



        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser()
    {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
       // Toast.makeText(context, "shared cleared", Toast.LENGTH_SHORT).show();

        Intent i=new Intent(context,SelectionActivity.class);


        // Closing all the Activities
         i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
         i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);

        // After logout redirect user to Loing Activity
       // Intent i = new Intent(context, MainActivity.class);
        // Closing all the Activities
       // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
       // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
       // context.startActivity(i);

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isLanguageSelected(){
        return pref.getBoolean(IS_Lang_selected, false);
    }

 public boolean isBadageSelected(){
        return pref.getBoolean(IS_Lang_Badge, false);
    }


}
