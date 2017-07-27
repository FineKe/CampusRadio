package com.hdu.kefan.campusradio.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.algebra.sdk.API;
import com.algebra.sdk.DeviceApi;
import com.algebra.sdk.OnMediaListener;
import com.algebra.sdk.OnSessionListener;
import com.algebra.sdk.SessionApi;
import com.algebra.sdk.entity.Channel;
import com.algebra.sdk.entity.CompactID;
import com.algebra.sdk.entity.Constant;
import com.algebra.sdk.entity.Contact;
import com.algebra.sdk.entity.HistoryRecord;
import com.hdu.kefan.campusradio.R;

import java.util.ArrayList;
import java.util.List;

import entity.MsgCode;
import entity.TalkHistory;

public class StreamingActivity extends AppCompatActivity implements OnSessionListener,OnMediaListener,View.OnTouchListener,View.OnClickListener{
    private static final String TAG = "StreamingActivity";


    private SessionApi sessionApi=null;
    private DeviceApi deviceApi=null;
    private Context uiContext = null;

    private AudioManager mAudioManager = null;
    private ComponentName mediaKeys = null;
    private TalkHistory talkHistory = null;

    private int selfId = 0;
    private String selfNick = null;
    private CompactID currSession = null;
    private CompactID dispSession = null;
    private int selfStatus = Constant.CONTACT_STATE_ONLINE;
    private boolean isUndistube = false;
    private HistoryRecord newLastSpeaking = null;

    private ArrayList<Integer> waitList = new ArrayList<Integer>();
    private static boolean pttTriggerable = false;

    private LinearLayout speaking;
    private ImageView settingVolume;
    private ImageView exit;
    private ImageView audiences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_streaming);

        selfId=getIntent().getIntExtra("id.self",0);
        selfId=4850;
        selfNick=getIntent().getStringExtra("nick.self");
        selfStatus=getIntent().getIntExtra("status.self",0);
        Log.i(TAG, "onCreate: "+selfId+"sataus"+selfStatus);

        mAudioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        talkHistory=new TalkHistory(selfId);

        sessionApi= API.getSessionApi();
        if(sessionApi!=null)
        {
            sessionApi.setOnSessionListener(this);

            sessionApi.sessionCall(4850,3,10);

        }

        initViews();
        initEvents();


    }

    private void initEvents() {
        speaking.setOnTouchListener(this);
        settingVolume.setOnClickListener(this);
        exit.setOnClickListener(this);
        audiences.setOnClickListener(this);
    }

    private void initViews() {
        speaking= (LinearLayout) findViewById(R.id.activity_streaming_linearLayout_speaking);
        settingVolume= (ImageView) findViewById(R.id.activity_streaming_imageView_setting_volume);
        exit= (ImageView) findViewById(R.id.activity_streaming_imageView_action_exit);
        audiences= (ImageView) findViewById(R.id.activity_streaming_imageView_audiences);

    }




    private void talkRequest(CompactID session) {
        if (session != null && sessionApi != null)
            sessionApi.talkRequest(selfId, session.getType(), session.getId());
    }

    private void talkRelease(CompactID session) {
        if (session != null && sessionApi != null)
            sessionApi.talkRelease(selfId, session.getType(), session.getId());
    }


    private Handler uiHandler =new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private Runnable delayInitApi = new Runnable() {
        @Override
        public void run() {
            if ((sessionApi = API.getSessionApi()) != null) {
                deviceApi = API.getDeviceApi();
                sessionApi.setOnMediaListener(StreamingActivity.this);
                isUndistube = API.getAccountApi().isUndistubed();
            } else {
                uiHandler.postDelayed(delayInitApi, 300);
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        if (sessionApi == null) {
            uiHandler.postDelayed(delayInitApi, 300);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy ....");

        if (sessionApi != null) {
            sessionApi.setOnMediaListener(null);
            sessionApi = null;
        } else if (uiHandler != null) { // don't know why uiHandler can be null.
            uiHandler.removeCallbacks(delayInitApi);
        }
    }

    private interface PLS {
        public int LASTSPEAKING_NEW = 7;
        public int LASTSPEAKING_PLAYING = 8;
        public int LASTSPEAKING_END = 9;
        public int LASTSPEAKING_GONE = 10;
    }

    public void startDialog(int self, int ctype, int sid, int[] ids) {
        if (sessionApi != null)
            sessionApi.startDialog(self, ctype, sid, ids);
    }

    public void stopDialog(int self, int dialog) {
        if (sessionApi != null)
            sessionApi.stopDialog(self, dialog);
    }

    public void onCurrentSessionChanged(int self, int ctype, int cid) {
        Log.i(TAG, "onCurrentSessionChanged " + ctype + ":" + cid);
        if (ctype != Constant.SESSION_TYPE_NONE) { // enter session
            currSession = new CompactID(ctype, cid);
            talkHistory.openFiles4Write(selfId, currSession.getCompactId());
        } else { // leave session
            currSession = null;
            newLastSpeaking = null;
            talkHistory.closeFiles();
        }

    }



    public void processPttAction(int theAct) {
        Toast.makeText(this,"down",Toast.LENGTH_SHORT).show();
            if (theAct == MotionEvent.ACTION_DOWN) {
                // Log.i(TAG,
                // "ptt down. uid:"+selfId+" session "+sessionType+":"+sessionId);
                talkRequest(currSession);
            } else if (theAct == MotionEvent.ACTION_UP) {
                // Log.i(TAG, "ptt stop.");
                talkRelease(currSession);
            }
    }


    @Override
    public void onSessionEstablished(int i, int i1, int i2) {
        System.out.println(i );
        System.out.println(i1);
        System.out.println(i2);
        sessionApi.getCurrentSession(i);
    }

    @Override
    public void onSessionReleased(int i, int i1, int i2) {

    }

    @Override
    public void onSessionGet(int i, int i1, int i2, int i3) {
        System.out.println(i );
        System.out.println(i1);
        System.out.println(i2);
        System.out.println(i3);
        currSession=new CompactID(i1,i2);
        dispSession=new CompactID(i1,i2);

    }

    @Override
    public void onSessionPresenceAdded(int i, int i1, List<Contact> list) {

    }

    @Override
    public void onSessionPresenceRemoved(int i, int i1, List<Integer> list) {

    }

    @Override
    public void onDialogEstablished(int i, int i1, int i2, List<Integer> list) {

    }

    @Override
    public void onDialogLeaved(int i, int i1) {

    }

    @Override
    public void onDialogPresenceAdded(int i, int i1, List<Integer> list) {

    }

    @Override
    public void onDialogPresenceRemoved(int i, int i1, List<Integer> list) {

    }

    @Override
    public void onMediaInitializedEnd(int i, int i1, int i2) {

    }

    @Override
    public void onPttButtonPressed(int i, int i1) {

    }

    @Override
    public void onTalkRequestConfirm(int i, int i1, int i2, int i3, boolean b) {

    }

    @Override
    public void onTalkRequestDeny(int i, int i1, int i2) {

    }

    @Override
    public void onTalkRequestQueued(int i, int i1, int i2) {

    }

    @Override
    public void onTalkReleaseConfirm(int i, int i1) {

    }

    @Override
    public void onTalkTransmitBroken(int i, int i1) {

    }

    @Override
    public void onStartPlaying(int i, int i1, int i2, int i3) {

    }

    @Override
    public void onPlayStopped(int i) {

    }

    @Override
    public void onSomeoneSpeaking(int i, int i1, int i2, int i3, int i4) {

    }

    @Override
    public void onThatoneSayOver(int i, int i1) {

    }

    @Override
    public void onSomeoneAttempt(int i, int i1, int i2) {

    }

    @Override
    public void onThatAttemptQuit(int i, int i1, int i2) {

    }

    @Override
    public void onNewSpeakingCatched(HistoryRecord historyRecord) {

    }

    @Override
    public void onPlayLastSpeaking(int i, int i1) {

    }

    @Override
    public void onPlayLastSpeakingEnd(int i) {

    }

    @Override
    public void onMediaSenderCutted(int i, int i1) {

    }

    @Override
    public void onMediaSenderReport(long l, int i, int i1, int i2, int i3) {

    }

    @Override
    public void onMediaReceiverReport(long l, int i, int i1, int i2, int i3) {

    }

    @Override
    public void onRecorderMeter(int i, int i1) {

    }

    @Override
    public void onPlayerMeter(int i, int i1) {

    }

    @Override
    public void onBluetoothBatteryGet(int i) {

    }

    @Override
    public void onBluetoothConnect(int i) {

    }




    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int theAct = motionEvent.getAction();

        if (sessionApi == null || currSession == null) {

            if (theAct == MotionEvent.ACTION_UP) {
                Toast.makeText(uiContext, getResources().getString(R.string.no_session), Toast.LENGTH_SHORT).show();
            }
            return false;
        }

        if (!Channel.sameCid(currSession, dispSession)) {
            if (theAct == MotionEvent.ACTION_UP)
                uiHandler.obtainMessage(MsgCode.MC_SESSIONACTIVE,
                        currSession.getType(), currSession.getId())
                        .sendToTarget();
        } else {
            processPttAction(theAct);
        }

        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.activity_streaming_imageView_audiences:
                startActivity(new Intent(StreamingActivity.this,OnlineAudiencesActivity.class));
                break;
        }

    }
}
