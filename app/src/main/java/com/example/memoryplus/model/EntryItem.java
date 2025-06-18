package com.example.memoryplus.model;

public class EntryItem implements ListItem {
    public String type;
    public String description;
    public String part;

    public EntryItem(String type, String description, String part) {
        this.type = type;
        this.description = description;
        this.part = part;
    }

    @Override
    public int getType() {
        return TYPE_ENTRY;
    }
}
