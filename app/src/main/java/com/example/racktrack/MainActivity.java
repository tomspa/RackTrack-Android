package com.example.racktrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private View quoteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.quoteButton = findViewById(R.id.quoteButton);
        this.quoteButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, QuoteEdit.class);
            startActivity(intent);
        });
    }
}