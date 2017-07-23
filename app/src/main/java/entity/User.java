package entity;

import android.content.Context;

/**
 * Created by finefine.com on 2017/7/23.
 */

public class User {
    public static final String TAG = "reg.io";
    public static final String KeyAccount = "flag_account_string";
    public static final String KeyPassword = "flag_password_string";
    public static final String KeyNick = "flag_nick_string";
    public static final String KeyInviteCode = "flag_invite_string";
    public static final String KeyHasRegistered = "flag_has_registered_bool";
    public static final String KeyIsVisitor = "flag_visitor_bool";
    public static final String KeyAuthCode = "flag_auth_code";

    private Context mContext = null;
    private MySharedPreferences myIO;

    public User(Context mContext) {
        this.mContext = mContext;
        myIO=new MySharedPreferences(mContext);
    }


    public String getUserAccount() {
        return myIO.Read(KeyAccount);
    }

    public void saveUserInfo(String name, String nick, String pass) {
        myIO.Write(KeyAccount, name);
        myIO.Write(KeyNick, nick);
        myIO.Write(KeyPassword, pass);
    }

    public void setNickName(String nick) {
        myIO.Write(KeyNick, nick);
    }

    public void removeUserInfo() {
        myIO.Remove(KeyHasRegistered);
        myIO.Remove(KeyAccount);
        myIO.Remove(KeyPassword);
    }

    public void setVisitor(boolean yn) {
        myIO.setBoolean(KeyIsVisitor, yn);
        return;
    }

    public void setUserRegistered() {
        myIO.setBoolean(KeyHasRegistered, true);
        return;
    }

    public String getUserPassword()
    {
        return myIO.Read(KeyPassword);
    }
}
