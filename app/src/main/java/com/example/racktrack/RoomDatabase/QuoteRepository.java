package com.example.racktrack.RoomDatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class QuoteRepository {

    private final QuoteDAO quoteDAO;
    private final LiveData<List<Quote>> allQuotes;


    public QuoteRepository(Application application) {
        QuoteRoomDatabase db = QuoteRoomDatabase.getDatabase(application);
        quoteDAO = db.quoteDAO();
        allQuotes = quoteDAO.getAllQuotes();
    }

    public LiveData<List<Quote>> getAllQuotes() {
        return allQuotes;
    }

    public void insert(Quote quote) {
        QuoteRoomDatabase.databaseWriteExecutor.execute(() -> quoteDAO.insert(quote));
    }
}
