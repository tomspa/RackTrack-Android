package com.example.racktrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class NewQuoteActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.quotelistsql.REPLY";
    private EditText editQuoteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quote);

        this.editQuoteView = findViewById(R.id.edit_quote);

        Button button = findViewById(R.id.save_quote_button);
        button.setOnClickListener(view -> {
            Intent intent = new Intent();
            if (TextUtils.isEmpty(editQuoteView.getText())) {
                setResult(RESULT_CANCELED, intent);
            } else {
                String quote = editQuoteView.getText().toString();
                intent.putExtra(EXTRA_REPLY, quote);
                setResult(RESULT_OK, intent);
            }
            finish();
        });
    }
}