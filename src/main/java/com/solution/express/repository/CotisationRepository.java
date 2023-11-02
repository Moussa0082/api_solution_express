package com.solution.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Cotisation;

public interface CotisationRepository  extends JpaRepository<Cotisation,Integer>{
    
}
