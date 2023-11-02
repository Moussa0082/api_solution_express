package com.solution.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Rapport;

public interface RapportRepository  extends JpaRepository<Rapport,Integer>{
    
}
