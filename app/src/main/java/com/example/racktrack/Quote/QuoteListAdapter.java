package com.example.racktrack.Quote;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.racktrack.R;
import com.example.racktrack.RoomDatabase.Quote;

public class QuoteListAdapter extends ListAdapter<Quote, QuoteViewHolder> {
    private final OnItemClickListener listener;

    public QuoteListAdapter(@NonNull DiffUtil.ItemCallback<Quote> diffCallBack, OnItemClickListener listener) {
        super(diffCallBack);
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return QuoteViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        Quote current = getItem(position);
        holder.bind(current.getQuote());

        holder.itemView.findViewById(R.id.edit_quoteItem_button).setOnClickListener(view -> listener.onItemClick(current.getId()));
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
