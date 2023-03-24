package com.example.racktrack.Music;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.example.racktrack.R;

public class MusicActivity extends AppCompatActivity {

    private Button playAudioBtn;
    private Button pauseAudioBtn;
    private Button stopAudioBtn;

    private MusicService musicService;
    private BroadcastReceiver audioBroadcastReceiver;
    boolean isServiceBound = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isServiceBound) {
            Intent audioPlayerIntent = new Intent(this, MusicService.class);
            this.bindService(audioPlayerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
        setContentView(R.layout.activity_music);

        this.playAudioBtn = this.findViewById(R.id.start_music_button);
        this.pauseAudioBtn = this.findViewById(R.id.pause_music_button);
        this.stopAudioBtn = this.findViewById(R.id.stop_music_button);

        setupAudioButtons();
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicService.AUDIO_BROADCAST);
        this.registerReceiver(audioBroadcastReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.unregisterReceiver(audioBroadcastReceiver);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.musicService.initialiseAudioPlayer();
    }

    private void setupAudioButtons() {
        playAudioBtn.setOnClickListener(audioPlayListener);
        pauseAudioBtn.setOnClickListener(audioPauseListener);
        stopAudioBtn.setOnClickListener(audioStopListener);

        audioBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean status = intent.getBooleanExtra("status", false);
                playAudioBtn.setEnabled(status);
                pauseAudioBtn.setEnabled(status);
                stopAudioBtn.setEnabled(status);

            }
        };
    }

    private final View.OnClickListener audioPlayListener = view -> {
        this.startService(new Intent(this, MusicService.class));
        musicService.playAudio();
    };

    private View.OnClickListener audioPauseListener = view -> {
        musicService.pauseAudio();
    };

    private View.OnClickListener audioStopListener = view -> {
        musicService.stopAudio();
    };


    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) iBinder;
            musicService = binder.getService();
            isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceBound = false;
        }
    };
}