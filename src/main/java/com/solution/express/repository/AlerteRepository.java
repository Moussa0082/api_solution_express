package com.solution.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Alerte;

public interface AlerteRepository  extends JpaRepository<Alerte, Integer>{
    
    

}
