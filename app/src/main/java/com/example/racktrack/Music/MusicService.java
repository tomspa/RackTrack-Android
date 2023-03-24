package com.example.racktrack.Music;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.racktrack.R;

import java.io.IOException;


public class MusicService extends Service {

    private final IBinder iBinder = new LocalBinder();
    private MediaPlayer audioPlayer = null;
    private boolean isPlayerReady = false;
    private boolean audioIsPlaying = false;

    public static final String AUDIO_BROADCAST = "AUDIO_BROADCAST";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAudio();
    }

    public void initialiseAudioPlayer() {
        audioPlayer = new MediaPlayer();
        audioPlayer.setOnCompletionListener(mediaPlayer -> {
            stopAudio();
            stopSelf();
        });

        try {
            AssetFileDescriptor afd = this.getResources().openRawResourceFd(R.raw.gym_banger);
            if (afd == null) return;
            audioPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            audioPlayer.prepareAsync();
            audioPlayer.setOnPreparedListener(mediaPlayer -> {
                isPlayerReady = true;
                sendBroadcast();
            });
            audioPlayer.setOnErrorListener((mediaPlayer, i, i1) -> {
                stopSelf();
                return false;
            });
        } catch (IOException e) {
            stopSelf();
        }
    }

    public void playAudio() {
        if (audioPlayer != null && isPlayerReady && !audioIsPlaying) {
            audioIsPlaying = true;
            sendNotification("Music playing", "Music started playing");
            new Thread(() -> audioPlayer.start()).start();
        }
    }

    public void pauseAudio() {
        if (audioIsPlaying) {
            new Thread(() -> audioPlayer.pause()).start();
            sendNotification("Music paused", "Music paused");
            audioIsPlaying = false;
        }
    }

    public void stopAudio() {
        if (audioPlayer == null) return;
        if (audioIsPlaying) {
            new Thread(() -> audioPlayer.stop()).start();
            sendNotification("Stopped Music", "Music stopped playing");
            audioIsPlaying = false;
            audioPlayer.release();
            audioPlayer = null;
            isPlayerReady = false;
            initialiseAudioPlayer();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        initialiseAudioPlayer();
        return iBinder;
    }

    private void sendBroadcast() {
        Intent broadcastIntent = new Intent();

        broadcastIntent.putExtra("status", isPlayerReady);
        broadcastIntent.setAction(AUDIO_BROADCAST);

        sendBroadcast(broadcastIntent);
    }

    private void sendNotification(String title, String contentText){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "audio_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(false);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        NotificationChannel channel = new NotificationChannel("audio_channel", "Audio", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    public class LocalBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }

    }
}
