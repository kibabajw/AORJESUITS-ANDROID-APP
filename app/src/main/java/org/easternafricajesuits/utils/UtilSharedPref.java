package org.easternafricajesuits.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UtilSharedPref {
    SharedPreferences sharedPref;


    public UtilSharedPref(Context context) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getStoragePreference(String preftitle) {
        return sharedPref.getString(preftitle, "0");
    }

    public void setPref(String preftitle, String prefStr) {
        sharedPref
                .edit()
                .putString(preftitle, prefStr)
                .apply();
    }

}
