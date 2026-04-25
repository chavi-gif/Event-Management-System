package com.example.event.Repository;

import com.example.event.Model.Event;
import com.example.event.Model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByEventDate(LocalDate date);

    List<Event> findByResource(Resource resource);

    boolean existsByResourceAndEventDate(Resource resource, LocalDate date);

    boolean existsByResourceAndEventDateAndIdNot(Resource resource, LocalDate date, Long id);

    List<Event> findByOrganizerNameContainingIgnoreCase(String organizerName);

    List<Event> findByEventDateGreaterThanEqualOrderByEventDateAsc(LocalDate date);

    long countByStatus(Event.EventStatus status);

    List<Event> findByStatus(Event.EventStatus status);

    List<Event> findTop5ByOrderByEventDateDesc();
}