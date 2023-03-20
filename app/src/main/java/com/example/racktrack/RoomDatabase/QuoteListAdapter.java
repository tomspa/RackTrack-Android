package com.example.racktrack.RoomDatabase;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.racktrack.Listener.OnItemClickListener;
import com.example.racktrack.R;

public class QuoteListAdapter extends ListAdapter<Quote, QuoteViewHolder> {
    private OnItemClickListener listener;

    public QuoteListAdapter(@NonNull DiffUtil.ItemCallback<Quote> diffCallBack, OnItemClickListener listener) {
        super(diffCallBack);
        this.listener = listener;
    }

    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return QuoteViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        Quote current = getItem(position);
        holder.bind(current.getQuote());

        holder.itemView.findViewById(R.id.edit_quoteItem_button).setOnClickListener(view -> {
            listener.onItemClick(current.getId());
        });
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
