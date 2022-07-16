package com.jaimayal.learningmanager.persistence;

import com.jaimayal.learningmanager.business.Resource;
import com.jaimayal.learningmanager.business.ResourceStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends CrudRepository<Resource, Long> {
    List<Resource> findAllByStatusEquals(ResourceStatus status);
}
