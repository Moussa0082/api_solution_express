package com.solution.express.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Rapport;

public interface RapportRepository  extends JpaRepository<Rapport,Integer>{
    
     Rapport findByMessage(String message);
     public List<Rapport> findByCotisationIdCotisation(Integer idCotisation);
}
