package com.example.memoryplus.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class TypeWithCategory {
    @Embedded
    public Type type;

    @Relation(
            parentColumn = "categoryId",
            entityColumn = "id"
    )
    public Category category;
}
