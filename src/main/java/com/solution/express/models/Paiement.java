package com.solution.express.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Paiement {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int idPaiement;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date datePaiement;
    
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm")
    private Date heurePaiement;

    //Liée l'id de la cotisation au paiement pour voir la cotisation liée à un paiement specifique
    // @ManyToOne
    // private Cotisation cotisation;
    
    // //Liaison du user au paiement pour voir le user qui faire le paiement
    // @ManyToOne
    // private Utilisateur utilisateur;

    

    
}
