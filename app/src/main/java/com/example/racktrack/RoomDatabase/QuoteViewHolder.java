package com.example.racktrack.RoomDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racktrack.R;

public class QuoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView quoteItemView;
    public QuoteViewHolder(@NonNull View itemView) {
        super(itemView);
        quoteItemView = itemView.findViewById(R.id.quoteTextView);
    }

    public void bind(String text) {
        quoteItemView.setText(text);
    }
    static QuoteViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_list_item, parent, false);
        return new QuoteViewHolder(view);
    }
}
