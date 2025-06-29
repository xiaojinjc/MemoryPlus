package com.example.memoryplus.items;

public interface ListItem {
    int TYPE_HEADER = 0;
    int TYPE_ENTRY = 1;

    int getType();
}

