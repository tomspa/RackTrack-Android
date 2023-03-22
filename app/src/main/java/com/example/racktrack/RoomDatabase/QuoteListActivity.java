package com.example.racktrack.RoomDatabase;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.racktrack.Listener.OnItemClickListener;
import com.example.racktrack.NewQuoteActivity;
import com.example.racktrack.Quote.QuoteEditActivity;
import com.example.racktrack.R;

public class QuoteListActivity extends AppCompatActivity implements OnItemClickListener {
    private QuoteViewModel quoteViewModel;
    public static final int NEW_QUOTE_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final QuoteListAdapter adapter = new QuoteListAdapter(new QuoteListAdapter.QuoteDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.quoteViewModel = new ViewModelProvider(this).get(QuoteViewModel.class);

        this.quoteViewModel.getAllQuotes().observe(this, quotes -> {
            adapter.submitList(quotes);
        });

        ActivityResultLauncher<Intent> activityResultLauncher = this.getActivityResultLauncher();

        Button addButton = findViewById(R.id.addQuoteButton);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(QuoteListActivity.this, NewQuoteActivity.class);
            activityResultLauncher.launch(intent);
        });
    }

    //Replacement for deprecated 'startActivityForResult' function
    private ActivityResultLauncher<Intent> getActivityResultLauncher() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Quote word = new Quote(result.getData().getStringExtra(NewQuoteActivity.EXTRA_REPLY));
                        quoteViewModel.insert(word);
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.not_saved_message,Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    @Override
    public void onItemClick(int id) {
        Intent intent = new Intent(this, QuoteEditActivity.class);
        intent.putExtra("quote_id", id);
        startActivity(intent);
    }
}