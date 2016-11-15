package moveonit.beautyque.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
}
