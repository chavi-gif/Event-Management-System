package com.example.event.Service;

import com.example.event.Exception.DoubleBookingException;
import com.example.event.Exception.ResourceNotFoundException;
import com.example.event.Model.Event;
import com.example.event.Model.Resource;
import com.example.event.Repository.EventRepository;
import com.example.event.Repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    public Event createEvent(Event event) {
        Resource resource = resourceRepository.findById(event.getResource().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with ID: " + event.getResource().getId()));

        if (!resource.isAvailable()) {
            throw new DoubleBookingException(
                    "Resource '" + resource.getName() + "' is currently not available for booking.");
        }

        if (eventRepository.existsByResourceAndEventDate(resource, event.getEventDate())) {
            throw new DoubleBookingException(
                    "'" + resource.getName() + "' is already booked on " + event.getEventDate() +
                            ". Please choose a different date or resource.");
        }

        if (event.getEventDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Event date cannot be in the past. Please select today or a future date.");
        }

        event.setResource(resource);
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Event not found with ID: " + id));
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findByEventDateGreaterThanEqualOrderByEventDateAsc(LocalDate.now());
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        Event existing = getEventById(id);

        Resource resource = resourceRepository.findById(updatedEvent.getResource().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with ID: " + updatedEvent.getResource().getId()));

        if (eventRepository.existsByResourceAndEventDateAndIdNot(
                resource, updatedEvent.getEventDate(), id)) {
            throw new DoubleBookingException(
                    "'" + resource.getName() + "' is already booked on " +
                            updatedEvent.getEventDate() +
                            ". Please choose a different date or resource.");
        }

        if (updatedEvent.getEventDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Event date cannot be in the past.");
        }

        existing.setTitle(updatedEvent.getTitle());
        existing.setDescription(updatedEvent.getDescription());
        existing.setOrganizerName(updatedEvent.getOrganizerName());
        existing.setContactEmail(updatedEvent.getContactEmail());
        existing.setEventDate(updatedEvent.getEventDate());
        existing.setStartTime(updatedEvent.getStartTime());
        existing.setEndTime(updatedEvent.getEndTime());
        existing.setResource(resource);
        existing.setStatus(updatedEvent.getStatus());
        existing.setNotes(updatedEvent.getNotes());

        return eventRepository.save(existing);
    }

    public void deleteEvent(Long id) {
        Event existing = getEventById(id);
        eventRepository.delete(existing);
    }

    public boolean isResourceAvailable(Long resourceId, LocalDate date) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with ID: " + resourceId));
        return !eventRepository.existsByResourceAndEventDate(resource, date);
    }
}
