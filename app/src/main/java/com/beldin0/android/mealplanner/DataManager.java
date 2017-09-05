package com.beldin0.android.mealplanner;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DataManager {

    private static final String PLANS_FOLDER = "PLANS";
    private static final String MEALS_DATABASE = "user_meals";
    private static final String INGREDIENTS_DATABASE = "user_ingredients";
    private static DataManager instance;
    private static Context _context;

    private DataManager(Context context) {
        this._context = context;
    }

    public static DataManager getInstance(Context c) {
        _context = c;
        instance = null;
        return getInstance();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager(_context);
        }
        return instance;
    }

    public static List<String> getPlans() {

        List<String> plans = null;
        try {
            File path = _context.getExternalFilesDir(PLANS_FOLDER);
            plans = Arrays.asList(path.list());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plans;
    }

    public static void deletePlan(String filename) {
        File file = new File(_context.getExternalFilesDir(PLANS_FOLDER), filename);
        file.delete();
    }

    public IngredientList getIngredients() {

        IngredientList tmp = new IngredientList();
        String jsonString = null;
        try {
            jsonString = loadJSON(null, INGREDIENTS_DATABASE);
            if (ActivityMain.LOGGING) Log.d("getIngredients", jsonString);
            tmp.putAllFromJSON(jsonString);
        } catch (JSONException | FileNotFoundException e) {
            jsonString = getJsonFromAssets("ingredients");
            try {
                tmp.putAllFromJSON(jsonString);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        //saveIngredients(tmp.toJSON());
        return tmp;
    }

    public MealList getMeals() {

        MealList tmp = new MealList();
        String jsonString = null;
        try {
            jsonString = loadJSON(null, MEALS_DATABASE);
            tmp.addAllFromJSON(jsonString);
        } catch (JSONException | FileNotFoundException e) {
            jsonString = getJsonFromAssets("meals");
            try {
                tmp.addAllFromJSON(jsonString);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        Collections.sort(tmp);
        //saveMeals(tmp.toJSON());
        return tmp;
    }

    public void savePlan(String filename, String jsonString) {
        if (!saveJSON(PLANS_FOLDER, filename, jsonString) & ActivityMain.LOGGING) {
            Log.d("saveIngredients:", "Saving failed");
        }
        ;
    }

    public String loadPlan(String filename) throws FileNotFoundException {
        return loadJSON(PLANS_FOLDER, filename);
    }

    public void saveIngredients(String jsonString) {
        if (!saveJSON(null, INGREDIENTS_DATABASE, jsonString) & ActivityMain.LOGGING) {
            Log.d("saveIngredients:", "Saving failed");
        }
        ;
    }

    public void saveMeals(String jsonString) {
        if (!saveJSON(null, MEALS_DATABASE, jsonString) & ActivityMain.LOGGING) {
            Log.d("saveIngredients:", "Saving failed");
        }
        ;
    }

    public String getJsonFromAssets(String filename) {
        String json;
        try {
            InputStream is = _context.getAssets().open(filename + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (ActivityMain.LOGGING) {
            Log.d("getFromAssets", filename);
        }
        return json;
    }

    public void exportJSON(String name, String jsonString) {

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

    public String importJSON(String name) {

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

    public boolean saveJSON(String folder, String name, String jsonString) {
        if (ActivityMain.LOGGING) Log.d("saveJSON", "Saving " + folder + "/" + name);
        File path = _context.getExternalFilesDir(folder);
        if (!path.exists()) path.mkdir();
        File file = new File(path, name + ".json");
        try {
            OutputStream os = new FileOutputStream(file);
            Writer w = new OutputStreamWriter(os);
            w.write(jsonString);
            w.close();
            os.close();

        } catch (IOException e) {
            Log.w("saveJSON", "Error writing " + file, e);
        }
        return file.exists();
    }

    private String loadJSON(String folder, String name) throws FileNotFoundException {

        if (!(name.contains(".json"))) name = name + ".json";
        if (ActivityMain.LOGGING) Log.d("loadJSON", "Loading " + folder + "/" + name);

        File path = _context.getExternalFilesDir(folder);
        File file = new File(path, name);
        if (file.exists()) {
            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader bfr = new BufferedReader(
                        new FileReader(file));

                String data;
                while ((data = bfr.readLine()) != null) {
                    sb.append(data);
                }
                bfr.close();

            } catch (IOException e) {
                Log.w("loadJSON", "Error reading " + file, e);
            }
            return sb.toString();
        } else {
            throw new FileNotFoundException();
        }
    }

    public void deleteUserData() {
        File path = _context.getExternalFilesDir(null);
        File file;
        file = new File(path, INGREDIENTS_DATABASE + ".json");
        file.delete();
        file = new File(path, MEALS_DATABASE + ".json");
        file.delete();
    }
}
