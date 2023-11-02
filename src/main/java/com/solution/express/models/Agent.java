package com.solution.express.models;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Agent {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int idAgent;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    //Lier l'admin à l'agent pour voir l'admin qui la ajouter
    @ManyToOne
    @JoinColumn(name = "idAdmin")
    private Admin  admin;
    
    // //Lier la banque à l'agent pour voir la banque dans lequel l'agent est affecter
    @ManyToOne
    @JoinColumn(name = "idBanque")
    private Banque  banque;

    // //liaison demande à agent plusieurs à plusieurs
    @ManyToMany
    (mappedBy = "agent", cascade = CascadeType.ALL)
    Set<Demande> demande;
}
