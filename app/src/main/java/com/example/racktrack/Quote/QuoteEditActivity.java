package com.example.racktrack.Quote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import com.example.racktrack.R;
import com.example.racktrack.RoomDatabase.Quote;
import com.example.racktrack.RoomDatabase.QuoteDAO;
import com.example.racktrack.RoomDatabase.QuoteRoomDatabase;
import com.google.android.material.textfield.TextInputEditText;

public class QuoteEditActivity extends AppCompatActivity {

    private TextInputEditText textView;

    private QuoteDAO quoteDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_edit);

        this.quoteDAO = QuoteRoomDatabase.getDatabase(this).quoteDAO();
        View saveButton = findViewById(R.id.saveQuoteButton);
        this.textView = findViewById(R.id.quoteEditField);

        // get the id from the intent
        Intent intent = getIntent();
        int quoteId = intent.getIntExtra("quote_id", 1);
        LiveData<Quote> liveQuote = this.quoteDAO.getQuoteById(quoteId);

        liveQuote.observe(this, quote -> {
            if (quote != null) {
                this.textView.setText(quote.getQuote());
            } else {
                this.finish();
            }
        });

        saveButton.setOnClickListener(view -> {
            Editable text = this.textView.getText();

            if (text != null) {
                if (text.toString().isEmpty()) {
                    quoteDAO.deleteQuoteById(quoteId);
                } else {
                    quoteDAO.updateQuoteById(quoteId, text.toString());
                }
            }
            this.finish();
        });
    }
}