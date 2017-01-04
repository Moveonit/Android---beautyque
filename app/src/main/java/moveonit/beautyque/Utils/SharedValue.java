package moveonit.beautyque.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * Created by DANIELE on 15/11/2016.
 */

public class SharedValue {
    static public void setSharedPreferences(Context context, String key, String Value)
    {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, Value);
        editor.commit();
    }

    static public String getSharedPreferences(Context context, String key)
    {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getString(key, "");
    }

    static public <T> void setSharedPreferences(Context context, String key, T Value)
    {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Value);
        editor.putString(key, json);
        editor.commit();
    }

    static public <T> T getSharedPreferences(Context context, String key, Class<T> tClass)
    {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        return gson.fromJson(settings.getString(key, ""),tClass);
    }
}
