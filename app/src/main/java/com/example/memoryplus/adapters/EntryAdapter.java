package com.example.memoryplus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memoryplus.R;
import com.example.memoryplus.model.Entry;

import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

    private final List<Entry> entryList;

    public EntryAdapter(List<Entry> entryList) {
        this.entryList = entryList;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_item, parent, false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        Entry entry = entryList.get(position);
        holder.dateText.setText("「"+ entry.getDate() + "」");
        holder.categoryText.setText(entry.getCategory());
        holder.typeText.setText(entry.getType() + " " + entry.getDescription());
        holder.notesText.setText(entry.getNotes());
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    static class EntryViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, categoryText, typeText, descriptionText, notesText;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.entryDate);
            categoryText = itemView.findViewById(R.id.entryCategory);
            typeText = itemView.findViewById(R.id.entryTypeDescription);
//            descriptionText = itemView.findViewById(R.id.entryDescription);
            notesText = itemView.findViewById(R.id.entryNotes);
        }
    }
}
