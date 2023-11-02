package com.solution.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Agent;

public interface AgentRepository extends JpaRepository<Agent, Integer>{
    
}
