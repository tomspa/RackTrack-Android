package com.example.racktrack.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuoteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Quote quote);

    @Query("DELETE FROM quote_table")
    void deleteAll();

    @Query("SELECT * FROM quote_table ORDER BY id ASC")
    LiveData<List<Quote>> getAllQuotes();

    @Query("SELECT * FROM quote_table WHERE id=:id")
    LiveData<Quote> getQuoteById(int id);

    @Query("UPDATE quote_table SET quote=:quote WHERE id=:id")
    void updateQuoteById(int id, String quote);

    @Query("DELETE FROM quote_table WHERE id=:id")
    void deleteQuoteById(int id);
}
