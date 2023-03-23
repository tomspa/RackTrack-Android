package com.example.racktrack.Quote;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.racktrack.RoomDatabase.Quote;
import com.example.racktrack.RoomDatabase.QuoteRepository;

import java.util.List;

public class QuoteViewModel extends AndroidViewModel {

    private final QuoteRepository quoteRepository;
    private final LiveData<List<Quote>> allQuotes;

    public QuoteViewModel(@NonNull Application application) {
        super(application);

        quoteRepository = new QuoteRepository(application);
        allQuotes = quoteRepository.getAllQuotes();
    }

    public LiveData<List<Quote>> getAllQuotes() {
        return this.allQuotes;
    }

    public void insert(Quote quote) {
        quoteRepository.insert(quote);
    }
}
