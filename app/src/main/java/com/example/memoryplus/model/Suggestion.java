package com.example.memoryplus.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "suggestions")
public class Suggestion {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String type;         // e.g., GI, Dune, Movie, etc.
    public String suggestion;   // e.g., "Operation Downpour", "Movie Night", etc.

    public Suggestion(String type, String suggestion) {
        this.type = type;
        this.suggestion = suggestion;
    }
}
