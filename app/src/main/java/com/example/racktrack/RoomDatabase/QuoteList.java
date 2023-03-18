package com.example.racktrack.RoomDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.racktrack.NewQuoteActivity;
import com.example.racktrack.R;

public class QuoteList extends AppCompatActivity {
    private QuoteViewModel quoteViewModel;
    public static final int NEW_QUOTE_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final QuoteListAdapter adapter = new QuoteListAdapter(new QuoteListAdapter.QuoteDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.quoteViewModel = new ViewModelProvider(this).get(QuoteViewModel.class);

        this.quoteViewModel.getAllQuotes().observe(this, quotes -> {
            adapter.submitList(quotes);
        });

        Button addButton = findViewById(R.id.addQuoteButton);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(QuoteList.this, NewQuoteActivity.class);
            startActivityForResult(intent, NEW_QUOTE_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_QUOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Quote word = new Quote(data.getStringExtra(NewQuoteActivity.EXTRA_REPLY));
            quoteViewModel.insert(word);
        } else {
            Toast.makeText(getApplicationContext(),"Not saved",Toast.LENGTH_LONG).show();
        }
    }
}