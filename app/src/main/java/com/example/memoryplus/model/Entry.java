package com.example.memoryplus.model;

public class Entry {
    private String date;
    private String category;
    private String type;
    private String description;

    public Entry(String date, String category, String type, String description){
        this.date = date;
        this.category = category;
        this.type = type;
        this.description = description;
    }

    // Getters
    public String getDate() { return date; }
    public String getCategory() { return category; }
    public String getType() { return type; }
    public String getDescription() { return description; }

    // Setters
    public void setDate(String date) { this.date = date; }
    public void setCategory(String category) { this.category = category; }
    public void setType(String type) { this.type = type; }
    public void setDescription(String description) { this.description = description; }

}
