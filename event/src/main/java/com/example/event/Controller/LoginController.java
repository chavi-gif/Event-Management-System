package com.example.event.Controller;

import com.example.event.Model.User;
import com.example.event.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(HttpSession session) {
        // Check if user is logged in
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/login";
        }
        return "home";
    }

    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        // If already logged in, redirect to home
        if (session.getAttribute("loggedUser") != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        User user = userService.login(email, password);

        if (user != null) {
            session.setAttribute("loggedUser", user);

            // Check if admin - redirect to admin dashboard
            if ("admin@gmail.com".equals(email)) {
                return "redirect:/admin/dashboard";
            }

            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/api/current-user")
    @ResponseBody
    public User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("loggedUser");
    }
}
