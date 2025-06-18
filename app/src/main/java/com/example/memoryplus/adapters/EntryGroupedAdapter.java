package com.example.memoryplus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.memoryplus.R;
import com.example.memoryplus.model.EntryItem;
import com.example.memoryplus.model.HeaderItem;
import com.example.memoryplus.model.ListItem;

import java.util.ArrayList;
import java.util.List;

public class EntryGroupedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ListItem> items = new ArrayList<>();

    public void setItems(List<ListItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ListItem.TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_entry_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.entry_item_row, parent, false);
            return new EntryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItem item = items.get(position);
        if (holder instanceof HeaderViewHolder) {
            HeaderItem header = (HeaderItem) item;
            ((HeaderViewHolder) holder).dateText.setText("「" + header.date + "」");
            ((HeaderViewHolder) holder).pinIcon.setVisibility(header.food ? View.VISIBLE : View.GONE);
            ((HeaderViewHolder) holder).checkIcon.setVisibility(header.gym ? View.VISIBLE : View.GONE);
        } else {
            EntryItem entry = (EntryItem) item;
            String line = entry.type + " " + entry.description;
            if (entry.part != null && !entry.part.isEmpty()) {
                line += " " + entry.part;
            }
            ((EntryViewHolder) holder).entryText.setText(line);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView dateText;
        ImageView pinIcon, checkIcon;

        HeaderViewHolder(View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.text_date);
            pinIcon = itemView.findViewById(R.id.icon_pin);
            checkIcon = itemView.findViewById(R.id.icon_check);
        }
    }

    static class EntryViewHolder extends RecyclerView.ViewHolder {
        TextView entryText;

        EntryViewHolder(View itemView) {
            super(itemView);
            entryText = itemView.findViewById(R.id.text_entry_line);
        }
    }
}
