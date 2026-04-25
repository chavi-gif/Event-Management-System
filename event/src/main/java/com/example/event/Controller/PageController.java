package com.example.event.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/events")
    public String eventsPage() {
        return "events";
    }

    @GetMapping("/booking")
    public String bookingPage() {
        return "booking";
    }

    @GetMapping("/resources")
    public String resourcesPage() {
        return "resources";
    }
}
