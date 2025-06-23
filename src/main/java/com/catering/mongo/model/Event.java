/*package com.catering.mongo.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "events")
public class Event {
    @Id
    private String id;
    private String name;
    private String eventId;          // unique identifier for guests
    private String eventPassword;    // used by guests to "login"

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getEventPassword() { return this.eventPassword; }
    public void setEventPassword(String eventPassword) { this.eventPassword = eventPassword; }
}*/
package com.catering.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "events")
public class Event {
    @Id
    private String id;

    private String name; // organizer name

    @Indexed(unique = true)
    private String eventId;

    private String eventPassword;

    private String eventName;

    private LocalDate eventDate;

    private LocalTime eventTime;

    private int numberOfPlates;

    // Getters and Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getEventPassword() { return eventPassword; }
    public void setEventPassword(String eventPassword) { this.eventPassword = eventPassword; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public LocalTime getEventTime() { return eventTime; }
    public void setEventTime(LocalTime eventTime) { this.eventTime = eventTime; }

    public int getNumberOfPlates() { return numberOfPlates; }
    public void setNumberOfPlates(int numberOfPlates) { this.numberOfPlates = numberOfPlates; }
}

