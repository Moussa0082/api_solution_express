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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Cotisation {
    

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int idCotisation;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = true)
    private Date dateCreation;

    @Column(nullable = false)
    private int frais;

    @Column(nullable = false)
    private String description;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = true)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = true)
    private Date dateFin;

    // //Liaison User aux cotisation
    @ManyToMany
    @JoinColumn(name = "idUtilisateur")
    private List<Utilisateur> utilisateur;


    // //Pour afficher la liste des paiements liée à la cotisation
     @OneToMany
     (mappedBy="cotisation", cascade = CascadeType.ALL)
    // @JsonIgnoreProperties(value = {"utiliateur"})
    private  List<Paiement> paiement;
    
    
}
