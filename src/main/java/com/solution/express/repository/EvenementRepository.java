package com.solution.express.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Evenement;

public interface EvenementRepository extends JpaRepository<Evenement,Integer>{

    // String findEvenementByDescriptionAndNom(String nomEvenement, String Description);
    
}
