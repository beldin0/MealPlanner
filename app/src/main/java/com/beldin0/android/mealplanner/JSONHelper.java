package com.beldin0.android.mealplanner;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by beldi on 03/08/2017.
 */

public class JSONHelper {

    public static String getAsJSON(Context c, String filename) {
        String json = null;
        try {
            InputStream is = c.getAssets().open(filename + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static void saveJSON(String name, String jsonString) {

        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, name + ".json");

        try {
            path.mkdirs();
            OutputStream os = new FileOutputStream(file);
            Writer w = new OutputStreamWriter(os);
            w.write(jsonString);
            w.close();
            os.close();

        } catch (IOException e) {
            Log.w("ExternalStorage", "Error writing " + file, e);
        }
    }

    public static String loadJSON(String name) {

        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, name + ".json");
        StringBuilder sb = new StringBuilder();
        try {
            path.mkdirs();
            BufferedReader bfr = new BufferedReader(
                    new FileReader(file));

            String data;
            while ((data = bfr.readLine()) != null) {
                sb.append(data);
            }
            bfr.close();

        } catch (IOException e) {
            Log.w("ExternalStorage", "Error writing " + file, e);
        }
        return sb.toString();
    }
}
