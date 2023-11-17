package com.solution.express.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Admin {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int idAdmin;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    private String image;

    private Boolean isActive = true;

    //Pour voir la liste des agents ajoutés par l'admin
    @OneToMany
    (mappedBy="admin")
    @JsonIgnore
    private  List<Agent> agent;
    
    @OneToMany
    (mappedBy="admin")
    @JsonIgnore
    private  List<Alerte> alerte;
    
    @OneToMany
    (mappedBy="admin")
    @JsonIgnore
    private  List<Demande> demande;

    //Liaison superadmin à admin
    @ManyToOne
    @JoinColumn(name = "idSuperAdmin")
    @JsonIgnore
    private SuperAdmin superAdmin;

    // Liaison  banque à admin 
    @OneToOne 
    @JoinColumn(name = "idBanque")
    @JsonIgnore
    private Banque banque;
    
}
