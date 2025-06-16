package com.example.memoryplus.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "suggestions",
        foreignKeys = @ForeignKey(
                entity = Subcategory.class, // or rename this class to Type
                parentColumns = "id",
                childColumns = "typeId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("typeId")}
)
public class Suggestion {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int typeId; // Foreign key to Type (Subcategory) table

    @NonNull
    public String suggestion;   // e.g., "Operation Downpour", "Movie Night"

    public Suggestion(int typeId, @NonNull String suggestion) {
        this.typeId = typeId;
        this.suggestion = suggestion;
    }
}

