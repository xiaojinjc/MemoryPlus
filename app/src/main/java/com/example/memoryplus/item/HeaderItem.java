package com.example.memoryplus.item;

public class HeaderItem implements ListItem {
    public String date;
    public boolean food;
    public boolean gym;

    public HeaderItem(String date, boolean food, boolean gym) {
        this.date = date;
        this.food = food;
        this.gym = gym;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }
}

