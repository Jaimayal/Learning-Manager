package com.jaimayal.learningmanager.persistence;

import com.jaimayal.learningmanager.business.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends CrudRepository<Resource, Long> {
    List<Resource> findAllByCompletionFalse();

    List<Resource> findAllByCompletionTrue();
}
