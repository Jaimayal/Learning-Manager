package com.jaimayal.learningmanager.business;

import com.jaimayal.learningmanager.persistence.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ResourceService {
    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceService(ResourceRepository repository) {
        this.resourceRepository = repository;
    }

    public void saveResource(Resource resource) {
        resourceRepository.save(resource);
    }

    public Optional<Resource> findResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    public List<Resource> findAllResources() {
        List<Resource> resources = new ArrayList<>();
        resourceRepository.findAll().forEach(resources::add);
        return resources;
    }

    public List<Resource> findAllFinishedResources() {
        return resourceRepository.findAllByStatusEquals(ResourceStatus.FINISHED);
    }

    public List<Resource> findAllInProgressResources() {
        return resourceRepository.findAllByStatusEquals(ResourceStatus.IN_PROGRESS);
    }

    public boolean setFinishedStatusById(long id) {
        Optional<Resource> resourceOptional = findResourceById(id);
        if (resourceOptional.isEmpty()) {
            return false;
        }

        Resource resource = resourceOptional.get();

        if (resource.getStatus() == ResourceStatus.FINISHED) {
            return true;
        } else {
            resource.setStatus(ResourceStatus.FINISHED);
            resource.setFinishedAt(LocalDate.now());
        }

        saveResource(resource);
        return true;
    }

    public boolean deleteResourceById(long id) {
        Optional<Resource> resourceOptional = findResourceById(id);
        if (resourceOptional.isEmpty()) {
            return false;
        }

        resourceRepository.deleteById(id);
        return true;
    }

    public boolean addResource(String name, String description, String url, String type, String status) {
        Resource resource = new Resource(
                name,
                description,
                url,
                ResourceType.valueOf(type.toUpperCase()),
                ResourceStatus.valueOf(status.toUpperCase())
        );

        saveResource(resource);
        return true;
    }
}
