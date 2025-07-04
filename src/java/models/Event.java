package models;

public class Event {
    private int id;
    private String title;
    private String description;
    private String date;
    private String organizer;

    public Event(int id, String title, String description, String date, String organizer) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.organizer = organizer;
    }

    // Getters (Needed for JSON Serialization)
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getOrganizer() { return organizer; }
}
