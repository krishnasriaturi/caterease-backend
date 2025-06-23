/*package com.catering.mongo.repository;

import com.catering.mongo.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {
    Optional<Event> findByEventId(String eventId);
    Optional<Event> findByEventIdAndEventPassword(String eventId, String eventPassword);
    Optional<Event> findByEventIdAndEventPasswordAndName(String eventId, String eventPassword, String name);

}*/
package com.catering.mongo.repository;

import com.catering.mongo.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    Optional<Event> findByEventId(String eventId);

    Optional<Event> findByEventIdAndEventPassword(String eventId, String eventPassword);

    Optional<Event> findByEventIdAndEventPasswordAndName(String eventId, String eventPassword, String name);

    boolean existsByEventDate(LocalDate eventDate);

    boolean existsByEventId(String eventId);
}


