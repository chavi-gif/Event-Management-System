package com.example.event.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "resource")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Resource name is required")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Resource type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResourceType type;

    @Column
    private String description;

    @Column(nullable = false)
    private boolean available = true;

    @OneToMany(mappedBy = "resource", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Event> events;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "quantity")
    private Integer quantity = 1;

    public Resource() {}

    public Resource(String name, ResourceType type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.quantity = 1;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ResourceType getType() { return type; }
    public void setType(ResourceType type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public List<Event> getEvents() { return events; }
    public void setEvents(List<Event> events) { this.events = events; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public enum ResourceType {
        VENUE,
        EQUIPMENT
    }
}