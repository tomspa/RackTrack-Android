package com.example.racktrack.Quote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.racktrack.R;
import com.example.racktrack.RoomDatabase.Quote;
import com.example.racktrack.RoomDatabase.QuoteDAO;
import com.example.racktrack.RoomDatabase.QuoteRoomDatabase;
import com.google.android.material.textfield.TextInputEditText;

public class QuoteEditActivity extends AppCompatActivity {

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

        // get the id from the intent
        Intent intent = getIntent();
        int quoteId = intent.getIntExtra("quote_id", -1);
        QuoteDAO quoteDAO = QuoteRoomDatabase.getDatabase(this).quoteDAO();
        LiveData<Quote> quote = quoteDAO.getQuoteById(quoteId);
        this.textView.setText(quote.getValue().getQuote()); //Load database value if it exists, else ""
    }
}