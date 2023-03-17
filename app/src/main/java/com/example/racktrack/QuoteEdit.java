package com.example.racktrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class QuoteEdit extends AppCompatActivity {

    private TextInputEditText textView;
    private View saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_edit);
        this.saveButton = findViewById(R.id.saveQuoteButton);
        this.textView = findViewById(R.id.quoteEditField);
        this.saveButton.setOnClickListener(view -> {
            // save to Room database
            System.out.println(this.textView.getText());
            this.textView.setText("");
        });
        this.textView.setText(""); //Load database value if it exists, else ""
    }
}