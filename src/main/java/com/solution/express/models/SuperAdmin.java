package com.solution.express.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Entity
@Data
public class SuperAdmin {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int idSuperAdmin;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    @OneToMany
    (mappedBy = "superAdmin")
    // @JsonIgnoreProperties(value = {"admin"})
    private  List<Admin> admin;
    
    //Liaison super admin à banque pour voir la liste des banque ajoutés par le super admin
    @OneToMany
    (mappedBy = "superAdmin")
   // @JsonIgnoreProperties(value = {"admin"})
    private  List<Banque> banque;
    
     //Liaison super type à banque pour voir la liste des type ajoutés par le super admin
    @OneToMany
    (mappedBy = "superAdmin")
   // @JsonIgnoreProperties(value = {"admin"})
    private  List<TypeBanque> typeBanques;


   
}
