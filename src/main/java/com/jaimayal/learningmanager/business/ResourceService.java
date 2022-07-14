package com.jaimayal.learningmanager.business;

import com.jaimayal.learningmanager.persistence.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void addResource(Resource resource) {
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
        return resourceRepository.findAllByCompletionTrue();
    }

    public List<Resource> findAllUnfinishedResources() {
        return resourceRepository.findAllByCompletionFalse();
    }
}
