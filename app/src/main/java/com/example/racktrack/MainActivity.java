package com.example.racktrack;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.racktrack.Exercise.ExerciseListActivity;
import com.example.racktrack.Preferences.SettingsActivity;
import com.example.racktrack.Quote.QuoteListActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView gymImageview;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View quoteButton = findViewById(R.id.quotesListButton);
        quoteButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, QuoteListActivity.class);
            startActivity(intent);
        });

        View exerciseButton = findViewById(R.id.exerciseButton);
        exerciseButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ExerciseListActivity.class);
            startActivity(intent);
        });

        Button cameraButton = findViewById(R.id.camera_button);
        gymImageview = findViewById(R.id.gym_picture_imageview);


        activityResultLauncher = this.getActivityResultLauncher();
        cameraButton.setOnClickListener(view -> requestPermission());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings_button) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            intent.setAction(Intent.ACTION_SEND);
            startActivity(intent);
        }
        return true;
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            Toast.makeText(this, R.string.allow_camera_access_string, Toast.LENGTH_LONG).show();
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultLauncher.launch(cameraIntent);
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
        registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                openCamera();
            } else {
                Toast.makeText(this, R.string.allow_camera_access_string, Toast.LENGTH_LONG).show();
            }
        });

    private ActivityResultLauncher<Intent> getActivityResultLauncher() {
        return registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    assert data != null;
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    gymImageview.setImageBitmap(photo);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.not_saved_message,Toast.LENGTH_LONG).show();
                }
            }
        );
    }
}