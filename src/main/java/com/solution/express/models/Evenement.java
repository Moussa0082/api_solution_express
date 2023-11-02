package com.solution.express.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Evenement {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int idEvenement;
    
    @Column(nullable = false)
    private String descriptionEvenment;
    
    @Column(nullable = false)
    private String lieuEvenement;


    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateEvenement;
    
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm")
    private Date heureEvenement;


    // //Liaison du user au rapport pour voir le user qui a elaborer le rapport
    // @ManyToOne
    // private Utilisateur utilisateur;

    
}
