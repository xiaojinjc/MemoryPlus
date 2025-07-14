package com.example.memoryplus.items;

import com.example.memoryplus.entities.EntryDB;
import com.example.memoryplus.entities.EntryWithType;

public class EntryItem implements ListItem {
    public final EntryWithType entryWithType;

    public EntryItem(EntryWithType entry) {
        this.entryWithType = entry;
    }

    @Override
    public int getType() {
        return TYPE_ENTRY;
    }
}
