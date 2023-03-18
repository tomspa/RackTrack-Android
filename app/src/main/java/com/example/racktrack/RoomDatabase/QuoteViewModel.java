package com.example.racktrack.RoomDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class QuoteViewModel extends AndroidViewModel {

    private QuoteRepository quoteRepository;
    private LiveData<List<Quote>> allQuotes;

    public QuoteViewModel(@NonNull Application application) {
        super(application);

        quoteRepository = new QuoteRepository(application);
        allQuotes = quoteRepository.getAllQuotes();
    }

    LiveData<List<Quote>> getAllQuotes() {
        return this.allQuotes;
    }

    public void insert(Quote quote) {
        quoteRepository.insert(quote);
    }
}
