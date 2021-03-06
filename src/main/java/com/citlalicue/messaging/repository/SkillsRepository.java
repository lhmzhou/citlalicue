package com.citlalicue.messaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citlalicue.messaging.model.Skills;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long> {
    
}