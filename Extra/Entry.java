// TODO: Modify entry so it is {date, [{event}, {event}]}, and then grouped by yaer

package com.example.memoryplus.model;

public class Entry {
    private String date;
    private String category;
    private String type;
    private String description;
    private String notes;

    public Entry(String date, String category, String type, String description, String notes){
        this.date = date;
        this.category = category;
        this.type = type;
        this.description = description;
        this.notes = notes;
    }

    // Getters
    public String getDate() { return date; }
    public String getCategory() { return category; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public String getNotes() { return notes; }

    // Setters
    public void setDate(String date) { this.date = date; }
    public void setCategory(String category) { this.category = category; }
    public void setType(String type) { this.type = type; }
    public void setDescription(String description) { this.description = description; }
    public void setNotes(String notes) { this.notes = notes; }

}
