package com.ash.teacheron.commonComponents;


import static com.ash.teacheron.constants.Contants.*;
import static com.ash.teacheron.constants.Contants.Login_credentials;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefLocal {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public SharedPrefLocal(Context context) {
        sharedPreferences = context.getSharedPreferences(Login_credentials, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Method to retrieve User ID
    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, 0);  // Default to 0 if not found
    }

    // Method to retrieve Device ID
    public String getDeviceId() {
        return sharedPreferences.getString(KEY_DEVICE_ID, null);  // Default to null if not found
    }

    // Method to save User ID
    public void setUserId(int userId) {
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    // Method to save Device ID
    public void setDeviceId(String deviceId) {
        editor.putString(KEY_DEVICE_ID, deviceId);
        editor.apply();  // Save changes
    }

    public void setSessionId(String sessionId) {
        editor.putString(KEY_SESSION_ID, sessionId);
        editor.apply();
    }

    public String getSessionId() {
        return "Bearer "+sharedPreferences.getString(KEY_SESSION_ID, null);  // Default to null if not found
    }

    // Optional: Method to clear all data
    public void clear() {
        editor.clear();
        editor.apply();
    }

    public void setUserName(String value) {
        editor.putString(KEY_USER_NAME, value);
        editor.apply();
    }
    public void setProfileImage(String value) {
        editor.putString(KEY_USER_PROFILPIC, value);
        editor.apply();
    }

    public void setUserDisplay(String value) {
        editor.putString(KEY_USER_DISPLAY_NAME, value);
        editor.apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, "");  // Default to null if not found
    }

    public String getUserDisplayname() {
        return sharedPreferences.getString(KEY_USER_DISPLAY_NAME, "");  // Default to null if not found
    }

    public String getUserPhone() {
        return sharedPreferences.getString(KEY_USER_PHONE, "");  // Default to null if not found
    }

    public String getUserType() {
        return sharedPreferences.getString(KEY_USER_TYPE, "");  // Default to null if not found
    }



    public String getUserProfileImage() {
        return sharedPreferences.getString(KEY_USER_PROFILPIC, "");  // Default to null if not found
    }


    public void setUserEmail(String val) {
        editor.putString(KEY_EMAIL_ID, val);
        editor.apply();
    }

    public void setUserPassword(String val) {
        editor.putString(KEY_PASSWORD, val);
        editor.apply();
    }

    public String getUserEmail() {
        return sharedPreferences.getString(KEY_EMAIL_ID, "");  // Default to null if not found
    }

    public String getUserPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");  // Default to null if not found
    }


    public void setUserPhone(String val) {
        editor.putString(KEY_USER_PHONE, val);
        editor.apply();
    }

    public void setUserType(String val) {
        editor.putString(KEY_USER_TYPE, val);
        editor.apply();
    }


    //new data

    public void setUserLocation(String value) {
        editor.putString(KEY_USER_LOCATION, value);
        editor.apply();
    }
    public void setUserDOB(String value) {
        editor.putString(KEY_USER_DOB, value);
        editor.apply();
    }
    public void setUserFEEDETAIL(String value) {
        editor.putString(KEY_USER_FEEDETAIL, value);
        editor.apply();
    }
    public void setUserFeeAmount(String value) {
        editor.putString(KEY_USER_FEEAMOUNT, value);
        editor.apply();
    }
    public void setUserExperience(String value) {
        editor.putString(KEY_USER_EXPE, value);
        editor.apply();
    }
    public void setUserAssocia(String value) {
        editor.putString(KEY_USER_ASSOCIA, value);
        editor.apply();
    }

    public void setUserSchedule(String value) {
        editor.putString(KEY_USER_SCHEDULE, value);
        editor.apply();
    }


    public String getUserLocation() {
        return sharedPreferences.getString(KEY_USER_LOCATION, "");
    }

    public String getUserDOB() {
        return sharedPreferences.getString(KEY_USER_DOB, "");
    }

    public String getUserFEEDETAIL() {
        return sharedPreferences.getString(KEY_USER_FEEDETAIL, "");
    }

    public String getUserFeeAmount() {
        return sharedPreferences.getString(KEY_USER_FEEAMOUNT, "");
    }

    public String getUserExperience() {
        return sharedPreferences.getString(KEY_USER_EXPE, "");
    }

    public String getUserAssocia() {
        return sharedPreferences.getString(KEY_USER_ASSOCIA, "");
    }

    public String getUserSchedule() {
        return sharedPreferences.getString(KEY_USER_SCHEDULE, "");
    }



}
