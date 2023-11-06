package com.solution.express.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Agent;

public interface AgentRepository extends JpaRepository<Agent, Integer>{

    Boolean existsByEmail(String email);

    Agent findByEmail(String mail);

    
}
