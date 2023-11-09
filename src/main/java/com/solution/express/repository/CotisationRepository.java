package com.solution.express.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Cotisation;
import com.solution.express.models.Utilisateur;

public interface CotisationRepository  extends JpaRepository<Cotisation,Integer>{
 
    
   Cotisation findCotisationByUtilisateurAndNom(Utilisateur utilisateur, String nom);

   public Cotisation findByNom(String nom);
  
   List<Cotisation> findByUtilisateurIdUtilisateur(Integer idUtilisateur);

   Cotisation getByIdCotisation(int idCotisation);

   Cotisation findByCreateurAndIdCotisation(Utilisateur createur , int idCotisation);

   // Optional<Cotisation> findByCotisationCreateur(Utilisateur createur);
   

}
