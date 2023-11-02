package com.solution.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement,Integer>{
    
}
