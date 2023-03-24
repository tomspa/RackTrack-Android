package com.example.racktrack.Music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.racktrack.R;

public class MusicService extends Service {

    private MediaPlayer player;
    private boolean isRunning;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isRunning) {
            player = MediaPlayer.create(this, R.raw.gym_banger);
            player.setLooping(true);
            player.start();
            isRunning = true;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        player.stop();
        isRunning = false;
    }
}