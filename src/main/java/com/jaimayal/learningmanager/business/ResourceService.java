package com.jaimayal.learningmanager.business;

import com.jaimayal.learningmanager.persistence.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {
    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceService(ResourceRepository repository) {
        this.resourceRepository = repository;
    }

    public boolean addResource(String name, String author, String url, String type, String status) {
        boolean isValid = validateFields(name, author, url, type, status);
        if (!isValid) {
            return false;
        }

        Resource resource = new Resource();

        resource.setName(name);
        resource.setAuthor(author);
        resource.setUrl(url);
        resource.setType(ResourceType.valueOf(type));
        resource.setStatus(ResourceStatus.valueOf(status));
        resource.setAddedAt(LocalDate.now());

        saveResource(resource);
        return true;
    }

    public boolean deleteResourceById(Long id) {
        Optional<Resource> resourceOptional = findResourceById(id);
        if (resourceOptional.isEmpty()) {
            return false;
        }

        resourceRepository.deleteById(id);
        return true;
    }

    public Boolean existsResourceById(Long id) {
        return resourceRepository.existsById(id);
    }

    public List<Resource> findAllFinishedResources() {
        return resourceRepository.findAllByStatusEquals(ResourceStatus.FINISHED);
    }

    public List<Resource> findAllNonFinishedResources() {
        return resourceRepository.findAllByStatusNot(ResourceStatus.FINISHED);
    }

    public Optional<Resource> findResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    public List<Resource> findAllResources() {
        List<Resource> resources = new ArrayList<>();
        resourceRepository.findAll().forEach(resources::add);
        return resources;
    }

    public void saveResource(Resource resource) {
        resourceRepository.save(resource);
    }

    public boolean updateResourceById(Long id, String name, String author, String url, String type) {
        boolean isValid = validateFields(name, author, url, type);
        if (!isValid) {
            return false;
        }

        Optional<Resource> resourceOptional = findResourceById(id);
        if (resourceOptional.isEmpty()) {
            return false;
        }

        Resource resource = resourceOptional.get();

        resource.setName(name);
        resource.setAuthor(author);
        resource.setUrl(url);
        resource.setType(ResourceType.valueOf(type));

        saveResource(resource);
        return true;
    }

    public boolean updateResourceStatusById(Long id, String status) {
        boolean isValid = validateFields(status);
        if (!isValid) {
            return false;
        }

        Optional<Resource> resourceOptional = findResourceById(id);
        if (resourceOptional.isEmpty()) {
            return false;
        }

        Resource resource = resourceOptional.get();

        ResourceStatus updatedStatus;
        try {
            updatedStatus = ResourceStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            return false;
        }

        if (resource.getStatus() == updatedStatus) {
            return true;
        } else if (updatedStatus == ResourceStatus.FINISHED) {
            resource.setStatus(updatedStatus);
            resource.setFinishedAt(LocalDate.now());
        } else {
            resource.setStatus(updatedStatus);
            resource.setFinishedAt(null);
        }

        saveResource(resource);
        return true;
    }

    private boolean validateFields(String... parameters) {
        for (String parameter : parameters) {
            if (parameter.isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
