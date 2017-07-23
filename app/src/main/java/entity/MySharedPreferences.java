package entity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by finefine.com on 2017/7/23.
 */

public class MySharedPreferences {
    public static final String TourLinkRegisterInfo = "register.info.tourlink";

    private Context mContext = null;

    public MySharedPreferences(Context c) {
        mContext = c;
    }

    /**
     *
     * @param key 默认为false
     * @return
     */
    public boolean getBoolean(String key) {
        SharedPreferences sharedPreferences = mContext
                .getSharedPreferences(TourLinkRegisterInfo,
                        Context.MODE_PRIVATE);
        boolean rBool = sharedPreferences.getBoolean(key, false);

        return rBool;
    }

    public void setBoolean(String key, boolean tf) {
        SharedPreferences sharedPreferences = mContext
                .getSharedPreferences(TourLinkRegisterInfo,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, tf);
        editor.commit();
    }

    public String Read(String key) {
        SharedPreferences sharedPreferences = mContext
                .getSharedPreferences(TourLinkRegisterInfo,
                        Context.MODE_PRIVATE);
        String rStr = sharedPreferences.getString(key, null);
        return rStr;
    }

    public void Write(String key, String value) {
        SharedPreferences sharedPreferences = mContext
                .getSharedPreferences(TourLinkRegisterInfo,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void Remove(String key) {
        SharedPreferences sharedPreferences = mContext
                .getSharedPreferences(TourLinkRegisterInfo,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
