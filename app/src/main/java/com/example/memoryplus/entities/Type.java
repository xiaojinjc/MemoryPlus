package com.example.memoryplus.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "types",
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "id",
                childColumns = "categoryId",
                onDelete = ForeignKey.SET_NULL
        ),
        indices = {@Index("categoryId")}
)
public class Type {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int categoryId; // Foreign key to Category

    @NonNull
    public String name; // e.g., Movie, Anime, GI

    public Type(int categoryId,@NonNull String name){
        this.categoryId = categoryId;
        this.name = name;
    }
}

