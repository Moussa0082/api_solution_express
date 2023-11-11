package com.solution.express.repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Cotisation;
import com.solution.express.models.Evenement;
import com.solution.express.models.Utilisateur;

public interface EvenementRepository extends JpaRepository<Evenement,Integer>{

    Optional<Evenement> findByDateEvenement(String dateEvenement);

    // Evenement findByDateEvenement(Date date);
    // Evenement findByNomEvenementAndDateEvenement(String nomEvenment, Date date);

    // Evenement existsByNomEvenement(String nom);

    // String findEvenementByDescriptionAndNom(String nomEvenement, String Description);
    // Evenement findEvenementByUtilisateurAndIdEvenement(Utilisateur utilisateur, String nomEvenement);

    // List<Evenement> findByUtilisateurAndDateEvenement(Utilisateur utilisateur, Evenement  evenement);

    // Evenement findByIdEvenementAndDateEvenement(int idEvenement, Date dateEvenement);


}
