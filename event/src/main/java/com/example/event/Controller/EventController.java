package com.example.event.Controller;

import com.example.event.Model.Event;
import com.example.event.Model.Resource;
import com.example.event.Repository.EventRepository;
import com.example.event.Repository.ResourceRepository;
import com.example.event.Service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.event.Service.ImageUploadService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller for Event Booking API endpoints
 *
 * Base URL: /api/events
 */
@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ImageUploadService imageUploadService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Event>> getUpcomingEvents() {
        return ResponseEntity.ok(eventService.getUpcomingEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @GetMapping("/check-availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(
            @RequestParam Long resourceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        boolean available = eventService.isResourceAvailable(resourceId, date);
        return ResponseEntity.ok(Map.of(
                "resourceId", resourceId,
                "date", date.toString(),
                "available", available,
                "message", available
                        ? "Resource is available on this date"
                        : "Resource is already booked on this date"
        ));
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
        Event created = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ENDPOINT FOR IMAGE UPLOAD WITH ALL RESOURCES IN E_Quantity
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createEventWithImage(
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("organizerName") String organizerName,
            @RequestParam("contactEmail") String contactEmail,
            @RequestParam("eventDate") String eventDate,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "notes", required = false) String notes,
            @RequestParam("resourceIds") String[] resourceIds,
            @RequestParam(value = "quantities", required = false) String[] quantities,
            @RequestParam(value = "eventImage", required = false) MultipartFile eventImage) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Create new event (NO resource field set anymore)
            Event event = new Event();
            event.setTitle(title);
            event.setDescription(description);
            event.setOrganizerName(organizerName);
            event.setContactEmail(contactEmail);
            event.setEventDate(LocalDate.parse(eventDate));
            event.setStartTime(startTime);
            event.setEndTime(endTime);
            event.setNotes(notes);
            event.setStatus(Event.EventStatus.PENDING);

            // Build JSON for ALL resources (venue + equipment)
            StringBuilder allResourcesJson = new StringBuilder("{");
            boolean first = true;

            // Add all selected resources from resourceIds (main venue + other resources)
            if (resourceIds != null && resourceIds.length > 0) {
                for (String resId : resourceIds) {
                    Long rid = Long.parseLong(resId);
                    Resource res = resourceRepository.findById(rid).orElse(null);
                    if (res != null) {
                        if (!first) allResourcesJson.append(",");
                        // For venues, quantity is always 1
                        if (res.getType() == Resource.ResourceType.VENUE) {
                            allResourcesJson.append("\"").append(resId).append("\":1");
                        } else {
                            // For equipment, check if quantity is specified in quantities array
                            int quantity = 1;
                            if (quantities != null && quantities.length > 0) {
                                for (String qty : quantities) {
                                    String[] parts = qty.split(":");
                                    if (parts.length == 2 && parts[0].equals(resId)) {
                                        quantity = Integer.parseInt(parts[1]);
                                        break;
                                    }
                                }
                            }
                            allResourcesJson.append("\"").append(resId).append("\":").append(quantity);
                        }
                        first = false;
                    }
                }
            }

            allResourcesJson.append("}");
            event.setEquipmentQuantities(allResourcesJson.toString());
            System.out.println("All resources saved: " + allResourcesJson);

            // Handle image upload
            if (eventImage != null && !eventImage.isEmpty()) {
                String imageUrl = imageUploadService.uploadToImgBB(eventImage);
                event.setImageUrl(imageUrl);
                System.out.println("Image uploaded to: " + imageUrl);
            }

            eventRepository.save(event);

            response.put("success", true);
            response.put("message", "Event created successfully! Waiting for admin approval.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id,
                                             @Valid @RequestBody Event event) {
        return ResponseEntity.ok(eventService.updateEvent(id, event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateEventWithImage(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("eventDate") String eventDate,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "notes", required = false) String notes,
            @RequestParam("resourceId") Long resourceId,
            @RequestParam(value = "eventImage", required = false) MultipartFile eventImage) {

        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Event> eventOpt = eventRepository.findById(id);
            if (!eventOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Event not found");
                return ResponseEntity.badRequest().body(response);
            }

            Event event = eventOpt.get();
            event.setTitle(title);
            event.setDescription(description);
            event.setEventDate(LocalDate.parse(eventDate));
            event.setStartTime(startTime);
            event.setEndTime(endTime);
            event.setNotes(notes);

            // Update resources in equipmentQuantities
            if (resourceId != null) {
                Optional<Resource> resourceOpt = resourceRepository.findById(resourceId);
                if (resourceOpt.isPresent()) {
                    // Build new JSON with updated main resource
                    Map<String, Integer> currentMap = parseQuantities(event.getEquipmentQuantities());
                    currentMap.put(String.valueOf(resourceId), 1);
                    event.setEquipmentQuantities(mapToJson(currentMap));
                }
            }

            if (eventImage != null && !eventImage.isEmpty()) {
                String imageUrl = imageUploadService.uploadToImgBB(eventImage);
                event.setImageUrl(imageUrl);
                System.out.println("Updated image uploaded to: " + imageUrl);
            }

            eventRepository.save(event);
            response.put("success", true);
            response.put("message", "Event updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Helper method to parse JSON to Map
    private Map<String, Integer> parseQuantities(String json) {
        Map<String, Integer> map = new HashMap<>();
        if (json == null || json.isEmpty()) return map;
        try {
            json = json.replace("{", "").replace("}", "");
            String[] pairs = json.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    String key = keyValue[0].replace("\"", "").trim();
                    int value = Integer.parseInt(keyValue[1].trim());
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // Helper method to convert Map to JSON string
    private String mapToJson(Map<String, Integer> map) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (!first) json.append(",");
            json.append("\"").append(entry.getKey()).append("\":").append(entry.getValue());
            first = false;
        }
        json.append("}");
        return json.toString();
    }
}
