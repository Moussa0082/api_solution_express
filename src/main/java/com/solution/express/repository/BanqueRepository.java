package com.solution.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Banque;
import com.solution.express.models.TypeBanque;

import java.util.List;


public interface BanqueRepository extends JpaRepository<Banque,Integer> {
 
    Banque findBanqueByNom (String nom);

    Banque findByIdBanque (Integer id);
    
    Banque findBanqueByTypeBanque (TypeBanque typeBanque);

}
