/*
package com.catering.mongo.controller;

import com.catering.mongo.model.Event;
import com.catering.mongo.model.GuestPreference;
import com.catering.mongo.repository.EventRepository;
import com.catering.mongo.repository.GuestPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/organizer")
@CrossOrigin(origins = "*")
public class EventController {
    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private GuestPreferenceRepository guestRepo;

    // Create an event (organizer only)
    @PostMapping("/create")
    public Event createEvent(@RequestBody Event event) {
        return eventRepo.save(event);
    }

    // Validate event for guest login
    @PostMapping("/validate")
    public Optional<Event> validateEvent(@RequestBody Event event) {
        return eventRepo.findByEventIdAndEventPassword(event.getEventId(), event.getEventPassword());
    }

    // Fetch event details (optional)
    @GetMapping("/{eventId}")
    public Optional<Event> getEvent(@PathVariable String eventId) {
        return eventRepo.findByEventId(eventId);
    }

    @PostMapping("/submissions")
    public ResponseEntity<?> getSubmissions(@RequestBody Event request) {
        Optional<Event> event = eventRepo.findByEventIdAndEventPasswordAndName(
                request.getEventId(), request.getEventPassword(), request.getName()
        );

        if (event.isPresent()) {

            List<GuestPreference> guests = guestRepo.findByEventId(request.getEventId());
            return ResponseEntity.ok(guests);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
*/
package com.catering.mongo.controller;

import com.catering.mongo.model.Event;
import com.catering.mongo.model.GuestPreference;
import com.catering.mongo.repository.EventRepository;
import com.catering.mongo.repository.GuestPreferenceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/organizer")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private GuestPreferenceRepository guestRepo;

    // ✅ Create event with validations
    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        // 1. Validate date conflict
        if (eventRepo.existsByEventDate(event.getEventDate())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Date already occupied");
        }

        // 2. Validate time
        LocalTime time = event.getEventTime();
        if (time.isBefore(LocalTime.of(7, 0)) || time.isAfter(LocalTime.of(21, 0))) {
            return ResponseEntity.badRequest().body("Service not available in the requested time");
        }

        // 3. Validate plates
        int plates = event.getNumberOfPlates();
        if (plates < 10) {
            return ResponseEntity.badRequest().body("Minimum number of plates should be 10");
        }
        if (plates > 500) {
            return ResponseEntity.badRequest().body("Maximum limit is 500 plates");
        }

        // 4. Unique event ID
        if (eventRepo.existsByEventId(event.getEventId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Event ID already in use");
        }
        Event savedEvent = eventRepo.save(event);
        return ResponseEntity.ok(savedEvent);
    }

    // ✅ Guest login validation
    @PostMapping("/validate")
    public Optional<Event> validateEvent(@RequestBody Event event) {
        return eventRepo.findByEventIdAndEventPassword(event.getEventId(), event.getEventPassword());
    }

    // ✅ Get event details by event ID (optional)
    @GetMapping("/{eventId}")
    public Optional<Event> getEvent(@PathVariable String eventId) {
        return eventRepo.findByEventId(eventId);
    }

    // ✅ Organizer viewing guest submissions
    @PostMapping("/submissions")
    public ResponseEntity<?> getSubmissions(@RequestBody Event request) {
        Optional<Event> event = eventRepo.findByEventIdAndEventPasswordAndName(
                request.getEventId(), request.getEventPassword(), request.getName()
        );

        if (event.isPresent()) {
            List<GuestPreference> guests = guestRepo.findByEventId(request.getEventId());
            return ResponseEntity.ok(guests);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/dates")
    public List<String> getAllEventDates() {
        return eventRepo.findAll().stream()
                .map(Event::getEventDate)
                .filter(date -> date != null)
                .map(LocalDate::toString)  // ✅ Convert LocalDate to String
                .toList();
    }

}

