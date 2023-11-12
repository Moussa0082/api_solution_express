package com.solution.express.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Agent;
import com.solution.express.models.Banque;

public interface AgentRepository extends JpaRepository<Agent, Integer>{

    Boolean existsByEmail(String email);

    Agent findByEmail(String mail);

    List<Agent> findAllByBanque(Banque banque);

    
}
