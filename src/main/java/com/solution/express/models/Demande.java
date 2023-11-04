package com.solution.express.models;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Demande {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int idDemande;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateDemande;

    private String photoDidentite;

    private String photoValide;

    private Boolean statutDemande = false;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String sexe;
    
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateNaiss;

    @Column(nullable = false)
    private String lieuNaiss;
    
    @Column(nullable = false)
    private String nationnalite;

    @Column(nullable = false)
    private String adresse;
    
    @Column(nullable = false)
    private String statutResidence;
    
    @Column(nullable = false)
    private String etatCivil;

    //Lier la demande faite à un type specifique
    @ManyToOne
    @JoinColumn(name = "idTypeBanque")
    private TypeBanque typeBanque;

    // //Liaison agent à la demande pour voir l'agent qui s'occuper de la demande
    @ManyToMany
    @JoinColumn(name = "idAgent")
    // (mappedBy = "idAgent", cascade = CascadeType.ALL)
    Set<Agent> agent;

    // //Lier l'id de l'utilisateur à la demande pour voir l'utilisateur qui a effectué la demande
    @ManyToOne
    private Utilisateur utilisateur;

}
