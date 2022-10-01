package tn.scolarite.isi.service;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SharedPreferences {

    private static final String PREFS_NAME = "Prefs";

    public static void save(String data, Context context) {
        android.content.SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("auth", data);
        editor.commit();
    }

    public static String read(Context context) {
        android.content.SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String auth = prefs.getString("auth", "");
        return auth;
    }

}
