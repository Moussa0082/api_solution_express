package com.solution.express.repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.solution.express.models.Cotisation;
import com.solution.express.models.Evenement;
import com.solution.express.models.Utilisateur;

public interface EvenementRepository extends JpaRepository<Evenement,Integer>{

    boolean findByNomEvenementAndDateEvenement(String nomEvenment, String dateEvenement);
    Evenement findByDateEvenement(String dateEvenement);

    // List<Evenement> findByIdCotisation(Integer idCotisation);
    public List<Evenement> findByCotisationIdCotisation(Integer idCotisation);



    // List<Evenement> findByUtilisateurAndDateEvenement(Utilisateur utilisateur, Evenement  evenement);

    // Evenement findByIdEvenementAndDateEvenement(int idEvenement, Date dateEvenement);


}
