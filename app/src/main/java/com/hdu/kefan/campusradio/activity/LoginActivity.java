package com.hdu.kefan.campusradio.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.algebra.sdk.API;
import com.algebra.sdk.AccountApi;
import com.algebra.sdk.DeviceApi;
import com.algebra.sdk.OnAccountListener;
import com.algebra.sdk.entity.Constant;
import com.algebra.sdk.entity.Contact;
import com.algebra.sdk.entity.OEMToneGenerator;
import com.algebra.sdk.entity.OEMToneProgressListener;
import com.algebra.sdk.entity.UserProfile;
import com.hdu.kefan.campusradio.R;

import java.util.List;

import entity.MsgCode;
import entity.MySharedPreferences;
import entity.RegisterUser;
import entity.User;


public class LoginActivity extends BaseActivity implements View.OnClickListener,OnAccountListener{

    private static final int REQUEEST_PERMISSION_OK=1;
    private static final String TAG = "LoginActivity";
    private AccountApi accountApi;
    private DeviceApi deviceApi;
    private boolean newBind = true;
    private boolean needUnbind = true;

    private String userAccount = null;
    private String userPass = null;
    private String userNick = "???";
    private String userPhone = null;
    private boolean userBoundPhone = false;
    private boolean isVisitor = true;
    private boolean isLoginPhone=false;


    public static final String KeyAccount = "flag_account_string";
    public static final String KeyPassword = "flag_password_string";
    public static final String KeyNick = "flag_nick_string";
    public static final String KeyInviteCode = "flag_invite_string";
    public static final String KeyHasRegistered = "flag_has_registered_bool";
    public static final String KeyIsVisitor = "flag_visitor_bool";
    public static final String KeyAuthCode = "flag_auth_code";

    private Context mContext = null;
    private int mResource = 0;
    private MySharedPreferences myIO;

    private User user;

    private TextView newUser;
    private TextView forgetPassword;
    private TextView login;
    private EditText userName;
    private EditText userPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        initViews();
        initEvents();


        user=new User(this);
        mContext=this;

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},REQUEEST_PERMISSION_OK);
        }
        else
        {
            newBind=API.init(getApplicationContext());
            handler.postDelayed(delayInitApi,300);
        }
    }


    private void initEvents() {
        login.setOnClickListener(this);
    }

    private void initViews() {
        newUser= (TextView) findViewById(R.id.activity_login_textView_newuser);
        forgetPassword= (TextView) findViewById(R.id.activity_login_textView_forgetpassword);
        login= (TextView) findViewById(R.id.activity_login_textView_login);
        userName= (EditText) findViewById(R.id.activity_login_editText_username);
        userPassword= (EditText) findViewById(R.id.activity_login_editText_userpassword);

        user=new User(this);
        userName.setText(user.getUserAccount());
        userPassword.setText(user.getUserPassword());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.activity_login_textView_login:
                login();
                break;
        }
    }

    public void login(){
        isLoginPhone=userName.getText().toString().length() == 11;
        //输入账号登录
        if (!isLoginPhone) {//如果不是手机号登录
            if (userName.getText().toString() == null || userName.getText().length() < 5) {
                Toast.makeText(mContext,mContext.getResources().getString(R.string.need_account), Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                if (userPassword.getText().toString() == null || userPassword.getText().toString().length() < 4) {
                    Toast.makeText(mContext,mContext.getResources().getString(R.string.need_passwd), Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    if(accountApi!=null)
                    {
                        accountApi.login(userName.getText().toString(),API.md5(userPassword.getText().toString()));
                    }
                    else
                    {
                        handler.postDelayed(delayInitApi,300);
                    }
                }
            }
        } else {//如果是手机号登录
            if (userName.getText().toString() == null || userName.getText().toString().length() != 11) {
                Toast.makeText(mContext,mContext.getResources().getString(R.string.error_phoneno), Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                if (userPassword.getText().toString() == null || userPassword.getText().toString().length() < 4) {
                    Toast.makeText(mContext,mContext.getResources().getString(R.string.need_passwd), Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    if(accountApi!=null)
                    {
                        accountApi.loginPhone(userName.getText().toString(),API.md5(userPassword.getText().toString()));
                    }
                    else
                    {
                        handler.postDelayed(delayInitApi,300);
                    }
                }
            }
        }


    }



    private Runnable delayInitApi = new Runnable() {
        @Override
        public void run() {
            accountApi = API.getAccountApi();
            deviceApi = API.getDeviceApi();
            if (accountApi != null && deviceApi != null) {
                accountApi.setOnAccountListener(LoginActivity.this);
                setupToneGen();
                Contact me = accountApi.whoAmI();
                Log.d(TAG, "run: sssssssssssssssssss");
                if (me != null) {
                    userBoundPhone = !me.phone.equals("none");
                    isVisitor = me.visitor;
                    userNick = new String(me.name);
                    userAccount = user.getUserAccount();
                    android.util.Log.d(TAG, "Poc sdk for uid: " + me.id
                            + " is running, self state:" + me.state
                            + ", link in.");
                    handler.obtainMessage(MsgCode.MC_SDKISRUNNING, me.id,
                            me.state).sendToTarget();
                } else {
//                    handler.sendEmptyMessage(MsgCode.ASKFORSTARTSDK);
//                    accountApi.login("00662934",API.md5("716679"));
                }
            } else {
                if (handler != null) {
                    android.util.Log.d(TAG,
                            "start SDK and waiting another 300ms.");
                    handler.postDelayed(delayInitApi, 300);
                }
            }
        }
    };



    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what)
            {
                case MsgCode.MC_LOGINOK: // arg1: userId, arg2: userState//成功登录后，主要用于保存用户信息
                    user.setVisitor(false);
                    user.setUserRegistered();
//                    user.saveUserInfo(mActi.userAccount, mActi.userNick, mActi.userPass);

                    // no break here.
//                case MsgCode.MC_SDKISRUNNING:
//                    mActi.selfId = msg.arg1;
//                    mActi.initMyADChannels();
//                    mActi.initNotificationLauncher();
//                    mActi.start_channel_fragment(mActi.selfId, msg.arg2);
//                    mActi.start_talk_fragment(mActi.selfId, msg.arg2);
                    break;

                case MsgCode.ASKFOREXIT:
                    if (newBind) {
                        newBind = false;
                        needUnbind = true;
                        finish();
                    } else {
                        needUnbind = false;
                        finish();
                    }
                    break;
                //
            }
        }
    };



    /**
     * 开启游客模式
     * 如果是第一次启动程序，则进入游客登陆模式，并让游客输入昵称
     * @param resource
     */
    public void startVisitor(int resource) {
        mResource = resource;
        //key如果不存在，就进入游客注册
//		if (!myIO.getBoolean(KeyHasRegistered)) {
//			DialogVisitor loginDialog = new DialogVisitor(mContext,
//					R.style.loginDialogStyle, mResource, handler, null, null);
//			loginDialog.show();
//		} else if (myIO.getBoolean(KeyIsVisitor)) {
//			String account = myIO.Read(KeyAccount);
//			String nick = myIO.Read(KeyNick);
//			DialogVisitor loginDialog = new DialogVisitor(mContext,
//					R.style.loginDialogStyle, mResource, handler, account, nick);
//			loginDialog.show();
//		} else {
//			handler.obtainMessage(MsgCode.LOGINPAGE, null).sendToTarget();
//		}
    }

    /**
     * 用户登录
     * @param resource
     */
    public void startUserLogin(int resource) {
        mResource = resource;
//
//		if (!myIO.getBoolean(KeyHasRegistered) || myIO.getBoolean(KeyIsVisitor)) {
//			DialogLogin loginDialog = new DialogLogin(mContext,
//					R.style.loginDialogStyle, mResource, handler, null, null);
//			loginDialog.show();
//		} else {
//			String account = myIO.Read(KeyAccount);
//			String passwd = myIO.Read(KeyPassword);
//			DialogLogin loginDialog = new DialogLogin(mContext,
//					R.style.loginDialogStyle, mResource, handler, account,
//					passwd);
//			loginDialog.show();
//		}
    }

    /**
     * 用户注册
     * @param resource
     */
    public void startRegister(int resource) {
        mResource = resource;
//		DialogRegister loginReg = new DialogRegister(mContext,
//				R.style.loginDialogStyle, mResource, handler);
//		loginReg.show();
    }

    /**
     * 开始重置密码
     * @param resource
     * @param uid
     * @param uAccount
     */
    public void startResetPass(int resource, int uid, String uAccount) {
        mResource = resource;
//		DialogResetPass loginReg = new DialogResetPass(mContext,
//				R.style.loginDialogStyle, mResource, handler, uid, uAccount);
//		loginReg.show();
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEEST_PERMISSION_OK:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    newBind=API.init(getApplicationContext());
                    handler.postDelayed(delayInitApi,300);
                }else
                {
                    Toast.makeText(mContext, "你拒绝了权限，即将推出程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }

    }


    private class OEMToneGen implements OEMToneGenerator {
        private OEMToneProgressListener toneProgressListener = null;
        private ToneGenerator mToneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 15);

        void setToneProgressListener(OEMToneProgressListener l) {
            toneProgressListener = l;
        }

        @Override
        public void alertTone(final int type) {
            mToneGen.startTone(ToneGenerator.TONE_DTMF_5);
            handler.postDelayed(new Runnable(){
                @Override
                public void run() {
                    mToneGen.stopTone();
                    if (toneProgressListener != null)
                        toneProgressListener.onToneStopped(type);
                }
            }, 100);
        }
    }

    private OEMToneGen oemToneGen = new OEMToneGen();
    private void setupToneGen() {
        deviceApi.setOemToneGenerator(oemToneGen);
        OEMToneProgressListener listener = deviceApi.getToneProgressListener();
        oemToneGen.setToneProgressListener(listener);
    }

    private class LoginFailureShow implements Runnable {
        private String loginFailureTxt = null;

        public LoginFailureShow(String txt) {
            loginFailureTxt = txt;
        }

        @Override
        public void run() {
            Toast.makeText(LoginActivity.this, loginFailureTxt, Toast.LENGTH_SHORT)
                    .show();
        }
    }


    private void loginProgressHint(String txt) {
        LoginFailureShow lfs = new LoginFailureShow(txt);
        runOnUiThread(lfs);
    }


    @Override
    public void onLogin(int uid, int result, UserProfile uProfile) {

        handler.obtainMessage(MsgCode.MC_LOGINFINISHED, 2, 0).sendToTarget();
        System.out.println("uid " + uid + " onlogin result: " + result);

        if (result == Constant.ACCOUNT_RESULT_OK
                || result == Constant.ACCOUNT_RESULT_ALREADY_LOGIN) {
            if (uProfile != null) {
                userBoundPhone = !uProfile.userPhone.equals("none");
                userAccount = new String(uProfile.userName);
                userNick = new String(uProfile.userNick);
                isVisitor = (uProfile.userType == Constant.USER_TYPE_VISITOR);
            }
            handler.obtainMessage(MsgCode.MC_LOGINOK, uid,
                    Constant.CONTACT_STATE_ONLINE).sendToTarget();
            user.saveUserInfo(String.valueOf(uid),userAccount,userNick,userPassword.getText().toString());
            startActivity(new Intent(mContext,MainActivity.class));
            finish();
            return;
        }

        if (result == Constant.ACCOUNT_RESULT_PREOK) {
            loginProgressHint("Login...");
            return;
        }

        if (result == Constant.ACCOUNT_RESULT_TIMEOUT) {
            loginProgressHint(getString(R.string.err_timeout));
            user.removeUserInfo();
            handler.sendEmptyMessage(MsgCode.ASKFOREXIT);
            return;
        }

        if (result == Constant.ACCOUNT_RESULT_ERR_USER_NOTEXIST) {
            user.removeUserInfo();
            loginProgressHint(getString(R.string.err_account));
        }
        if (result == Constant.ACCOUNT_RESULT_ERR_USER_PWD) {
            user.removeUserInfo();
            loginProgressHint(getString(R.string.err_userpass));
        }
        if (result == Constant.ACCOUNT_RESULT_ERR_SERVER_UNAVAILABLE) {
            loginProgressHint(getString(R.string.err_serverunava));
        }
        if (result == Constant.ACCOUNT_RESULT_ERR_NETWORK) {
            loginProgressHint(getString(R.string.err_network));
        }
        if (result == Constant.ACCOUNT_RESULT_OTHER_LOGIN) {
            user.removeUserInfo();
            loginProgressHint(getString(R.string.err_nouser));
        }
        handler.sendEmptyMessage(MsgCode.ASKFORSTARTSDK);
        return;
    }

    @Override
    public void onCreateUser(int i, int i1, String s) {

    }

    @Override
    public void onLogout() {

    }

    @Override
    public void onSetNickName(int i) {

    }

    @Override
    public void onChangePassWord(int i, boolean b) {

    }

    @Override
    public void onAskUnbind(int i) {

    }

    @Override
    public void onAuthRequestReply(int i, int i1, String s) {

    }

    @Override
    public void onAuthBindingReply(int i, int i1, String s) {

    }

    @Override
    public void onAuthRequestPassReply(int i, int i1, String s) {

    }

    @Override
    public void onAuthResetPassReply(int i, int i1) {

    }

    @Override
    public void onFriendsSectionGet(int i, int i1, int i2, int i3, List<Contact> list) {

    }

    @Override
    public void onFriendStatusUpdate(int i, int i1, int i2) {

    }

    @Override
    public void onShakeScreenAck(int i, int i1, int i2) {

    }

    @Override
    public void onShakeScreenReceived(int i, String s, String s1) {

    }

    @Override
    public void onSetStatusReturn(int i, boolean b) {

    }

    @Override
    public void onHearbeatLost(int i, int i1) {

    }

    @Override
    public void onKickedOut(int i, int i1) {

    }

    @Override
    public void onSelfStateChange(int i, int i1) {

    }

    @Override
    public void onSelfLocationAvailable(int i, Double aDouble, Double aDouble1, Double aDouble2) {

    }

    @Override
    public void onSelfLocationReported(int i) {

    }

    @Override
    public void onUserLocationNotify(int i, String s, Double aDouble, Double aDouble1) {

    }

    @Override
    public void onLogger(int i, String s) {

    }

    @Override
    public void onSmsRequestReply(int i) {

    }
}
