package com.example.event.Controller;

import com.example.event.Model.Event;
import com.example.event.Model.LevelRange;
import com.example.event.Model.Resource;
import com.example.event.Model.User;
import com.example.event.Repository.EventRepository;
import com.example.event.Repository.LevelRangeRepository;
import com.example.event.Repository.ResourceRepository;
import com.example.event.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.event.Service.ImageUploadService;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LevelRangeRepository levelRangeRepository;

    @Autowired
    private ImageUploadService imageUploadService;

    // Simple admin check
    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        return user != null && "admin@gmail.com".equals(user.getEmail());
    }

    // ========== 1. DASHBOARD ==========
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("totalEvents", eventRepository.count());
        model.addAttribute("pendingEvents", eventRepository.countByStatus(Event.EventStatus.PENDING));
        model.addAttribute("approvedEvents", eventRepository.countByStatus(Event.EventStatus.APPROVED));
        model.addAttribute("totalResources", resourceRepository.count());
        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("recentEvents", eventRepository.findTop5ByOrderByEventDateDesc());

        return "admin/dashboard";
    }

    // ========== 2. MANAGE EVENTS ==========
    @GetMapping("/events")
    public String manageEvents(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("pendingEvents", eventRepository.findByStatus(Event.EventStatus.PENDING));
        model.addAttribute("approvedEvents", eventRepository.findByStatus(Event.EventStatus.APPROVED));
        model.addAttribute("cancelledEvents", eventRepository.findByStatus(Event.EventStatus.CANCELLED));

        return "admin/events";
    }

    @PostMapping("/event/approve/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> approveEvent(@PathVariable Long id, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        if (!isAdmin(session)) {
            response.put("success", false);
            response.put("message", "Unauthorized");
            return ResponseEntity.status(401).body(response);
        }

        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            event.setStatus(Event.EventStatus.APPROVED);
            eventRepository.save(event);
            response.put("success", true);
            response.put("message", "Event approved successfully");
        } else {
            response.put("success", false);
            response.put("message", "Event not found");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/event/reject/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> rejectEvent(@PathVariable Long id, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        if (!isAdmin(session)) {
            response.put("success", false);
            response.put("message", "Unauthorized");
            return ResponseEntity.status(401).body(response);
        }

        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            event.setStatus(Event.EventStatus.CANCELLED);
            eventRepository.save(event);
            response.put("success", true);
            response.put("message", "Event rejected");
        } else {
            response.put("success", false);
            response.put("message", "Event not found");
        }
        return ResponseEntity.ok(response);
    }

    // ========== 3. MANAGE RESOURCES WITH IMAGE UPLOAD ==========
    @GetMapping("/resources")
    public String manageResources(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("venues", resourceRepository.findByType(Resource.ResourceType.VENUE));
        model.addAttribute("equipment", resourceRepository.findByType(Resource.ResourceType.EQUIPMENT));

        return "admin/resources";
    }

    @PostMapping("/resource/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addResource(
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "image", required = false) MultipartFile image,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();
        if (!isAdmin(session)) {
            response.put("success", false);
            response.put("message", "Unauthorized");
            return ResponseEntity.status(401).body(response);
        }

        Resource resource = new Resource();
        resource.setName(name);
        resource.setType(Resource.ResourceType.valueOf(type));
        resource.setDescription(description);
        resource.setAvailable(true);

        // Handle image upload to ImgBB cloud
        if (image != null && !image.isEmpty()) {
            try {
                String imageUrl = imageUploadService.uploadToImgBB(image);
                resource.setImageUrl(imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
                response.put("success", false);
                response.put("message", "Failed to upload image: " + e.getMessage());
                return ResponseEntity.badRequest().body(response);
            }
        }

        resourceRepository.save(resource);
        response.put("success", true);
        response.put("message", "Resource added successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/resource/delete/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteResource(@PathVariable Long id, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        if (!isAdmin(session)) {
            response.put("success", false);
            response.put("message", "Unauthorized");
            return ResponseEntity.status(401).body(response);
        }

        Optional<Resource> resourceOpt = resourceRepository.findById(id);
        if (resourceOpt.isPresent()) {
            resourceRepository.deleteById(id);
            response.put("success", true);
            response.put("message", "Resource deleted");
        } else {
            response.put("success", false);
            response.put("message", "Resource not found");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resource/update/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateResourceWithImage(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("available") boolean available,
            @RequestParam(value = "image", required = false) MultipartFile image,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();
        if (!isAdmin(session)) {
            response.put("success", false);
            response.put("message", "Unauthorized");
            return ResponseEntity.status(401).body(response);
        }

        Optional<Resource> resourceOpt = resourceRepository.findById(id);
        if (resourceOpt.isPresent()) {
            Resource resource = resourceOpt.get();
            resource.setName(name);
            resource.setType(Resource.ResourceType.valueOf(type));
            resource.setDescription(description);
            resource.setAvailable(available);

            // Handle image upload to ImgBB cloud
            if (image != null && !image.isEmpty()) {
                try {
                    String imageUrl = imageUploadService.uploadToImgBB(image);
                    resource.setImageUrl(imageUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.put("success", false);
                    response.put("message", "Failed to upload image: " + e.getMessage());
                    return ResponseEntity.badRequest().body(response);
                }
            }

            resourceRepository.save(resource);
            response.put("success", true);
            response.put("message", "Resource updated successfully");
        } else {
            response.put("success", false);
            response.put("message", "Resource not found");
        }
        return ResponseEntity.ok(response);
    }

    // Get single resource (for edit modal)
    @GetMapping("/resource/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getResource(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(401).build();
        }
        Optional<Resource> resourceOpt = resourceRepository.findById(id);
        return resourceOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ========== 4. MANAGE LEVEL RANGES ==========
    @GetMapping("/levels")
    public String manageLevels(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        List<LevelRange> levelRanges = levelRangeRepository.findAll();
        model.addAttribute("levelRanges", levelRanges);

        return "admin/levels";
    }

    @PostMapping("/level/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addLevelRange(@RequestBody LevelRange levelRange, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        if (!isAdmin(session)) {
            response.put("success", false);
            response.put("message", "Unauthorized");
            return ResponseEntity.status(401).body(response);
        }

        levelRangeRepository.save(levelRange);
        response.put("success", true);
        response.put("message", "Level range added successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/level/delete/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteLevelRange(@PathVariable Long id, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        if (!isAdmin(session)) {
            response.put("success", false);
            response.put("message", "Unauthorized");
            return ResponseEntity.status(401).body(response);
        }

        Optional<LevelRange> levelOpt = levelRangeRepository.findById(id);
        if (levelOpt.isPresent()) {
            levelRangeRepository.deleteById(id);
            response.put("success", true);
            response.put("message", "Level range deleted");
        } else {
            response.put("success", false);
            response.put("message", "Level range not found");
        }
        return ResponseEntity.ok(response);
    }

    // ========== 5. MANAGE USERS ==========
    @GetMapping("/users")
    public String manageUsers(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "admin/users";
    }

    @DeleteMapping("/user/delete/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        if (!isAdmin(session)) {
            response.put("success", false);
            response.put("message", "Unauthorized");
            return ResponseEntity.status(401).body(response);
        }

        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            userRepository.deleteById(id);
            response.put("success", true);
            response.put("message", "User deleted successfully");
        } else {
            response.put("success", false);
            response.put("message", "User not found");
        }
        return ResponseEntity.ok(response);
    }
}