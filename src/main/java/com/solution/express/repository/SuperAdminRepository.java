package com.solution.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.SuperAdmin;
import com.solution.express.models.Utilisateur;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Integer> {
    
     Boolean existsByEmail(String email);
     
     SuperAdmin  findByEmail(String mail);

    SuperAdmin findByMotDePasseAndEmail(String motdepasse, String email);

}
