package com.solution.express.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Cotisation;
import com.solution.express.models.Evenement;
import com.solution.express.models.Utilisateur;

public interface EvenementRepository extends JpaRepository<Evenement,Integer>{

    boolean findByNomAndDateEvenement(String nomEvenment, String dateEvenement);

    // String findEvenementByDescriptionAndNom(String nomEvenement, String Description);
    // Evenement findEvenementByUtilisateurAndIdEvenement(Utilisateur utilisateur, String nomEvenement);

}
