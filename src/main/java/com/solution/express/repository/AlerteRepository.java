package com.solution.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Alerte;
import com.solution.express.models.Utilisateur;

import java.util.List;


public interface AlerteRepository  extends JpaRepository<Alerte, Integer>{

    List<Alerte> findByUtilisateurIdUtilisateur(Integer idUtilisateur);
    
    // List<Alerte> findByUtilisateur(Integer idUtilisateur);

}
