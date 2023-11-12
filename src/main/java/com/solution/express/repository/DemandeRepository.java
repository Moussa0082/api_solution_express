package com.solution.express.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Admin;
import com.solution.express.models.Demande;
import com.solution.express.models.TypeBanque;
import com.solution.express.models.Utilisateur;

public interface DemandeRepository extends JpaRepository<Demande,Integer>{

    Demande findByNumeroDemande(String numeroDemandeFormate);

    List<Demande> findByTypeBanque(TypeBanque typeBanque);

    Demande findByTypeBanqueAndUtilisateur(TypeBanque typeBanque, Utilisateur user);

    List<Demande> findByUtilisateurIdUtilisateur(Integer idUtilisateur);

    Demande findByTypeBanqueAndAdmin(TypeBanque typeBanque, Admin admin);

    List<Demande> findByIdBanque(Integer idBanque);

    // Demande findByDemandeUtilisateur(Utilisateur utilisateur, Integer id);
    // Demande findByNumeroDemande (String numeroDemande);
    // String findByTypeBanque (String TypeBanque);
}
