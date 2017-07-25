package com.hdu.kefan.campusradio.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.algebra.sdk.API;
import com.algebra.sdk.OnMediaListener;
import com.algebra.sdk.OnSessionListener;
import com.algebra.sdk.SessionApi;
import com.algebra.sdk.entity.CompactID;
import com.algebra.sdk.entity.Contact;
import com.algebra.sdk.entity.HistoryRecord;
import com.hdu.kefan.campusradio.R;

import java.util.List;

public class StreamingActivity extends AppCompatActivity implements OnSessionListener,OnMediaListener{


    private SessionApi sessionApi=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_streaming);


        sessionApi= API.getSessionApi();
        if(sessionApi!=null)
        {
            sessionApi.setOnSessionListener(this);

            sessionApi.sessionCall(4850,3,10);

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
}
