package com.example.memoryplus.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "subcategories",
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "id",
                childColumns = "categoryId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("categoryId")}
)
public class Subcategory {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int categoryId; // Foreign key to Category

    @NonNull
    public String name; // e.g., Movie, Anime, GI
}

