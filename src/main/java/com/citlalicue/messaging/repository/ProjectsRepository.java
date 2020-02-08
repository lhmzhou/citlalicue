package com.citlalicue.messaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citlalicue.messaging.model.Projects;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Long> {
    
}