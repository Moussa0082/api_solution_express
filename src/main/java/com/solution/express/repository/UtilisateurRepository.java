package com.solution.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Utilisateur;


public interface UtilisateurRepository  extends JpaRepository<Utilisateur, Integer>{

    Utilisateur findByIdUtilisateur(int idUtilisateur);
    public Utilisateur findByEmail(String email);
    
}
