package com.solution.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Banque;

public interface BanqueRepository extends JpaRepository<Banque,Integer> {
 
     Banque findBanqueByNom (String nom);
    

}
