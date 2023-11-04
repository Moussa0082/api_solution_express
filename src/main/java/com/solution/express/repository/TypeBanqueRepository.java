package com.solution.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.TypeBanque;

public interface TypeBanqueRepository  extends JpaRepository<TypeBanque,Integer>{

    TypeBanque findTypeBanqueByNom (String nom);
    
}
