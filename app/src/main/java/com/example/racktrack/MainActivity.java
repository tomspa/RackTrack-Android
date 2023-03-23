package com.example.racktrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.racktrack.Exercise.ExerciseListActivity;
import com.example.racktrack.Exercise.ExerciseListAdapter;
import com.example.racktrack.Quote.QuoteListActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPrefListener;


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
}