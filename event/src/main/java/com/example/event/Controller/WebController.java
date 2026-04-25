package com.example.event.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/index.html")
    public String indexHtml() {
        return "index";
    }

    @GetMapping("/home.html")
    public String homeHtml() {
        return "home";
    }

    @GetMapping("/login.html")
    public String loginHtml() {
        return "login";
    }

    @GetMapping("/events.html")
    public String eventsHtml() {
        return "events";
    }

    @GetMapping("/booking.html")
    public String bookingHtml() {
        return "booking";
    }

    @GetMapping("/resources.html")
    public String resourcesHtml() {
        return "resources";
    }
}
