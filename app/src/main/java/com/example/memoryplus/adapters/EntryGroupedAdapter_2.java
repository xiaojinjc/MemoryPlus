package com.example.memoryplus.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memoryplus.R;
import com.example.memoryplus.entities.EntryWithType;
import com.example.memoryplus.items.EntryItem;
import com.example.memoryplus.items.HeaderItem;
import com.example.memoryplus.items.ListItem;

import java.util.ArrayList;
import java.util.List;

public class EntryGroupedAdapter_2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ListItem> items = new ArrayList<>();
    private EntryGroupedAdapter_2.OnEntryClickListener listener;

    public void setItems(List<ListItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ListItem.TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entry_header, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ListItem.TYPE_ENTRY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entry_row, parent, false);
            return new EntryViewHolder(view);
        } else {
            throw new IllegalArgumentException("Unknown view type" + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("EntryAdapter", "Binding item at position " + position);

        ListItem item = items.get(position);
        if (holder instanceof HeaderViewHolder && item instanceof HeaderItem) {
            HeaderItem header = (HeaderItem) item;
            String tempDate = "「" + header.date + "」";
            ((HeaderViewHolder) holder).dateText.setText(tempDate);
            ((HeaderViewHolder) holder).pinIcon.setVisibility(header.food ? View.VISIBLE : View.GONE);
            ((HeaderViewHolder) holder).checkIcon.setVisibility(header.gym ? View.VISIBLE : View.GONE);
        } else if (holder instanceof  EntryViewHolder && item instanceof EntryItem){
            EntryItem tempEntry = (EntryItem) item;
            EntryWithType entryWithType = tempEntry.entryWithType;
            String line = entryWithType.type.name + " " + entryWithType.entryDB.description;
            boolean hasNotes = entryWithType.entryDB.notes != null && !entryWithType.entryDB.notes.trim().isEmpty();
            if (entryWithType.entryDB.isComplete){
                line += " Complete";
            } else if (entryWithType.entryDB.part != null) {
//                TODO: make parts automatic?
                line += " " + entryWithType.entryDB.part;
            }
            if (hasNotes) {
                // ▼ or ▶
                line += tempEntry.isExpanded ? "  \u25BC" : "  \u25B6";
            }
            Log.d("EntryAdapter", "desc: " + entryWithType.entryDB.description);
            ((EntryViewHolder) holder).entryText.setText(line);
            ((EntryViewHolder) holder).notesText.setText(entryWithType.entryDB.notes);
            ((EntryViewHolder) holder).notesText.setVisibility(tempEntry.isExpanded ? View.VISIBLE : View.GONE);

            holder.itemView.setOnClickListener(v -> {
                if (hasNotes) {
                    tempEntry.isExpanded = !tempEntry.isExpanded;
                    notifyItemChanged(position);
                }
            });
            holder.itemView.setOnLongClickListener(v -> {
                if (listener != null) {
                    listener.onEntryLongClick(entryWithType);
                }
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView dateText;
        ImageView pinIcon, checkIcon;

        HeaderViewHolder(View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.text_date);
            pinIcon = itemView.findViewById(R.id.icon_pin);
            checkIcon = itemView.findViewById(R.id.icon_check);
        }
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder {
        TextView entryText;
        TextView notesText;

        EntryViewHolder(View itemView) {
            super(itemView);
            entryText = itemView.findViewById(R.id.text_entry_line);
            notesText = itemView.findViewById(R.id.notes_entry_line);
        }
    }

    public interface OnEntryClickListener {
        void onEntryLongClick(EntryWithType entryDB);
    }

    public void setOnEntryClickListener (EntryGroupedAdapter_2.OnEntryClickListener listener) {
        this.listener = listener;
    }
}
