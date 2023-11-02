package com.solution.express.models;

import java.util.List;

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

@Entity
@Data
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

    // //Pour voir la liste des agents ajoutés par l'admin
    @OneToMany
    (mappedBy="admin")
    // @JsonIgnoreProperties(value = {"admin"})
    private  List<Agent> agent;

    // //Liaison superadmin à admin
    @ManyToOne
    @JoinColumn(name = "idSuperAdmin")
    private SuperAdmin superAdmin;

    // // Liaison  banque à admin 
    @OneToOne 
    @JoinColumn(name = "idBanque")
    private Banque banque;
    
}
