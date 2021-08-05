package uz.revolution.mantiq_qani.dataBase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import uz.revolution.mantiq_qani.model.MyData;

import java.util.ArrayList;

public class Database {

    private static Database database;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private Database(Context context) {

        preferences = context.getSharedPreferences("contactList", Context.MODE_PRIVATE);

    }

    public static void init(Context context) {
        if (database == null) {
            database = new Database(context);
        }
    }


    public static Database getDatabase() {
        return database;
    }

    public void insertData(String name, String score, String time,String status) {
        editor = preferences.edit();
        editor.putString("name_" + getCount(), name);
        editor.putString("score_" + getCount(), score);
        editor.putString("time_" + getCount(), time);
        editor.putString("status_" + getCount(), status);
        editor.apply();
        setCount();
    }

    public String getData(String key){
        return preferences.getString(key, "0");
    }

    public int getScore(String key){
        return preferences.getInt(key, 0);
    }


    private void setCount() {
        int n = getCount();
        preferences.edit().putInt("count", n + 1).apply();
    }

    public int getCount() {
        return preferences.getInt("count",0);
    }

    public ArrayList<MyData> getUsersData() {
        ArrayList<MyData> list=new ArrayList<>();

        for (int i = 0; i < getCount(); i++) {

            list.add(
                    getUserData(i)
            );
        }

        return list;
    }

    private MyData getUserData(int i) {
        MyData myData = new MyData(
                preferences.getString("name_" + i, ""),
                preferences.getString("score_" + i, ""),
                preferences.getString("time_" + i, ""),
                preferences.getString("status_" + i, "1")

        );
        return myData;
    }

    @SuppressLint("CommitPrefEdits")
    public void deleteAllData() {
        preferences.edit().clear().apply();
    }




}
