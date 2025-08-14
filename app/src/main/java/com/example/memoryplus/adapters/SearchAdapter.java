package com.example.memoryplus.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memoryplus.R;
import com.example.memoryplus.entities.EntryWithType;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<EntryWithType> entries;

    public SearchAdapter(List<EntryWithType> entries) {
        this.entries = entries;
    }

    public void updateData(List<EntryWithType> newEntries) {
        this.entries = newEntries;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EntryWithType entry = entries.get(position);
        String line = entry.type.name + " " + entry.entryDB.description;
        boolean hasNotes = entry.entryDB.notes != null && !entry.entryDB.notes.trim().isEmpty();
        if (entry.entryDB.isComplete){
            line += " Complete";
        } else if (entry.entryDB.part != null) {
//                TODO: make parts automatic?
            line += " " + entry.entryDB.part;
        }
//        if (hasNotes) {
//            // ▼ or ▶
//            line += entry.isExpanded ? "  \u25BC" : "  \u25B6";
//        }
        Log.d("EntryAdapter", "desc: " + entry.entryDB.description);
        holder.searchLine.setText(line);
        holder.searchDate.setText(entry.entryDB.date);
//        holder.otesText.setText(entryWithType.entryDB.notes);
//        holder.notesText.setVisibility(tempEntry.isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView searchLine;
        TextView searchDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            searchLine = itemView.findViewById(R.id.search_item);
            searchDate = itemView.findViewById(R.id.search_date);
        }
    }
}
