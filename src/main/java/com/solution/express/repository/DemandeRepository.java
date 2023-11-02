package com.solution.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Demande;

public interface DemandeRepository extends JpaRepository<Demande,Integer>{
    
}
