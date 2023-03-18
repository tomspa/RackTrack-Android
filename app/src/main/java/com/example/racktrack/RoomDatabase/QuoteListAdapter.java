package com.example.racktrack.RoomDatabase;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class QuoteListAdapter extends ListAdapter<Quote, QuoteViewHolder> {

    public QuoteListAdapter(@NonNull DiffUtil.ItemCallback<Quote> diffCallBack) {
        super(diffCallBack);
    }

    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return QuoteViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        Quote current = getItem(position);
        holder.bind(current.getQuote());
    }

    static class QuoteDiff extends DiffUtil.ItemCallback<Quote> {

        @Override
        public boolean areItemsTheSame(@NonNull Quote oldItem, @NonNull Quote newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Quote oldItem, @NonNull Quote newItem) {
            return oldItem.getQuote().equals(newItem.getQuote());
        }
    }
}
