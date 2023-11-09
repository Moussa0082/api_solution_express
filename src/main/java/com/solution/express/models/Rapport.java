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
public class Rapport {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int idRapport;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateRapport;
    
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm")
    private Date heureRapport;

    @Column(nullable = false)
    private String lieu;
    
    @Column(nullable = false)
    private String message;

    //Liaison du user au rapport pour voir le user qui a elaborer le rapport
    // @ManyToOne
    // @JoinColumn(name = "idUtilisateur")
    // private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "idCotisation")
    private Cotisation cotisation;

    
    // @ManyToOne
    // @JoinColumn(name = "idCreateurCotisation")
    // private Cotisation createur;
}
