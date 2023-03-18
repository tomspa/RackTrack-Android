package com.example.racktrack.RoomDatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class QuoteRepository {

    private QuoteDAO quoteDAO;
    private LiveData<List<Quote>> allQuotes;


    QuoteRepository(Application application) {
        QuoteRoomDatabase db = QuoteRoomDatabase.getDatabase(application);
        quoteDAO = db.quoteDAO();
        allQuotes = quoteDAO.getAllQuotes();
    }

    LiveData<List<Quote>> getAllQuotes() {
        return allQuotes;
    }

    void insert(Quote quote) {
        QuoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            quoteDAO.insert(quote);
        });
    }
}
