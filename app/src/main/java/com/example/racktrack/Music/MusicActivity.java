package com.example.racktrack.Music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.racktrack.R;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {

    private View playImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);


        playImage = findViewById(R.id.play_music_image);
        View stopImage = findViewById(R.id.stop_music_image);

        playImage.setOnClickListener(this);
        stopImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == playImage) {
            startService(new Intent(this, MusicService.class));
        } else {
            stopService(new Intent(this, MusicService.class));
        }
    }
}