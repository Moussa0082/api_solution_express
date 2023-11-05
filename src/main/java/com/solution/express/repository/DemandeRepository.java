package com.solution.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Demande;
import com.solution.express.models.Utilisateur;

public interface DemandeRepository extends JpaRepository<Demande,Integer>{

    // Demande findByDemandeUtilisateur(Utilisateur utilisateur, Integer id);
    // Demande findByNumeroDemande (String numeroDemande);
    // String findByTypeBanque (String TypeBanque);
}
