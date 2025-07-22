package com.example.memoryplus.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.memoryplus.R;
import com.example.memoryplus.items.HeaderItem;
import com.example.memoryplus.items.ListItem;

import java.util.ArrayList;
import java.util.List;

public class EntryGroupedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    Hold the items of both header or entry type
    private List<ListItem> items = new ArrayList<>();
    private EntryClickListener entryClickListener;

//    Called from outside to set new data in the list, and call warns the rv that data changed and need to be redrawn
//    TODO: fix this notifyDataSetChanged
//    This is expensive last resort, change can either be data or structure, this assumes both so it build everything again
    public void setItems(List<ListItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

//    Tells the adapter which type of view (header or entry) is needed at that position
    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

//    Gets the count of the list of items, tell rv how many rows to display
    @Override
    public int getItemCount() {
        return items.size();
    }

//    Creates the layout for the correct type of view
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ListItem.TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_entry_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_entry_row, parent, false);
            return new EntryViewHolder(view);
        }
    }

//    View holders: Classes that holds all the views in one for easier writing
//    so that you don't have to write findViewById all the time
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

//    TODO: Finish adding click functionality
    static class EntryViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        TextView entryText;

        EntryViewHolder(View itemView) {
            super(itemView);
            entryText = itemView.findViewById(R.id.text_entry_line);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            Log.d("ENTRY ADAPTER", "click " + getAdapterPosition());
        }
    }


//    Important: Fills the data, checks type of view holders and sets the data in the respective places
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItem item = items.get(position);
        if (holder instanceof HeaderViewHolder) {
            HeaderItem header = (HeaderItem) item;
            String tempDate = "「" + header.date + "」";                 // small error if not using this and just passing
            ((HeaderViewHolder) holder).dateText.setText(tempDate);     // here
            ((HeaderViewHolder) holder).pinIcon.setVisibility(header.food ? View.VISIBLE : View.GONE);
            ((HeaderViewHolder) holder).checkIcon.setVisibility(header.gym ? View.VISIBLE : View.GONE);
        }
//        else {
//            EntryItem entry = (EntryItem) item;
//            String line = entry.type + " " + entry.description;
//            if (entry.part != null && !entry.part.isEmpty()) {
//                    line += " " + entry.part;
//            }
//            ((EntryViewHolder) holder).entryText.setText(line);
//        }
    }

//    Interface to handle clicks, delegating responsibility to parent
    public interface EntryClickListener {
        void onItemClick(View view, int position);
    }

//    Allows for callbacks(passing functions) from activity
    void setEntryClickListener(EntryClickListener entryClickListener){
        this.entryClickListener = entryClickListener;
    }

}
