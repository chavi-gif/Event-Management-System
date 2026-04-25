package com.example.event.Service;

import com.example.event.Exception.ResourceNotFoundException;
import com.example.event.Model.Resource;
import com.example.event.Repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public Resource getResourceById(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with ID: " + id));
    }

    public List<Resource> getAllVenues() {
        return resourceRepository.findByType(Resource.ResourceType.VENUE);
    }

    public List<Resource> getAllEquipment() {
        return resourceRepository.findByType(Resource.ResourceType.EQUIPMENT);
    }

    public Resource createResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    public Resource updateResource(Long id, Resource updatedResource) {
        Resource existing = getResourceById(id);
        existing.setName(updatedResource.getName());
        existing.setType(updatedResource.getType());
        existing.setDescription(updatedResource.getDescription());
        existing.setAvailable(updatedResource.isAvailable());
        return resourceRepository.save(existing);
    }

    public void deleteResource(Long id) {
        Resource existing = getResourceById(id);
        resourceRepository.delete(existing);
    }
}