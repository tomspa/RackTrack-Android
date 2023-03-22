package com.example.racktrack.Quote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racktrack.R;

public class QuoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView quoteTextView;
    public QuoteViewHolder(@NonNull View itemView) {
        super(itemView);
        quoteTextView = itemView.findViewById(R.id.quoteTextView);
    }

    public void bind(String text) {
        quoteTextView.setText(text);
    }

    static QuoteViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_list_item, parent, false);
        return new QuoteViewHolder(view);
    }
}
