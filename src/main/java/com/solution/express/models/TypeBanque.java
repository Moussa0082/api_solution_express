package com.solution.express.models;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class TypeBanque {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int idTypeBanque;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String description;
    
    private String image;

     
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private String dateCreated;

    //Joindre le superadmin à la baqnque l'id du super admin pour connaitre le super admin qui l'a crée
    @ManyToOne
    @JoinColumn(name="idSuperAdmin")
    @JsonIgnoreProperties(value = {"demande"})
    private SuperAdmin superAdmin;
    
    // //Joindre la banque a son type
    @ManyToOne
    @JoinColumn(name = "idBanque")
    @JsonIgnoreProperties(value = {"demande"})
    private Banque banque;

    // //Liste des demandes dans le type
    @OneToMany
    (mappedBy = "typeBanque" ,cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"demande"})
    private  List<Demande> demande;
   
}
