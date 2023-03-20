package com.example.racktrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.racktrack.RoomDatabase.QuoteListActivity;

public class MainActivity extends AppCompatActivity {

    private View quoteButton;

    private static final String KEY_QUOTE = "favorite_quote";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPrefListener;

    private String favoriteQuote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.quoteButton = findViewById(R.id.quotesListButton);

        this.quoteButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, QuoteListActivity.class);
            startActivity(intent);
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPrefListener = (sharedPreferences, key) -> favoriteQuote = sharedPreferences.getString(KEY_QUOTE, "DEFAULT");
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPrefListener);

        favoriteQuote = sharedPreferences.getString(KEY_QUOTE, "DEFAULT");
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