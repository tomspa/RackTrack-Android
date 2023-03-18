package com.example.racktrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.racktrack.RoomDatabase.QuoteList;

public class MainActivity extends AppCompatActivity {

    private View quoteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.quoteButton = findViewById(R.id.quotesListButton);
        this.quoteButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, QuoteList.class);
            startActivity(intent);
        });
    }
}