package com.example.memoryplus.item;

public class EntryItem implements ListItem {
    public String type;
    public String description;
    public String part;
    public String notes;

    public EntryItem(String type, String description, String part, String notes) {
        this.type = type;
        this.description = description;
        this.part = part;
        this.notes = notes;
    }

    @Override
    public int getType() {
        return TYPE_ENTRY;
    }
}
